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
    kchaubey    07/31/12 - Backport kchaubey_bug-14175643 from
    kchaubey    07/01/12 - Backport kchaubey_bug-14217989 from main
    atijain     08/20/09 - Creation
 */
package oracle.jldif.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;

/**
 * LDAP Data Interchange Format (LDIF) is a file format used to represent the
 * Directory entries. The data from the directory can be exported in this format
 * and could be imported into another directory server. LDIF Data can describe a
 * set of changes that needs to be applied to data in a directory. This format
 * is described in the Internet draft <A
 * HREF="http://www.ietf.org/rfc/rfc2849.txt" TARGET="_blank">The LDAP Data
 * Interchange Format (LDIF) - RFC-2849</A>.
 * <P>
 * 
 * This class provides a set of methods to read and manipulate an LDIF file.
 */
public class LDIFReader
{

    private static java.util.logging.Logger m_logger = java.util.logging.Logger
        .getLogger("oracle.ldap.util");
    /**
     * comments constant
     */
    private final static char COMMENT = '#';
    private final static String COMMENTSTR = "#orclfaversion:";

    /**
     * constant - DEFAULT_ENCODING - Platforms default encoding
     */
    private static String DEFAULT_ENCODING = null;
    static
    {
        DEFAULT_ENCODING = System.getProperty("file.encoding");
    }

    /**
     * The character encoding of the file or the byte stream
     */
    private String m_encoding = null;

    /**
     * A string object to hold the line that is read ahead.
     */
    private String lookAheadBuf = null;

    /**
     * BufferedReader object which provides an handle to the ldif file.
     */
    private BufferedReader inBuf = null;

    /**
     * A flag to track whether end of entry has reached or not.
     */
    private boolean isEndOfEntry = false;

    /**
     * A variable to count the number of bytes read from the LDIF file.
     */
    private long readCount = 0L;

    /**
     * A variable to hold the current line number that is read from the LDIF
     * file. Used for error reporting.
     */
    private int currLineNo = -1;

    /**
     * True if an attribute was successfully read for an entry. set when an
     * attribute was successfully returned. Reset when the nextRecord or
     * nextEntry is called. Basically used to care of the case in which the
     * comment is in last line. of the entry.
     */
    private boolean previousValue = false;

    /**
     * if set, the attribute value will be treated as a file name and the
     * contents of which will be taken as the attribute value. in case of unix
     * the file name begins with '/' and in case of NT the file name contains
     * ':'
     */
    private boolean attrValAsFileContent = false;

    /**
     * An list, which contains the attribute names whose values should be
     * treated as the sequence of raw bytes. This is used in case of Binary
     * attributes.
     */
    private String[] binaryAttributes = null;

    /**
     * Contains the version
     */
    private String version = null;

    /**
     * A flag to indicate the First Record.
     */
    private boolean firstRecord = true;

    /**
     * The version number to be excluded in reading the input ldif
     */
    private Version exVersion = null;

    private boolean ignoreError = true;

    /**
     * Default Constructor, Reads the data from standard input. The input data
     * is required to be present in UTF8 format.
     * 
     * @exception IOException
     *                If an I/O error occurs.
     */
    public LDIFReader() throws IOException
    {
        inBuf = new BufferedReader(new InputStreamReader(System.in,
            DEFAULT_ENCODING));
        this.m_encoding = DEFAULT_ENCODING;
    }

    /**
     * Constructs an Input stream reader to read the LDIF data from the file
     * specified.
     * 
     * @param file
     *            the name of the LDIF file
     * @exception IOException
     *                If an I/O error occurs.
     */
    public LDIFReader(String file) throws IOException
    {
        this(file, DEFAULT_ENCODING);
    }

    public LDIFReader(String file, String excludeVersionStr, Boolean ignoreError)
            throws IOException, UtilException
    {
        this(file, DEFAULT_ENCODING);
        this.ignoreError = ignoreError;

        if (excludeVersionStr != null)
        {
            Version exVersion = new Version(excludeVersionStr);
            if (exVersion.isValidVersion())
            {
                this.exVersion = exVersion;
            }
            else
            {
                throw new UtilException("Exclude version not valid: "
                        + excludeVersionStr);
            }
        }
    }

    /**
     * Constructs an Input stream reader to read the LDIF data from the file
     * specified.
     * 
     * @param file
     *            the name of the LDIF file
     * @param encoding
     *            the character encoding of the file
     * @exception IOException
     *                If an I/O error occurs.
     */
    public LDIFReader(String file, String encoding) throws IOException
    {
        if (null == encoding)
            encoding = DEFAULT_ENCODING;
        inBuf = new BufferedReader(new InputStreamReader(new FileInputStream(
            file), encoding));
        this.m_encoding = encoding;
    }

    /**
     * Constructs an Input stream reader to read the LDIF data from the file
     * object specified.
     * 
     * @param fileObj
     *            The file object of the LDIF file
     * @exception IOException
     *                If an I/O error occurs.
     */
    public LDIFReader(File fileObj) throws IOException
    {
        this(fileObj, DEFAULT_ENCODING);
    }

    /**
     * Constructs an Input stream reader to read the LDIF data from the file
     * object specified.
     * 
     * @param fileObj
     *            The file object of the LDIF file
     * @param encoding
     *            the character encoding of the file
     * @exception IOException
     *                If an I/O error occurs.
     */
    public LDIFReader(File fileObj, String encoding) throws IOException
    {
        if (encoding == null)
            encoding = DEFAULT_ENCODING;
        inBuf = new BufferedReader(new InputStreamReader(new FileInputStream(
            fileObj), encoding));
        this.m_encoding = encoding;
    }

    /**
     * Constructs an Input stream reader to read the LDIF data from the
     * specified input stream.
     * 
     * @param ds
     *            - the input stream providing the LDIF data
     * @exception IOException
     *                If an I/O error occurs.
     */
    public LDIFReader(InputStream ds) throws IOException
    {
        this(ds, DEFAULT_ENCODING);
    }

    /**
     * Constructs an Input stream reader to read the LDIF data from the
     * specified input stream.
     * 
     * @param ds
     *            the input stream providing the LDIF data
     * @param encoding
     *            the character encoding of the input byte stream
     * @exception IOException
     *                If an I/O error occurs.
     */
    public LDIFReader(InputStream ds, String encoding) throws IOException
    {
        if (encoding == null)
            encoding = DEFAULT_ENCODING;
        inBuf = new BufferedReader(new InputStreamReader(ds, encoding));
        this.m_encoding = encoding;
    }

    /**
     * Returns the number of bytes read from the LDIF file.
     */
    public long getReadCount()
    {
        return readCount;
    }

    /**
     * Check to make sure that the stream has not been closed
     * 
     * @exception IOException
     *                if the stream is closed.
     */
    private void ensureOpen() throws IOException
    {
        if (inBuf == null)
            throw new IOException("Stream closed");
    }

    /**
     * This method reads one line from the file and returns it. If an attribute
     * is continued in next line (i.e CRLF followed be a SPACE char) then the
     * continued line is also read so that one attribute:value pair is returned
     * 
     * @return String A line will be returned (if required joinging the
     *         continued lines)
     * @exception IOException
     *                If an I/O error occurs.
     */
    private synchronized String readAttributeLine(Boolean readComment) 
         throws IOException,UtilException
    {
        String ret = null;
        ensureOpen();

        /* initially the isEndOfEntry will be false */
        if (!isEndOfEntry)
        {
            while (true)
            {
                ret = inBuf.readLine();
                currLineNo++;

                if (ret != null)
                {
                    // Update the number of bytes read.
                    readCount += ret.length() + 1;

                    if (ret.length() < 1)
                    {
                        if (lookAheadBuf == null)
                        {
                            /* A sequence of empty lines needs to be ignored */
                            continue;
                        }
                        else
                        {
                            /*
                             * end of entry has been reached but lookAheadBuf is
                             * set but should not be comment
                             */
                            if (lookAheadBuf.charAt(0) == COMMENT
                                    && !readComment)
                            {
                                lookAheadBuf = ret = null;
                                if (previousValue)
                                {
                                    previousValue = false;
                                    break;
                                }
                                continue;
                            }
                            else if (firstRecord)
                            {
                                Object[] attrNV = getAttrNV(lookAheadBuf);
                                String attrName = (String) attrNV[0];
                                if (attrName.equalsIgnoreCase("version"))
                                {
                                    version = (String) attrNV[1];
                                    firstRecord = false;
                                    lookAheadBuf = null;
                                    continue;
                                }
                            }

                            ret = lookAheadBuf;
                            lookAheadBuf = null;
                            isEndOfEntry = true;
                            firstRecord = false;
                            break;
                        }
                    }

                    if (lookAheadBuf == null)
                    {
                        /* to read the next line in advance */
                        lookAheadBuf = ret;
                        continue;
                    }
                    if (ret.charAt(0) == ' ' || ret.charAt(0) == '\t')
                    {
                        /* checking whether attributes continues to next line */
                        lookAheadBuf = lookAheadBuf + ret.substring(1);
                        continue;
                    }
                    else
                    {
                        /* not a continuous line */
                        String tmp = lookAheadBuf;
                        lookAheadBuf = ret;
                        ret = tmp;
                        if (ret.charAt(0) == COMMENT && !readComment)
                            continue;
                        else if (firstRecord)
                        {
                            Object[] attrNV = getAttrNV(ret);
                            String attrName = (String) attrNV[0];
                            if (attrName.equalsIgnoreCase("version"))
                            {
                                version = (String) attrNV[1];
                                firstRecord = false;
                                continue;
                            }
                        }

                        firstRecord = false;
                        previousValue = true;
                        break;
                    }
                }
                else
                {
                    /* End of file has been reached */
                    isEndOfEntry = true;
                    ret = lookAheadBuf;
                    lookAheadBuf = null;
                    if (ret != null && ret.charAt(0) == COMMENT && !readComment)
                        ret = null;
                    else if (firstRecord && ret != null)
                    {
                        Object[] attrNV = getAttrNV(ret);
                        String attrName = (String) attrNV[0];
                        if (attrName.equalsIgnoreCase("version"))
                        {
                            version = (String) attrNV[1];
                            firstRecord = false;
                        }
                    }

                    break;
                }
            }
        }
        else
        {
            /*
             * Entry end has been reached. Repeat the same sequence for next
             * entry, until end of file is reached
             */
            isEndOfEntry = false;
            previousValue = false;
        }

        return ret;
    }

    /**
     * Specify the attribute list whose values should be treates as binary
     * sequence.
     * 
     * @param attrNames
     *            an array containing attribute names.
     */
    public void setBinaryAttributes(String attrNames[])
    {
        this.binaryAttributes = attrNames;
    }

    /**
     * A helper method to identify whether the given attribute is binary
     * attribute or not.
     */
    private boolean isBinaryAttribute(String attrName)
    {
        if (!(null == attrName || null == binaryAttributes || 0 == binaryAttributes.length))
        {
            for (int i = 0; i < binaryAttributes.length; i++)
                if (attrName.equalsIgnoreCase(binaryAttributes[i]))
                    return true;
        }

        return false;
    }

    /**
     * Returns the next entry in the LDIF file. Using this method you can
     * iterate thru all the entries in the LDIF file.
     * 
     * @return the next entry as a vector object containing the attributes as
     *         name:value pairs.i.e each element in the vector will look like
     *         name:vlaue. A null is returned if there are no more entries.
     * @exception IOException
     *                If an I/O error occurs.
     */
    public Vector<String> nextEntry() throws IOException, UtilException
    {
        Vector<String> retVector = new Vector<String>();
        try
        {
            /* read all the attributes of this entry. */
            String attrNV = null;
            Version version = null;

            if (exVersion != null)
            {
                /* read the commented lines at the beginning of entry */
                while ((attrNV = readAttributeLine(true)) != null)
                {
                    if (attrNV.charAt(0) == COMMENT)
                    {
                        /*
                         * version number for the entry found, read it as an
                         * attrNV pair
                         */
                        if (attrNV.toLowerCase().contains(COMMENTSTR))
                        {
                            retVector.addElement(attrNV);
                            break;
                        }
                    }
                    else
                    {
                        /*
                         * add the first attrNV pair to return vector and break
                         * out of the loop
                         */
                        retVector.addElement(attrNV);
                        break;
                    }
                }
            }

            /*
             * ignore all commented lines and read the entry
             */
            while ((attrNV = readAttributeLine(false)) != null)
            {
                retVector.addElement(attrNV);
            }

            if (retVector.size() == 0)
            {
                /* last entry, return null */
                return null;
            }

            /* no version numbers have been read, return the vector */
            if (exVersion == null)
            {
                return retVector;
            }

            /*
             * version number have been read as the first element in return
             * vector
             */
            if (retVector.firstElement().toLowerCase().contains(COMMENTSTR))
            {
                Object arr[] = getAttrNV(retVector.firstElement());
                version = new Version((String) arr[1]);
                retVector.removeElementAt(0);
                /*
                 * only version number and no attributes were present in the
                 * entry, hence return the empty vector
                 */
                if (retVector.size() == 0)
                    return retVector;
            }

            /* perform the checks for version number */
            if (version == null)
            {
                throw new UtilException(
                        "Version not present for the ldifEntry ["
                                + retVector.firstElement() + "]");
            }
            else if (!version.isValidVersion())
            {
                throw new UtilException(
                        "Invalid Version present for ldifEntry ["
                                + retVector.firstElement() + "]");
            }
            else if (exVersion.compareTo(version) >= 0)
            {
                m_logger.log(Level.INFO,
                        "Excluding ldifEntry [" + retVector.firstElement()
                                + "] version [" + version.getVersionString()
                                + "]");
                retVector.removeAllElements();
            }
        }
        catch (UtilException ue)
        {
            if (ignoreError)
            {
                m_logger.log(Level.SEVERE, "Ignoring Error:" + ue);
                retVector.removeAllElements();
                return retVector;
            }
            else
            {
                throw ue;
            }
        }
        return retVector;
    }

    /**
     * A helper method to convert the string to a byte sequence based on the
     * encoding.
     */
    private byte[] string2bytes(String attrStr)
    {
        byte[] retBytes = null;
        try
        {
            retBytes = attrStr.getBytes(this.m_encoding);
        }
        catch (UnsupportedEncodingException uee)
        {
            // This should never happen. Check for the encoding
            // support must have ideally happened at the beginning.
            // System.err.println(uee.getMessage());
            m_logger.log(Level.INFO, uee.getMessage());
        }

        return retBytes;
    }

    /**
     * A helper method to return a string in a particular encoding based on the
     * byte sequence of that encoding.
     */
    private String bytes2string(byte[] bytes)
    {
        String retStr = null;
        try
        {
            retStr = new String(bytes, this.m_encoding);
        }
        catch (UnsupportedEncodingException uee)
        {
            // This should never happen. Check for the encoding
            // support must have ideally happened at the beginning.
            // System.err.println(uee.getMessage());
            m_logger.log(Level.INFO, uee.getMessage());
        }

        return retStr;
    }

    /**
     * This method returns an array containing 3 elements. [0] - Attribute Name,
     * [1] - Attriubte Value, [2] - Binary or not
     * 
     * @param attrNV
     *            A string that is of the form name:value or name::value. in
     *            case of name::value the value will be decoded.
     * @return an array of three objects. If it is a binary attribute then a
     *         byte [] will be returned as the 2 element of the array; otherwise
     *         it will be a string object. The 3 contains 0 or 1 0 - Non Binary
     *         and 1 specifies Binary.
     */
    private Object[] getAttrNV(String attrNV) throws IOException,UtilException
    {
        if (attrNV == null)
            return null;

        Object retArr[] = new Object[3];

        int endIndex = 0;
        int startIndex = 0;
        int len = attrNV.length();
        boolean base64Encoded = false;
        boolean urlAttribValue = false;
        String attrName = null;
        String attrValue = "";
        byte[] byteValue = null;

        retArr[2] = "0";

        /* get the attribute name */
        endIndex = attrNV.indexOf(":");
        if (endIndex == -1)
        {
            System.out
                .println("Invalid Attribute specified in the source file at Line: "
                    + currLineNo);
           throw new UtilException("Invalid Attribute specified in the source file at Line: "
                    + currLineNo);
        }
        attrName = attrNV.substring(startIndex, endIndex).trim();

        /* when attribute name is not specified but, : is specified */
        if (attrName.equals(""))
        {
            System.out
                .println("Invalid Attribute specified in the source file at Line: "
                    + currLineNo);
            throw new UtilException("Invalid Attribute specified in the source file at Line: "
                    + currLineNo);
        }
        /* check whether the value is base 64 encoded. */
        if (endIndex + 1 < len)
        {
            if (attrNV.charAt(endIndex + 1) == ':')
            {
                endIndex++;
                base64Encoded = true;
            }
            else if (attrNV.charAt(endIndex + 1) == '<')
            {
                endIndex++;
                urlAttribValue = true;
            }
        }

        startIndex = endIndex + 1;
        endIndex = len;

        /* get the attibute value */
        attrValue = attrNV.substring(startIndex, endIndex).trim();

        if (urlAttribValue)
        {
            byteValue = readFile(attrValue);
            if (isBinaryAttribute(attrName))
                retArr[2] = "1"; // Binary Attribute
            else
                attrValue = bytes2string(byteValue);
        }
        else if (base64Encoded)
        {
            if (isBinaryAttribute(attrName))
            {
                byteValue = Base64.decode(string2bytes(attrValue));
                retArr[2] = "1"; // Binary Attribute
            }
            else
            {
                // After decoding the B64 String the resultant byte array must
                // be
                // treated as UTF-8 byte sequence.
                try
                {
                    attrValue = new String(Base64.decode(attrValue.getBytes()),
                        "UTF8");
                }
                catch (UnsupportedEncodingException uee)
                {
                    attrValue = null;
                    // System.err.println(uee.getMessage());
                    m_logger.log(Level.INFO, uee.getMessage());
                }
            }
        }
        else if (attrValAsFileContent && !attrValue.equals("")
            && (attrValue.startsWith("/") || attrValue.indexOf(":") == 1))
        {
            byteValue = readFile(attrValue);
            if (isBinaryAttribute(attrName))
                retArr[2] = "1";
            else
                attrValue = bytes2string(byteValue);
        }

        retArr[0] = attrName;
        retArr[1] = (retArr[2].equals("1")) ? (Object) byteValue : attrValue;

        return retArr;
    }

    /**
     * To read from an url
     */
    private byte[] readFile(String urlStr) throws IOException
    {
        byte[] retArr = null;
        try
        {
            BufferedInputStream bufStream = null;

            // NT filename convention.
            // The actual value needs to be read from the file
            if (urlStr.startsWith("/") || urlStr.indexOf(":") == 1)
            {
                File valFile = new File(urlStr);
                bufStream = new BufferedInputStream(new FileInputStream(
                    new File(urlStr)), 4096);
            }
            else
            {
                bufStream = new BufferedInputStream(new URL(urlStr)
                    .openStream(), 4096);
            }

            retArr = new byte[bufStream.available()];

            bufStream.read(retArr, 0, retArr.length);
            bufStream.close();
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
            throw ioe;
        }

        return retArr;
    }

    /**
     * A helper method to handle, when the record changetype is moddn or modrdn
     * 
     * @param ldifRecord
     *            that needs to be updated.
     * @throws IOException
     *             if an I/O error occurs.
     */
    private void handleRecChangeTypeModDn(LDIFRecord ldifRecord)
        throws IOException,UtilException
    {
        ldifRecord.setChangeType(LDIF.RECORD_CHANGE_TYPE_MODDN);
        String attrNV = readAttributeLine(false);
        if (attrNV == null)
        {
            System.out.println("New RDN Expected at Line: " + currLineNo);
            throw new UtilException("New RDN Expected at Line: " + currLineNo);
        }
        Object attrNVArr[] = getAttrNV(attrNV);
        String attrName = (String) attrNVArr[0];
        String attrValue = (String) attrNVArr[1];

        /* should be followed by newrdn:value */
        if (!attrName.equalsIgnoreCase("newrdn") || attrValue == null
            || attrValue.equals(""))
        {
            System.out.println("New RDN Expected at Line: " + currLineNo);
            throw new UtilException("New RDN Expected at Line: " + currLineNo);
        }

        boolean deleteOldRdn = false;
        String newSuperior = null;
        String newAttrNV = readAttributeLine(false);

        /* deleteoldrdn:0/1 and newsuperior is optional */

        for (; (null != newAttrNV);)
        {
            Object[] newArr = getAttrNV(newAttrNV);
            String newAttrName = (String) newArr[0];
            String newAttrVal = (String) newArr[1];

            if (newAttrName.equalsIgnoreCase("deleteoldrdn"))
            {
                if ((newAttrVal == null)
                    || (!(newAttrVal.equals("1") || newAttrVal.equals("0"))))
                {
                    System.out
                        .println("ATTRIBUTE_DELETEOLDRDN_0/1_EXPECTED at Line: "
                            + currLineNo);
                    throw new UtilException("ATTRIBUTE_DELETEOLDRDN_0/1_EXPECTED at Line: "
                            + currLineNo);
                }
                deleteOldRdn = newAttrVal.equals("1");
                newAttrNV = readAttributeLine(false);
                continue;
            }
            else if (newAttrName.equalsIgnoreCase("newsuperior"))
            {
                if ((newAttrVal == null) || newAttrVal.equals(""))
                {
                    System.out.println("NEWSUPERIOR_VALUE_EXPECTED at Line: "
                        + currLineNo);
                    throw new UtilException("NEWSUPERIOR_VALUE_EXPECTED at Line: "
                            + currLineNo);
                }
                newSuperior = newAttrVal;
                newAttrNV = readAttributeLine(false);
                continue;
            }
            else
            {
                System.out.println("Unknown Attribute at Line: " + currLineNo);
                throw new UtilException("Unknown Attribute at Line: " + currLineNo);
            }
        }
        ldifRecord.setNewRdn(attrValue, deleteOldRdn, newSuperior);
    }

    /**
     * A helper method to handle, when the record changetype is delete
     * 
     * @param ldifRecord
     *            that needs to be updated.
     * @throws IOException
     *             if an I/O error occurs.
     */
    private void handleRecChangeTypeDelete(LDIFRecord ldifRecord)
        throws IOException,UtilException
    {
        ldifRecord.setChangeType(LDIF.RECORD_CHANGE_TYPE_DELETE);
        /*
         * incase of changetype:delete the ldifrecord should not contain any
         * attributes.
         */
        String attrNV = readAttributeLine(false);
        if (attrNV != null)
        {
            Object[] arr = getAttrNV(attrNV);
            String attrName = (String) arr[0];
            String attrValue = (String) arr[1];

            if (attrName.equalsIgnoreCase("orclguid"))
            {
                ldifRecord.add(attrName, attrValue,
                    LDIF.ATTRIBUTE_CHANGE_TYPE_DELETE);

                attrNV = readAttributeLine(false);
                if (attrNV == null)
                    return;
            }

            System.out.println("Attribute not expected at Line: " + currLineNo);
            throw new UtilException("Attribute not expected at Line: " + currLineNo);
        }
    }

    /**
     * A helper method to handle, when the record changetype is modify
     * 
     * @param ldifRecord
     *            the LDIFRecord that needs to be updated.
     * @throws IOException
     *             if an I/O error occurs.
     */
    private void handleRecChangeTypeModify(LDIFRecord ldifRecord)
        throws IOException,UtilException
    {
        ldifRecord.setChangeType(LDIF.RECORD_CHANGE_TYPE_MODIFY);

        String attrNV = null;
        boolean atLeastOneAttribute = false;

        /* reading the attributes of this entry. */
        while ((attrNV = readAttributeLine(false)) != null)
        {
            Object[] arr = getAttrNV(attrNV);
            String attrName = (String) arr[0];
            String attrValue = (String) arr[1];
            int atrChangeType = LDIF.ATTRIBUTE_CHANGE_TYPE_ADD;
            boolean isBinaryAttr = false;

            if (attrName.equalsIgnoreCase("changetype"))
            {
                System.out
                    .println("Record Change Type already Specified.Error at Line: "
                        + currLineNo);
                throw new UtilException("Record Change Type already Specified.Error at Line: "
                        + currLineNo);
            }

            if (attrName.equalsIgnoreCase("delete"))
                atrChangeType = LDIF.ATTRIBUTE_CHANGE_TYPE_DELETE;
            else if (attrName.equalsIgnoreCase("replace"))
                atrChangeType = LDIF.ATTRIBUTE_CHANGE_TYPE_REPLACE;
            else if (!attrName.equalsIgnoreCase("add"))
            {
                System.out.println("Invalid Attribute Change Type at Line: "
                    + currLineNo);
                throw new UtilException("Invalid Attribute Change Type at Line: "
                        + currLineNo);
            }
            /* this is of the form add: , replace: , delete: without a value */
            if (attrValue == null || attrValue.equals(""))
            {
                System.out.println("Attribute Value Mandatory.Line: "
                    + currLineNo);
                throw new UtilException("Attribute Value Mandatory.Line: "
                        + currLineNo);
            }
            attrName = attrValue;
            isBinaryAttr = isBinaryAttribute(attrName);
            LDIFAttribute attribute = new LDIFAttribute(attrName, isBinaryAttr);

            attribute.setChangeType(atrChangeType);
            String nextAttrNV = null;

            /*
             * get the subsequent attributes util a line containing a "-" is
             * returned or end of entry is reached.
             */
            while (true)
            {
                nextAttrNV = this.readAttributeLine(false);
                /*
                 * whenever a change type is specified then it must end with a
                 * '-' in a new line. If it is the last attribute of the entry
                 * then it may not end with '-'
                 */
                if (nextAttrNV == null || nextAttrNV.trim().equals("-"))
                    break;

                Object[] nextArr = getAttrNV(nextAttrNV);
                String nextAttrName = (String) nextArr[0];
                String nextAttrVal = null;
                byte[] nextAttrByteVal = null;

                if (isBinaryAttr)
                    nextAttrByteVal = (byte[]) nextArr[1];
                else
                    nextAttrVal = (String) nextArr[1];

                /*
                 * only the attribute of the change type specified should
                 * present
                 */
                if (!nextAttrName.equalsIgnoreCase(attrName))
                {
                    System.out.println("Attribute Type Name Mismatch at Line: "
                        + currLineNo);
                    throw new UtilException("Attribute Type Name Mismatch at Line: "
                            + currLineNo);
                }

                if (isBinaryAttr)
                    attribute.addValue(nextAttrByteVal);
                else
                    attribute.addValue(nextAttrVal);

                atLeastOneAttribute = true;
            }

            /* incase of add value at least one name:value must be specified */
            if (!atLeastOneAttribute
                && (atrChangeType == LDIF.ATTRIBUTE_CHANGE_TYPE_ADD))
            {
                System.out
                    .println("Atleast one NAME:VALUE must be specified for ADD.Error at Line: "
                        + currLineNo);
                throw new UtilException("Atleast one NAME:VALUE must be specified for ADD.Error at Line: "
                        + currLineNo);
            }
            ldifRecord.addAttribute(attribute);

            /* end of entry has been reached */
            if (nextAttrNV == null)
                return;
        }
    }

    /**
     * A helper method to handle ldif change record.
     * 
     * @param ldifRecord
     *            the ldifrecord that needs to be updated.
     * @param changeType
     *            any one of the following strings <I>add</I> or <I>delete</I>
     *            or <I>moddn/modrdn</I> or <I>modify</I>
     * @throws IOException
     *             if an I/O error occurs.
     */
    private void handleChangeRecord(LDIFRecord ldifRecord, String changeType)
        throws IOException, UtilException
    {
        String attrNV = null;

        /* when no change type value is specified */
        if (changeType == null || changeType.equals(""))
        {
            System.out.println("INVALID_RECORD_CHANGE_TYPE at Line: "
                + currLineNo);
            throw new UtilException("INVALID_RECORD_CHANGE_TYPE at Line: "
                + currLineNo);
        }
        /* set the record change type */
        if (changeType.equalsIgnoreCase("delete"))
        {
            handleRecChangeTypeDelete(ldifRecord);
        }
        else if (changeType.equalsIgnoreCase("modify"))
        {
            handleRecChangeTypeModify(ldifRecord);
        }
        else if (changeType.equalsIgnoreCase("modrdn")
            || changeType.equalsIgnoreCase("moddn"))
        {
            handleRecChangeTypeModDn(ldifRecord);
        }
        else if (changeType.equalsIgnoreCase("add"))
        {
            handleAttrValueRecord(ldifRecord);
        }
        else
        {
            System.out.println("INVALID_RECORD_CHANGE_TYPE at Line: "
                + currLineNo);
            throw new UtilException("INVALID_RECORD_CHANGE_TYPE at Line: "
                + currLineNo);
        }
        ldifRecord.setExpChgType(true);
    }

    /**
     * A helper method to handle ldif attribute value record.
     * 
     * @param ldifRecord
     *            the ldifrecord that needs to be updated.
     * @throws IOException
     *             if an I/O error occurs.
     */
    private void handleAttrValueRecord(LDIFRecord ldifRecord)
        throws IOException, UtilException
    {
        ldifRecord.setChangeType(LDIF.RECORD_CHANGE_TYPE_ADD);
        String attrNV = null;

        /* reading the attributes of this entry. */
        while ((attrNV = readAttributeLine(false)) != null)
        {
            Object[] arr = getAttrNV(attrNV);
            String attrName = (String) arr[0];
            String attrValue = null;
            byte[] attrByteVal = null;
            LDIFAttribute attribute = ldifRecord.getAttribute(attrName);

            if (arr[2].equals("1"))
                attrByteVal = (byte[]) arr[1];
            else
                attrValue = (String) arr[1];

            if (attribute == null)
            {
                /* null value will not be added to the attribute list */
                if (arr[2].equals("1"))
                    attribute = new LDIFAttribute(attrName, attrByteVal, true);
                else
                    attribute = new LDIFAttribute(attrName, attrValue);

                ldifRecord.addAttribute(attribute);
            }
            else
            {
                /* the attribute is already existing only adding the value */
                if (arr[2].equals("1"))
                    attribute.addValue(attrByteVal);
                else
                    attribute.addValue(attrValue);
            }
        }
    }

    /**
     * Returns the next record in the LDIF file. Using this method you can
     * iterate thru all the entries in the LDIF file.
     * 
     * @return the next entry as a LDIFRecord object. A null is returned if
     *         there are no more entries.
     * @exception IOException
     *                If an I/O error occurs.
     */
    public synchronized LDIFRecord nextRecord() throws IOException,UtilException
    {
        LDIFRecord ldifRecord = null;
        String attrNV = null;

        /* reading the attributes of this entry. */
        if ((attrNV = readAttributeLine(false)) != null)
        {
            Object[] arr = getAttrNV(attrNV);
            String attrName = (String) arr[0];
            String attrValue = (String) arr[1];
            String attrByteValue = null;

            /* first attribute must be dn */
            if (!attrName.equalsIgnoreCase("dn"))
            {
                System.out.println("DN not specified first.Error at Line: "
                    + currLineNo);
                throw new UtilException("DN not specified first.Error at Line: "
                        + currLineNo);
            }

            /* If dn value is not specified then it refers to rootDSE */
            ldifRecord = new LDIFRecord(attrValue);

            attrNV = readAttributeLine(false);
            /* if attrNV is null then the record contains only dn attrib */
            if (attrNV != null)
            {
                arr = getAttrNV(attrNV);
                attrName = (String) arr[0];

                if (attrName.equalsIgnoreCase("changetype"))
                    handleChangeRecord(ldifRecord, (String) arr[1]);
                else
                {
                    /*
                     * the change type is default change type which is
                     * LDIF.RECORD_CHANGE_TYPE_ADD
                     */
                    LDIFAttribute ldifAttr = (arr[2].equals("1")) ? new LDIFAttribute(
                        attrName, (byte[]) arr[1], true)
                        : new LDIFAttribute(attrName, (String) arr[1]);
                    ldifRecord.addAttribute(ldifAttr);
                    handleAttrValueRecord(ldifRecord);
                }
            }
        }

        return ldifRecord;
    }

    /**
     * Closes the stream.
     * 
     * @exception IOException
     *                If an error occurs.
     */
    public void close() throws IOException
    {
        if (inBuf != null)
        {
            inBuf.close();
            inBuf = null;
        }
    }

    /**
     * Return the version attribute specified in the LDIF file.
     * 
     * @return the version string specified in the LDIF file.
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * if set, the attribute value will be treated as a file name and the
     * contents of which will be taken as the attribute value. in case of unix
     * the file name begins with '/' and in case of NT the file name contains
     * ':'
     * 
     * @param bool
     *            boolean value to be set.
     */
    public void setReadFileContentForAttrib(boolean bool)
    {
        attrValAsFileContent = bool;
    }

    /**
     * Test Driver1 Using the LDIFRecord to store the attribute vale:name
     */
    private static void test1(String file)
    {
        LDIFReader ldifReader = null;
        String[] bAttrs = { "jpegphoto" };
        try
        {
            ldifReader = new LDIFReader(file);
            ldifReader.setBinaryAttributes(bAttrs);
        }
        catch (Exception e)
        {
            System.err.println("Error while reading LDIF file " + file);
            System.err.println(e.toString());
            return;
        }
        try
        {
            LDIFRecord record = null;
            while ((record = ldifReader.nextRecord()) != null)
                System.out.println(record);

            ldifReader.close();
        }
        catch (UtilException ue)
        {
            System.out.println("Error while reading the file: " + ue.getMessage());
        }
        catch (IOException ex)
        {
            System.out.println("Error while reading the LDIF File " + file);
        }
    }

    /**
     * Test Driver2 Using the LDIFRecord to store the attribute vale:name
     */
    private static void test2(String file) 
    {
        LDIFReader ldifReader = null;
        try
        {
            ldifReader = new LDIFReader(file);
        }
        catch (Exception e)
        {
            System.err.println("Error while reading LDIF file " + file);
            System.err.println(e.toString());
            return;
        }
        try
        {
            String attr = null;
            Vector vect = null;
            int i = 0;

            while ((vect = ldifReader.nextEntry()) != null)
            {
                for (int ix = 0; ix < vect.size(); ix++)
                    System.out.println(vect.elementAt(ix));
                System.out.println();
            }
            ldifReader.close();
        }
        catch (UtilException ue)
        {
            System.out.println("Error while reading the file: " + ue.getMessage());
        }
        catch (IOException ex)
        {
            System.out.println("Error while reading the LDIF File " + file);
        }
    }

    /*
     * Test driver - to read the ldif file and print each record.
     * 
     * @param args name of the LDIF file to read
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java LDIFReader <FILE1>");
            System.exit(1);
        }
        // System.out.print(" ##### Test 1 : Using LDIFRecord");
        LDIFReader.test1(args[0]);
        // System.out.print(" ##### Test 2 : Vectors ");
        LDIFReader.test2(args[0]);

    }

}
