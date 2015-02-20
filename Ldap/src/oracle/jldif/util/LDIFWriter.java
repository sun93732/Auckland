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
    kchaubey    07/01/12 - Backport kchaubey_bug-14217989 from main
    atijain     08/20/09 - Creation
 */
package oracle.jldif.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 * LDAP Data Interchange Format (LDIF) is a file format used to
 * represent the Directory entries. The data from the directory can be 
 * exported in this format and could be imported into another directory
 * server. The Import and export of the directory data from an LDAP server
 * can describe a set of changes that can be applied to data in a directory.
 * This format is described in the Internet draft
 * <A HREF="http://www.ietf.org/rfc/rfc2849.txt"
 * TARGET="_blank">The LDAP Data Interchange Format (LDIF) - RFC-2849</A>.
 * <P>
 *
 * This class provides a set of methods to write an LDIF entry to the file. 
 * <P>
 */
public class LDIFWriter {

    /**
    * constant - Line Seperator. The value is line.sperator System property.
    */
   private final static String lineSeparator 
                              = System.getProperty("line.separator"); 
   
    /**
    * Max chars in a line. Default value is 76 chars per line.
    */
   private int maxLineLen = 76;
   
   /**
    * Wrapping of the attribute values should be done or not
    */
   private boolean wrap = true;

    /**
    * constant - ENCODING.
    */
   private final static String ENCODING = "UTF8";
   
   /**
    * constant - DEFAULT_ENCODING - Platforms default encoding
    */
   private static String DEFAULT_ENCODING = null;
   static {
      DEFAULT_ENCODING = System.getProperty("file.encoding");
   }
   
   /**
    * BufferedWriter object which provides an handle to the ldif file 
    */
   private BufferedWriter outBuf = null;

   /**
    * Default Constructor, Creates a Writer stream to the standard output.
    * Since the file name is not specified the LDIF data is redirected
    * to the standard output. The output data is written in UTF8 format.
    * If the attribute value contains more characters than max line len 
    * then it will not be wrapped.
    * @exception IOException An I/O error has occurred.
    */
   public LDIFWriter() throws IOException 
   {
      outBuf = new BufferedWriter(new OutputStreamWriter(System.out, ENCODING));
   }

   /**
    * Creates a Writer stream to the specified file for writing the LDIF data.
    * If the attribute value contains more characters than max line length 
    * then it will not be wrapped.
    * @param file the name of the LDIF file 
    * @exception IOException An I/O error has occurred.
    */
   public LDIFWriter(String file) throws IOException 
   {
      outBuf = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(file), ENCODING));
   }

   /**
    * Creates a Writer stream to the specified file for writing the LDIF data.
    * If the attribute value contains more characters than max line length 
    * then it will not be wrapped.
    * @param fileObj The file oject of the LDIF file 
    * @exception IOException An I/O error has occurred.
    */
   public LDIFWriter(File fileObj) throws IOException 
   {
      outBuf = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(fileObj), ENCODING));
   }

   /**
    * Creates a Writer Stream using the specified output stream object
    * for writing the LDIF data. If the attribute value contains more 
    * characters than max line length then it will not be wrapped.
    * @param out stream on to which the LDIF data needs to be written.
    * @exception IOException An I/O error has occurred.
    */
   public LDIFWriter(OutputStream out) throws IOException 
   {
      outBuf = new BufferedWriter(new OutputStreamWriter(out, ENCODING));
   }

   public LDIFWriter(OutputStream out, String enc) throws IOException 
   {
      if (enc == null)
        enc = DEFAULT_ENCODING;
      outBuf = new BufferedWriter(new OutputStreamWriter(out, enc));
   }

   /**
    * Creates a Writer stream to the specified file for writing the LDIF data.
    * If the attribute value contains more characters than max line length 
    * then it will be wrapped to next line with a space char appended before.
    * @param file the name of the LDIF file 
    * @param wrap if true and the attribute value has more characters than 
    * max line length then the line will be wrapped
    * @exception IOException An I/O error has occurred.
    */
   public LDIFWriter(String file, boolean wrap) throws IOException 
   {
      outBuf = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(file), ENCODING));
      this.wrap = wrap;
   }

   /**
    * Creates a Writer stream to the specified file for writing the LDIF data.
    * If the attribute value contains more characters than max line length 
    * then it will be wrapped to next line with a space the first character.
    * @param fileObj The file oject of the LDIF file 
    * @param wrap if true and the attribute value has more characters than 
    * max line length then the line will be wrapped
    * @exception IOException An I/O error has occurred.
    */
   public LDIFWriter(File fileObj, boolean wrap) throws IOException 
   {
      outBuf = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(fileObj), ENCODING));
      this.wrap = wrap;
   }

   /**
    * Creates a Writer Stream using the specified output stream object
    * for writing the LDIF data. If the attribute value contains more 
    * characters than max line length then it will be wrapped to next 
    * line with a space as the first character.
    * @param out stream on to which the LDIF data needs to be written.
    * @param wrap if true and the attribute value has more characters than 
    * max line length then the line will be wrapped
    * @exception IOException An I/O error has occurred.
    */
   public LDIFWriter(OutputStream out, boolean wrap) throws IOException 
   {
      outBuf = new BufferedWriter(new OutputStreamWriter(out, ENCODING));
      this.wrap = wrap;
   }

   /**
    * Check to make sure that the stream has not been closed 
    * @exception IOException if the stream is closed.
    */
   private void ensureOpen() throws IOException 
   {
   if (outBuf == null)
      throw new IOException("Stream closed");
   }
   
   /**
    * Write a line separator.  The value of the line seperator is as defined
    * by the system property <tt>line.separator</tt>, and it is not neccessary
    * for the value to be a newline ('\n') character.
    *
    * @exception  IOException  If an I/O error occurs
    */
   public void newLine() throws IOException 
   {
      ensureOpen();
      outBuf.write(lineSeparator);
   }

   /**
    * This method writes an attribute to the LDIF file.
    *
    * @param attrString the Attribute Name:Value pair.
    * @exception IOException If an I/O error occurs.
    */
   private void writeAttribute(String attrStr) throws IOException 
   {
      int startIndex = 0;
      int endIndex = 0;
      int len;

      if ( attrStr == null || ( len = attrStr.length()) == 0)
         return;
      else 
      {
         ensureOpen();
         if (!wrap) 
            outBuf.write(attrStr);
         else 
         {
            endIndex = Math.min(len, maxLineLen);
            /* 
             * to break the line into multiple lines based on the max
             * width which is 76 chars per line. The new continued line will
             * start with a space.
             */
            while ( true )
            {
               outBuf.write(attrStr.substring(startIndex,endIndex));
               startIndex = endIndex;
               endIndex = Math.min(len - endIndex, maxLineLen);
               if (endIndex != 0) 
               {
                  newLine();
                  outBuf.write(' ');
               }
               else
                  break;
               endIndex += startIndex;
            }
         }
      }
   }

   /**
    * This method writes an attribute to the LDIF file.
    *
    * @param type Attribute Name.
    * @param value Attribute value.
    * @exception IOException If an I/O error occurs.
    */
   void writeAttribute(String type, String value) throws IOException
   {
      if ( null == type || null == value )
         return;
      else 
      {
         writeAttribute(type + ": " + value);
         newLine();
         outBuf.flush();
      }
   }

   /**
    * Use this method to set the maximum nubmer of chars that could be 
    * written in a line.
    * @param maxLineLen The maximum number of characters in a line.
    */
   public void setMaxLineLen(int maxLineLen) 
   {
      this.maxLineLen = maxLineLen;
   }

   /**
    * Use this method to specify should the wrapping of the attribute 
    * value done or not.
    * @param wrap if true and the attribute value has more characters than 
    * max line length then the line will be wrapped
    */
   public void setWrap(boolean wrap) 
   {
      this.wrap = wrap;
   }

   /**
    * Use this method to write an <CODE>LDIFRecord</CODE> to a file.
    *
    * @param ldifRecord a LDIFRecord object.
    * @exception IOException If an I/O error occurs.
    */
   public void writeEntry(LDIFRecord ldifRecord) throws IOException
   {
      ensureOpen();
      outBuf.write(ldifRecord.getRecordAsString(false, this.wrap));
      outBuf.flush();
   }

   /**
    * Use this method to write an <CODE>LDIFRecord</CODE> to a file as a 
    * commented entry.
    *
    * @param ldifRecord a LDIFRecord object.
    * @exception IOException If an I/O error occurs.
    */
   public void writeEntryAsComment(LDIFRecord ldifRecord) throws IOException
   {
      ensureOpen();
      outBuf.write(ldifRecord.getRecordAsString(true, this.wrap));
      outBuf.flush();
   }

   /**
    * Use this method to write an LDIF entry to the file.
    *
    * @param vEntry A vector containing the Attribute Names:Values as elements.
    * @exception IOException If an I/O error occurs.
    */
   public void writeEntry(Vector vEntry) throws IOException
   {
      int sz;
      if (vEntry == null || (sz = vEntry.size()) == 0) 
         return;
      else
      {
         /* for every attribute in the entry */
         for (int ix = 0;ix < sz;ix++) 
         {
            writeAttribute((String)vEntry.elementAt(ix));
            newLine();
         }
         /* after every entry a new line. */
         newLine();
      }         
   }
    
   /**
    * use this method to add a comment line to the LDIF file.
    *
    * @param comment the Comment string which is to be added to the LDIF file
    * @exception IOException If an I/O error occurs.
    */
   public void writeComment(String comment) throws IOException 
   {
      ensureOpen();
      outBuf.write("# " + comment);
      outBuf.newLine();
   }

   /**
    * Closes the stream.
    * 
    * @exception IOException If an error occurs.
    */
   public synchronized void close() throws IOException
   {
      if (outBuf != null) {
         outBuf.flush();
         outBuf.close();
      }
      outBuf = null;
   }

   /**
    * Test Driver1 Using the LDIFRecord to write the  record.
    */
   private static void test1(String inFile, String outFile)
   {
      LDIFReader ldifReader = null;
      LDIFWriter ldifWriter = null;
      String [] bAttrs = { "jpegphoto" };
      try {
         ldifReader = new LDIFReader( inFile );
         ldifReader.setBinaryAttributes(bAttrs);
         ldifWriter = new LDIFWriter( outFile );
      } catch (Exception e) {
         System.err.println("Error while opening LDIF file ");
         System.err.println(e.toString());
         return;
      }
      try {
         LDIFRecord record = null;
         while ( ( record = ldifReader.nextRecord() ) != null )
         {
            ldifWriter.writeEntry(record);
            ldifWriter.newLine();
         }
         ldifReader.close();
         ldifWriter.close();
      } catch ( UtilException ue ) {
          System.out.println("Error while reading/writing the LDIF File: " + ue);
      }
      catch ( IOException ex ) {
         System.out.println("Error while reading/writing the LDIF File ");
      }
   }

   /**
    * Test Driver2 Using vector representation
    */
   private static void test2(String inFile, String outFile)
   {
      LDIFReader ldifReader = null;
      LDIFWriter ldifWriter = null;
      try {
         ldifReader = new LDIFReader(inFile);
         ldifWriter = new LDIFWriter(outFile);
      } catch (Exception e) {
         System.err.println("Error while opening the LDIF file ");
         System.err.println(e);
         return;
      }
      try {
         Vector vect = ldifReader.nextEntry();
         while (vect != null) {
               ldifWriter.writeEntry(vect);
               vect = ldifReader.nextEntry();
         }
         ldifWriter.close();
      } catch ( UtilException ue ) {
          System.out.println("Error while reading/writing the LDIF File: " + ue);
      }
      catch ( IOException ex ) {
         System.out.println("Error while reading/writing the LDIF File ");
      }
   }

   /*
    * Test driver - to read the ldif file and print each record.
    *
    * @param args name of the LDIF file to read
    */

// JAVADOC_EXCLUDE
   public static void main( String[] args ) 
   {
      if ( args.length != 2 )
      {
         System.out.println( "Usage: java LDIFWriter <FILE1> <FILE2> ...." );
         System.exit( 1 );
      }
      test1(args[0], args[1] + ".1");
      test2(args[0], args[1] + ".2");
      System.exit( 0 );
   }
// END_JAVADOC_EXCLUDE
}
