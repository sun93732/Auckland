/* Copyright (c) 2009, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
 DESCRIPTION
 <short description of component this file declares/defines>

 PRIVATE CLASSES
 <list of private classes defined - with one-line descriptions>

 NOTES
 <other useful comments, qualifications, etc.>

 MODIFIED    (MM/DD/YY)
 vijanaki    09/27/12 - XbranchMerge vijanaki_bug-14630717 from main
 kchaubey    07/31/12 - Backport kchaubey_bug-14175643 from
 kchaubey    07/01/12 - Backport kchaubey_bug-14217989 from main
 kchaubey    06/24/12 - Backport rapurush_bug-14151044 from main
 kchaubey    06/22/12 - Backport kchaubey_bug-14059926 from main
 kchaubey    04/06/12 - bug 13924664
 vijanaki    10/20/10 - XbranchMerge vijanaki_bug-10213674 from
 st_entsec_11.1.1.4.0
 vijanaki    10/19/10 - Fix Bug 10213674
 atijain     08/02/10 - XbranchMerge atijain_bug-9885286 from main
 atijain     07/15/10 - Bug 9885286
 atijain     03/07/10 - Taking an XLIF File, generating an LDIF File and
 loading it.
 atijain     08/20/09 - Creation
 */
package oracle.jldif.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.naming.NameNotFoundException;
import javax.naming.CommunicationException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.NoSuchAttributeException;

public class LDIFLoader
{
    private String fileName = null;
    private Vector subVector = null;
    private boolean debug = true;
    private DirContext ctx = null;
    private boolean ignoreError = false;
    private NamingException[] ignoredExceptionList = null;
    private PrintStream pStream = null;
    protected boolean changeTypeAdd = false;
    protected String tempFilesLocation;

    private String listFileExt = ".lst";
    private List<Integer> retryIndices = null;
    private int maxRetryCount = 15;
    private int retryCount = -1;

    private static java.util.logging.Logger m_logger = java.util.logging.Logger
            .getLogger("oracle.ldap.util");

    /**
     * Constructor of the LdifLoader.
     * 
     * @param inCtx
     *            a valid DirContext
     * @param inFileName
     *            the input file name to be loaded
     * @param inSubVector
     *            the substitution vector
     */
    public LDIFLoader(DirContext inCtx, String inFileName, Vector inSubVector,
            java.util.logging.Logger lgr)
    {
        this(inCtx, inFileName, inSubVector);
        m_logger = lgr;
        // initialize retryIndices
        retryIndices = new LinkedList<Integer>();
    }

    public LDIFLoader(DirContext inCtx, String inFileName, Vector inSubVector)
    {
        fileName = inFileName;
        subVector = inSubVector;
        ctx = inCtx;
        debug = false;
        pStream = System.out;
        // initialize retryIndices
        retryIndices = new LinkedList<Integer>();
    }

    public LDIFLoader(DirContext inCtx, String inFileName, Vector inSubVector,
            java.util.logging.Logger lgr, int maxretryCount)
    {
        this(inCtx, inFileName, inSubVector);
        m_logger = lgr;

        maxRetryCount = maxretryCount;
        // initialize retryIndices
        retryIndices = new LinkedList<Integer>();
    }

    public LDIFLoader(DirContext inCtx, String inFileName, Vector inSubVector,
            int maxretryCount)
    {
        fileName = inFileName;
        subVector = inSubVector;
        ctx = inCtx;
        debug = false;
        pStream = System.out;

        maxRetryCount = maxretryCount;
        // initialize retryIndices
        retryIndices = new LinkedList<Integer>();
    }

    /**
     * This performs the actual loading of the ldif files into the directory
     * 
     */
    public void load() throws UtilException
    {
        load(ignoreError, pStream);
    }

    /**
     * Similar to load(). The ignore parameter allows any LDAP errors to be
     * ignore and the rest of LDIF entries to be loaded
     * 
     * @param ignoreError
     *            set to true if errors are to be ignored
     * @exception UtilException
     */
    public void load(boolean ignoreError, PrintStream pStream)
            throws UtilException
    {
        load(ignoreError, pStream, null);
    }

    public void load(boolean ignoreError, PrintStream pStream,
            String excludeVersionStr) throws UtilException
    {
        if (pStream == null)
            this.pStream = System.out;
        else
            this.pStream = pStream;
        // this.ignoredExceptionList = ignoredExceptionList;

        this.ignoreError = ignoreError;
        
        if (fileName.endsWith(listFileExt))
            recursiveLoad(fileName, "");
        else if (fileName.endsWith(".xlf"))
            loadXLFFile(fileName);
        else
            loadOneLdifFile(fileName, "", excludeVersionStr);
    }

    /**
     * This will recursive traverse files that are in the lst/sbs format
     * 
     * @param inFileName
     *            the input file name
     * @param indentStr
     *            it should always be "".
     */
    private void recursiveLoad(String inFileName, String indentStr)
            throws UtilException
    {
        try
        {
            File file = new File(inFileName);
            if (!file.exists())
            {
                // throw NoSuchFileException
            }

            BufferedReader in = new BufferedReader(new FileReader(file));

            // Get the directory where the files are located, which
            // is the same as where the list file is located.
            String directory = file.getParent();

            String curLine;
            while (in.ready())
            {
                curLine = in.readLine().trim();
                /*
                 * Ignore blank lines and lines preceding with #
                 */
                if (curLine.length() > 0 && !curLine.startsWith("#"))
                {
                    String curFile = directory + File.separator + curLine;
                    if (curFile.endsWith(listFileExt))
                    {
                        // recursively work on this .lst file
                        recursiveLoad(curFile, indentStr + "   ");
                    }
                    else
                    {
                        loadOneLdifFile(curFile, indentStr + "   ");
                    }
                }
            } // end while
        }

        catch (Exception e)
        {
            // m_logger.log(Level.SEVERE, "Exception in recursive load", this);
            System.out.println("Error loading the file");
        }

    }

    /**
     * This method is used internally to perform substitutions of an LDIF
     * template and loading the LDIF entries into the directory for a single
     * ldif file
     */
    private void loadOneLdifFile(String curFileName, String indentStr)
            throws UtilException
    {
        loadOneLdifFile(curFileName, indentStr, null);
    }

    private void loadOneLdifFile(String curFileName, String indentStr,
            String excludeVersionStr) throws UtilException
    {
        try
        {

            System.out.println("Loading LDIF File: " + curFileName);
            LDIFReader ldifReader = new LDIFReader(curFileName, excludeVersionStr,
                    ignoreError);
            LDIFWriter ldifWriter = null;
            ByteArrayOutputStream outStream = null;
            outStream = new ByteArrayOutputStream();
            ldifWriter = new LDIFWriter(outStream);

            Vector<String> ldifEntry = null;
            Vector subsLdifEntry = null;
            while ((ldifEntry = ldifReader.nextEntry()) != null)
            {
                if (ldifEntry.size() > 0)
                {
                    subsLdifEntry = LDIFSubstitute.substitute(ldifEntry,
                            subVector);
                    ldifWriter.writeEntry(subsLdifEntry);
                }
            }
            ldifWriter.close();
            ldifReader.close();

            ldifReader = new LDIFReader(new ByteArrayInputStream(
                    outStream.toByteArray()), "UTF-8");

            LDIFRecord curRecord = null;

            // loading LDIFRecords for the first time
            // populating retry indices for failed child records
            for (int index = 0; (curRecord = ldifReader.nextRecord()) != null; index++)
            {
                if (!loadRecord(curRecord))
                {
                    retryIndices.add(index);
                    m_logger.log(Level.INFO, "Adding to retry list: "
                            + curRecord.getDN());
                }
            }

            ldifReader.close();

            // retry logic starts here
            ListIterator<Integer> itr = null;

            // number of indices in the retry list
            int numRetryIndices = retryIndices.size();

            // if ignoreError = FALSE, an additional retry is required to throw
            // an exception and terminate
            if (!ignoreError)
                maxRetryCount++;

            for (retryCount = 0; retryCount < maxRetryCount; retryCount++)
            {
                // if retryIndices is empty
                if (retryIndices.isEmpty())
                    break;

                ldifReader = new LDIFReader(new ByteArrayInputStream(
                        outStream.toByteArray()), "UTF-8");
                itr = retryIndices.listIterator();
                int bufPos = 0;

                while (itr.hasNext())
                {
                    Integer index = itr.next();
                    // to get to the index(th) position
                    for (int i = bufPos; i <= index; i++)
                    {
                        curRecord = ldifReader.nextRecord();
                    }
                    bufPos = index + 1;

                    // retry loading the record
                    if (loadRecord(curRecord))
                    {
                        itr.remove();
                    }
                }
                ldifReader.close();

                // if there has been no change in the list after the retry
                if (numRetryIndices == retryIndices.size())
                {
                    // so that only one more retry is performed and appropriate
                    // error messages/exceptions are generated
                    retryCount = maxRetryCount - 2;
                }
                numRetryIndices = retryIndices.size();
            }
        }

        catch (IOException e)
        {
            System.out.println("IOException encountered when loading file : "
                    + curFileName);
            throw new UtilException(e);
        }
    }

    private void loadXLFFile(String fileName)
    {
        try
        {
            System.out.println("Loading XLIFF File: " + fileName);
            XLIFFParser parser = new XLIFFParser();
            parser.changeTypeAdd = changeTypeAdd;
            parser.tempFilesLocation = tempFilesLocation;
            Vector<LDIFRecord> ldifRecords = parser.parseXLFFile(fileName);
            for (LDIFRecord rec : ldifRecords)
            {
                loadRecord(rec);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error:" + e);
        }
    }

    private boolean loadRecord(LDIFRecord curRecord) throws UtilException
    {
        boolean loaded = true;
        try
        {
            try
            {
                switch (curRecord.getChangeType())
                {
                    case LDIF.RECORD_CHANGE_TYPE_ADD:
                        Attributes attrs = curRecord.getJNDIAttributes();
                        ctx.createSubcontext(curRecord.getDN(), attrs);
                        if (retryCount >= 0)
                            m_logger.log(Level.INFO,
                                    "Retry load successful for entry: "
                                            + curRecord.getDN());
                        break;
                    case LDIF.RECORD_CHANGE_TYPE_MODIFY:
                        ModificationItem[] modItem = curRecord
                                .getJNDIModificationItems();
                        ctx.modifyAttributes(curRecord.getDN(), modItem);
                        break;
                    case LDIF.RECORD_CHANGE_TYPE_DELETE:
                        ctx.destroySubcontext(curRecord.getDN());
                        break;
                    default:

                }
            }
            catch (NameNotFoundException ne)
            {
                if (retryCount < maxRetryCount - 1
                        && curRecord.getChangeType() == LDIF.RECORD_CHANGE_TYPE_ADD)
                {
                    loaded = false;
                }
                else
                {
                    throw ne;
                }
            }
        }
        catch (CommunicationException ce)
        {
            m_logger.log(
                    Level.SEVERE,
                    "Unable to establish connection to directory."
                            + " Verify the input parameters: host, port, dn & password"
                            + ce.getMessage());
            throw new UtilException(
                    "\nException encountered during loading of record:  "
                            + curRecord.toString(), ce);
        }
        catch (Exception e)
        {
            if (ignoreError)
            {
                if (!isIgnoredException(e))
                {
                    throw new UtilException(
                            "\nException encountered during loading of record:  "
                                    + curRecord.toString(), e);
                }
                else
                {
                    System.out.println("Ignoring Error " + e);
                    m_logger.log(Level.INFO, "Ignoring Error " + e);
                }
            }
            else
            {
                throw new UtilException(
                        "\nException encountered during loading of record:  "
                                + curRecord.toString(), e);
            }
        }
        return loaded;
    }

    private boolean isIgnoredException(Exception ne)
    {
        if (ignoredExceptionList == null)
        {
            return true;
        }

        if ((ne instanceof AttributeInUseException)
                || (ne instanceof NameAlreadyBoundException)
                || (ne instanceof NoSuchAttributeException))
            return true;

        return false;
    }
}

