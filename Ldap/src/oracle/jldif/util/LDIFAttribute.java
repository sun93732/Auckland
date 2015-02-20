/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    atijain     08/20/09 - Creation
 */
package oracle.jldif.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 * The LDIFAttribute class represents the name and values of an attribute. It 
 * is used to specify an attribute to be added to, deleted  from, or modified 
 * in a Directory entry. It is also returned on a search of a Directory.
 */

public class LDIFAttribute implements Cloneable, Serializable
{
   private static java.util.logging.Logger m_logger =
                    java.util.logging.Logger.getLogger( "oracle.ldap.util" );
   /**
    * Attribute Name.
    */
   private String name = null;

   /**
    * List of Values. Each values is representeds as byte[];
    */
   private Vector<byte[]> values = null;

   /**
    * Attribute Change type constant.
    */
   private int changeType = LDIF.ATTRIBUTE_CHANGE_TYPE_ADD;

   /**
    * Attribute value encoding
    */
   private static final String ENCODING = "UTF8";

   /**
    * Attribute value type. Whether binary or not ?
    */
   private boolean isBinary = false;

   /**
    * Non Printable attributes
    */
   private final static String NON_PRINTABLE_ATTRS = "oracle.idm.util.npattrs";

   /**
    * Constructs an attribute with no values.
    * @param attrName name of the attribute
    */
   public LDIFAttribute( String attrName ) 
   {
      this(attrName, false);
   }

   /**
    * Constructs an attribute with no values.
    * @param attrName name of the attribute
    * @param isBinary whether the attribute is binary or not ?
    */
   public LDIFAttribute( String attrName, boolean isBinary ) 
   {
      this.name = attrName;
      this.values = new Vector<byte[]>();
      this.isBinary = isBinary;
   }

   /**
    * Constructs an attribute with copies of all values of the input 
    * LDIFAttribute.
    * @param ldapAttribute An attribute to use as template.
    */
   public LDIFAttribute( LDIFAttribute ldapAttribute)
   {
      this(ldapAttribute.name, ldapAttribute.isBinary);
      int sz = ldapAttribute.values.size();
      for (int i = 0; i < sz; i++) 
      {
         byte [] valBuf = (byte [])ldapAttribute.values.elementAt(i);
         byte [] newValBuf = new byte[valBuf.length];
         System.arraycopy(valBuf, 0, newValBuf, 0, valBuf.length);
         values.addElement(newValBuf);
      }
   }

   /**
    * Constructs an attribute with a byte-formatted value.
    * @param attrName Name of the attribute
    * @param attrBytes Value of the attribute as raw bytes
    * @param isBinary Wether the attribute is a binary attribute or not ?
    */
   public LDIFAttribute( String attrName, byte[] attrBytes, boolean isBinary )
   {
      this(attrName, isBinary);
      this.addValue(attrBytes);
   }

   /**
    * Constructs an attribute with a byte-formatted value.
    * @param attrName Name of the attribute
    * @param attrBytes Value of the attribute as raw bytes
    */
   public LDIFAttribute( String attrName, byte[] attrBytes ) 
   {
      this(attrName, attrBytes, false);
   }

   /**
    * Constructs an attribute that has a single string value.
    * @param attrName name of the attribute
    * @param attrString value of the attribute in String format
    */
   public LDIFAttribute( String attrName, String attrString ) 
   {
      this(attrName);
      this.addValue(attrString);
   }

   /**
    * Constructs an attribute that has an array of string values.
    * @param attrName name of the attribute
    * @param attrStrings the list of string values for this attribute
    */
   public LDIFAttribute( String attrName, String[] attrStrings ) 
   {
      this(attrName);
      setValues( attrStrings );
   }

   /**
    * A helper method to convert the string to a byte sequence based on 
    * the encoding.
    */
   private static byte [] string2bytes(String attrStr)
   {
      byte [] retBytes = null;
      try {
         retBytes = attrStr.getBytes(ENCODING);
      } catch (UnsupportedEncodingException uee) {
         // This should never happen. Check for the encoding 
         // support must have ideally happened at the beginning.
         // ENCODING -> UTF8. (assumed as default)
         //System.err.println(uee.getMessage());
         m_logger.log(Level.SEVERE, "Exception in string2bytes()", uee);
      }

      return retBytes;
   }

   /**
    * A helper method to return a string in a particular encoding based on
    * the byte sequence of that encoding.
    */
   private static String bytes2string(byte [] bytes)
   {
      String retStr = null;
      try {
         retStr = new String(bytes, ENCODING);
      } catch (UnsupportedEncodingException uee) {
         // This should never happen. Check for the encoding 
         // support must have ideally happened at the beginning.
         // ENCODING -> UTF8. (assumed as default)
         //System.err.println(uee.getMessage());
         m_logger.log(Level.SEVERE, "Exception in bytes2string()", uee);
      }

      return retStr;
   }

   /**
    * Adds a string value to the attribute.
    * @param attrString Value of the attribute as a String. 
    * If the attribute is in Base64 encoded format, then
    * this will decode and store the value
    */
   public synchronized void addValue( String attrString ) 
   {
      if (attrString != null) 
         values.addElement(string2bytes(attrString));
   }

   /**
    * Adds a <CODE>byte[]</CODE>-formatted value to the attribute.
    * @param attrBytes the value of attribute as raw bytes.
    * A copy of the byte sequence will be made and added to the attribute.
    */
   public synchronized void addValue( byte[] attrBytes )
   {
      if (attrBytes != null) 
      {
         byte [] newValBuf = new byte[attrBytes.length];
         System.arraycopy(attrBytes, 0, newValBuf, 0, attrBytes.length);
         values.addElement(newValBuf);
      }
   }

   /**
    * Adds an array of string values to the attribute.
    *
    * @param attrValues array of string values. Note that the
    * strings must be in UTF8 encoding format.
    */
   public synchronized void addValue( String[] attrValues)
   {
      if (attrValues != null) 
      {
         for (int i = 0; i < attrValues.length; i++) 
            this.addValue(attrValues[i]);
      }
   }

   /**
    * Returns an enumerator for the values of the attribute in 
    * <CODE>byte[]</CODE> format.
    * @return a set of attribute values. Each element in the enumeration
    * is of type <CODE>byte[]</CODE>.
    */
   public Enumeration getByteValues() 
   {
      return values.elements();
   }

   /**
    * Returns an enumerator for the string values of an attribute.
    * @return enumerator for the string values.
    */
   public Enumeration getStringValues() 
   {
      return new AttributeValueEnumerator(values, false);
   }

   /**
    * Returns the attribute values as Object array.
    * @return Object[] for the string values.
    *
   public synchronized Object[] getValsAsObject() 
   {
      Object [] retObj =  null;

      if ( this.isBinary() )
      {
         Enumeration values =  this.getByteValues();
         Vector attrVals = new Vector();

         while ( values.hasMoreElements() )
         {
            byte byteValues[] = (byte[]) values.nextElement();
            attrVals.addElement(byteValues);
         }
         retObj = attrVals.toArray();
      }
      else 
      {
         retObj = (String[]) getStringValueArray();
      }

      return retObj;
   }*/
	
   /**
    * Returns the attributes as enumeration. In case of binary attribute 
    * the enumeration will contain byte[] and in case of no binary attributes
    * it will be String element
    * @return Enumeration of object
    */
   public synchronized Enumeration getValsAsObject() 
   {
      return new AttributeValueEnumerator(values, isBinary());
   }
	
   /**
    * Returns the values of the attribute as an array of <CODE>byte[]</CODE>
    * @return array of attribute values in <CODE>byte[]</CODE> format.
    */
   public synchronized byte[][] getByteValueArray() 
   {
      byte retVals[][] = null;
      if ( values.size() != 0 )
      {
         retVals = new byte[values.size()][];
         for (int i=0; i < values.size(); i++) 
         {
            byte ar[] = (byte[]) values.elementAt(i);
            retVals[i] = new byte[ar.length];
            System.arraycopy(ar, 0, retVals[i], 0, ar.length);
         }
    	}
    	return retVals;
   }
   
   /**
    * Returns the values of the attribute as an array of <CODE>Strings</CODE> 
    *
    * @return array of attribute values as <CODE>String</CODE> object.
    */
   public synchronized String[] getStringValueArray() 
   {
      String retVals[] = null;
      if ( values.size() != 0 ) 
      {
         retVals = new String[values.size()];
         for (int i=0; i < values.size(); i++) 
            if ( values.elementAt(i) != null ) 
               retVals[i] = bytes2string((byte[])values.elementAt(i));
      }
      return retVals;
   }

   /**
    * Sets the string values as the attribute's values.
    *
    * @param attrValues an array of string values which represent 
    * the attribute values.
    */
   public synchronized void setValues( String[] attrValues ) 
   {
      if (attrValues != null) 
      {
         values.removeAllElements(); 
         for (int i = 0; i < attrValues.length; i++) 
            values.addElement(string2bytes(attrValues[i]));
      }
   }

   /**
    * Returns the language subtype if any. For example, if the attribute name
    * is <CODE>cn;lang-fr;phonetic</CODE>, this method returns the 
    * String <CODE>lang-fr</CODE>.
    *
    * @return the language subtype, or null (if the name has no
    * language subtype).
    */
   public String getLangSubtype() 
   {
      String [] subTypes = getSubtypes();
      String retString = null;
      if ( subTypes != null ) 
      {
         for( int i = 0; i < subTypes.length; i++ ) 
         {
            String str = subTypes[i].trim(); 
            if ( str.length() < 5 )
               continue;
            if (str.substring(0, 5).equalsIgnoreCase("lang-"))
            {
               retString = str;
               break;
            }
         }
      }
      return retString;
   }

   /**
    * Returns the base name. For example, if the attribute name is 
    * <CODE>cn;lang-fr;phonetic</CODE>, this method returns <CODE>cn</CODE>.
    *
    * @param attrName name of the attribute to extract the base name from
    * @return base name (i.e. the attribute name without subtypes).
    */
   public static String getBaseName(String attrName) 
   {
      String retStr = attrName;
      int index = attrName.indexOf(";");
      if (index != -1)
         retStr = attrName.substring(0,index);
      return retStr;
   }

   /**
    * Returns the base name of this object. For example, if the attribute name 
    * is <CODE>cn;lang-fr;phonetic</CODE>, this method returns <CODE>cn</CODE>.
    *
    * @return base name (i.e. the attribute name without subtypes).
    */
   public String getBaseName() 
   {
       return getBaseName(this.name);
   }

   /**
    * Returns the name of the attribute.
    *
    * @return Attribute name.
    */
   public String getName() 
   {
      return this.name;
   }

   /**
    * Extracts the subtypes from the specified attribute name.
    * For example, if the attribute name is <CODE>cn;lang-fr;phonetic</CODE>,
    * this method returns an array containing <CODE>lang-fr</CODE> and 
    * <CODE>phonetic</CODE>.
    *
    * @param attrName name of the attribute to extract the subtypes from.
    * @return array of subtypes, or null (if no subtypes).
    */
   public static String[] getSubtypes(String attrName) 
   {
      StringTokenizer st = new StringTokenizer(attrName, ";");
      String [] retVals = null;
      if ( st.hasMoreElements() ) 
      {
         /* Ignore the name of the attribute.*/
         st.nextElement();
         retVals = new String[st.countTokens()];
         for (int i=0;st.hasMoreElements(); i++)
            retVals[i] = (String)st.nextElement();
      }
      return retVals;
   }

   /**
    * Extracts the subtypes from the attribute name of this object.
    * For example, if the attribute name is <CODE>cn;lang-fr;phonetic</CODE>,
    * then this method returns an array containing <CODE>lang-ja</CODE> 
    * and <CODE>phonetic</CODE>.
    *
    * @return array of subtypes, or null (if no subtypes).
    */
   public String[] getSubtypes() 
   {
      return getSubtypes(this.name);
   }

   /**
    * Reports whether the attribute name contains the specified subtype.
    * For example, if you check for the subtype <CODE>lang-fr</CODE>
    * and the attribute name is <CODE>cn;lang-fr</CODE>, this method
    * returns <CODE>true</CODE>.
    *
    * @param subtype the single subtype to check for
    * @return true if the attribute name contains the specified subtype.
    */
   public boolean hasSubtype(String subtype) 
   {
      String[] subtypes = getSubtypes();
      for(int i = 0; i < subtypes.length; i++) 
      {
         if( subtype.equalsIgnoreCase( subtypes[i] ) )
            return true;
      }
      return false;
   }

   /**
    * Reports if the attribute name contains all specified subtypes.
    * For example, if you check for the subtypes <CODE>lang-fr</CODE>
    * and <CODE>phonetic</CODE> and if the attribute name is
    * <CODE>cn;lang-fr;phonetic</CODE>, this method returns <CODE>true</CODE>.
    * If the attribute name is <CODE>cn;phonetic</CODE> or
    * <CODE>cn;lang-fr</CODE>, this method returns <CODE>false</CODE>.
    *
    * @param subtypes an array of subtypes to check for
    * @return true if the attribute name contains all subtypes
    */
   public boolean hasSubtypes(String[] subtypes) 
   {
      for(int i = 0; i < subtypes.length; i++) 
         if( !hasSubtype(subtypes[i]) )
            return false;

      return true;
   }

   /**
    * Removes a string value from the attribute.
    *
    * @param attrString the string value to remove
    */
   public synchronized boolean removeValue( String attrString ) 
   {
      boolean retBool = false;
      if ((attrString != null) && (values.size() != 0))
      {
         for (int i=0; i<values.size(); i++) 
         {
            String attrVal = bytes2string((byte[])values.elementAt(i));
            if ( attrVal.equalsIgnoreCase(attrString) )
            {
               values.removeElementAt(i);
               retBool = true;
               break;
            }
         }
      }

      return retBool;
   }

   /**
    * Removes a <CODE>byte[]</CODE>-formatted value from the attribute.
    *
    * @param attrBytes <CODE> byte[]</CODE>-formatted value to remove
    */
   public synchronized void removeValue( byte[] attrBytes )
   {
      if ((attrBytes != null) && (values.size() != 0))
      {
         for (int i=0; i<values.size(); i++) 
         {
            byte [] bytes = (byte[])values.elementAt(i);
            if ( LDIFAttribute.isEqual(bytes, attrBytes) )
            {
               values.removeElementAt(i);
               break;
            }
         }
      }
   }

   /**
    * removes All the values of the attributes
    */
   public synchronized void removeAll() 
   {
      values.removeAllElements(); 
   }

   /**
    * Returns the number of values of the attribute.
    * @return number of values for this attribute.
    */
   public int size() 
   {
      return values.size();
   }

   /**
    * Return this the change type associated with this attribute(if any).
    *
    * @return a Change Type constant defined in the LDIF class 
    */
   public int getChangeType()
   {
      return changeType; 
   }

   /**
    * Sets the change type for this attribute.
    *
    * @param changeType Change Type constant defined in the LDIF class 
    */
   public void setChangeType(int changeType)
   {
      this.changeType = changeType;
   }
   
   /**
    * Retruns the value of a single value attribute. In case of a multivalued 
    * attribute the first value is returned. If the attribute does contain not
    * any value then null is returned.
    * @return an attribute value or null if the attribute has no value.
    */
   public String getValue()
   {
      String retVal = null;

      if (values.size() > 0)
         retVal = bytes2string((byte[])values.elementAt(0));

      return retVal;
   }
   
   /**
    * Reports whether this object contains the specified attribute value.
    * @param attrString value as <CODE>String</CODE> object that needs to
    * be checked for
    * @return true if the attribute contains the specified value, else false.
    */
   public synchronized boolean contains(String attrString)
   {
      if ( (attrString == null) || (values.size() == 0) )
         return false;
      
      for(int i=0; i < values.size(); i++)
      {
         String attrVal = bytes2string((byte[])values.elementAt(i));
         if ( attrVal.equalsIgnoreCase(attrString) )
            return true;
      }

      return  false;
   }
   
   /**
    * Reports whether this object contains the specified attribute value.
    * @param attrBytes - value as <CODE>byte[]</CODE> formatted representation
    * that needs to be checked for
    * @return true if the attribute contains the specified value, else false.
    */
   public synchronized boolean contains(byte []attrBytes)
   {
      if ( (attrBytes == null) || (values.size() == 0) )
         return false;
      
      for(int i=0; i < values.size(); i++)
      {
         byte [] byteArr = (byte[])values.elementAt(i);
         if ( LDIFAttribute.isEqual(byteArr, attrBytes) )
            return true;
      }

      return  false;
   }

   /**
    * Returns an object of <CODE>javax.naming.directory.Attribute</CODE>
    * that describes the attribute
    *
    * @return javax.naming.directory.Attribute object describing the attribute
    */
   public Attribute getJNDIAttribute()
   {
      /* creating an Attribute which will hold an ordered list of values */
      Attribute retAttr = new BasicAttribute(name, true);
      
      /* add all the values to the attribute created above */
      for (int ix = 0; ix < values.size(); ix++ )
         retAttr.add(values.elementAt(ix));

      return retAttr;
   }

   /**
    * Returns an object of <CODE>javax.naming.directory.ModificationItem </CODE>
    * that represents a JNDI modification item.
    *
    * @return javax.naming.directory.ModificationItem that represents a 
    * modification item
    */
   public ModificationItem getJNDIModificationItem()
   {
      Attribute attr = this.getJNDIAttribute();

      /* default mod operation is add */
      int modOp = DirContext.ADD_ATTRIBUTE;

      if (this.getChangeType() == LDIF.ATTRIBUTE_CHANGE_TYPE_REPLACE)
         modOp = DirContext.REPLACE_ATTRIBUTE;
      else if (this.getChangeType() == LDIF.ATTRIBUTE_CHANGE_TYPE_DELETE)
         modOp = DirContext.REMOVE_ATTRIBUTE;

      ModificationItem retModItem = new ModificationItem(modOp, attr);
         
      return retModItem;
   }

   /**
    * Helper Method, returns true if contents of both the byte arrays are same.
    *
    * @param byteArr1 Byte array 1.
    * @param byteArr2 Byte array 2.
    */
   private static boolean isEqual(byte[] byteArr1, byte[] byteArr2) 
   {
      if (byteArr1.length != byteArr2.length)
         return false;

      for (int i=0; i<byteArr2.length; i++) 
         if (byteArr2[i] != byteArr1[i])
            return false;
        
      return true;
   }

   /**
    * Retrieves the string representation of an attribute
    * in an LDAP entry.
    * 
    * @param asComment will return the attribute string with '#' prepended.
    * @return string representation of the attribute.
    */
   public String getAttributeAsString(boolean asComment)
   {
      return getAttributeAsString(asComment, true);
   }

   /**
    * Retrieves the string representation of an attribute
    * in an LDAP entry.
    * 
    * @param asComment will return the attribute string with '#' prepended.
    * @param wrap if true the line will be wrapped and continued in the next
    * line with ' ' at the begining.
    * @return string representation of the attribute.
    */
   public String getAttributeAsString(boolean asComment, boolean wrap)
   {
      StringBuffer retBuf = new StringBuffer();
      String newLine = System.getProperty("line.separator");

      if (changeType == LDIF.ATTRIBUTE_CHANGE_TYPE_ADD)
         retBuf.append(LDIFAttribute.getAttrAsLDIFStr("add", name,
                                     asComment, wrap)).append(newLine);
      else if (changeType == LDIF.ATTRIBUTE_CHANGE_TYPE_DELETE)
         retBuf.append(LDIFAttribute.getAttrAsLDIFStr("delete", name,
                                     asComment, wrap)).append(newLine);
      else if (changeType == LDIF.ATTRIBUTE_CHANGE_TYPE_REPLACE)
         retBuf.append(LDIFAttribute.getAttrAsLDIFStr("replace", name,
                                     asComment, wrap)).append(newLine);

      for (int i=0;i< values.size();i++)
      {
         byte [] value =  (byte[]) values.elementAt(i);
         retBuf.append(getAttrAsLDIFStr(name, value, asComment, wrap, isBinary))
               .append(newLine);
      }

      if ( asComment )
         retBuf.append("#");

      retBuf.append("-").append(newLine);

      return retBuf.toString();
   }

   /**
    * Retrieves the string representation of an attribute
    * in an LDAP entry.
    * 
    * @param type attribute name
    * @param val attribute value
    * @param asComment will return the attribute string with '#' prepended.
    * @param wrap if true the line will be wrapped and continued in the next
    * line with ' ' at the begining.
    * @return string representation of the attribute.
    */
   static String getAttrAsLDIFStr(String type, String val, boolean asComment, 
                                          boolean wrap )
   {
      return getAttrAsLDIFStr(type, string2bytes(val), asComment, wrap, false);
   }

   /**
    * Retrieves the string representation of an attribute
    * in an LDAP entry.
    * 
    * @param type attribute name
    * @param val attribute value
    * @param asComment will return the attribute string with '#' prepended.
    * @param wrap if true the line will be wrapped and continued in the next
    * line with ' ' at the begining.
    * @param isBinary if true the value will be treated as raw sequence of 
    * bytes end encode to Base64 format.
    * @return string representation of the attribute.
    */
   static String getAttrAsLDIFStr(String type, byte [] val, boolean asComment, 
                                          boolean wrap, boolean isBinary)
   {
      StringBuffer sbuf = new StringBuffer();
      String newLine = System.getProperty("line.separator");
      String cmt = "#";
      int maxLineLen = 77;
      int startIndex = 0;
      int endIndex = 0;
      int len = 0;
      boolean firstLine = true;
      String newStr = bytes2string(val);
      String tmpStr = ": ";

      if ( isAttrNonPrintable(type) )
      {
        // When the attribute is defined as not printable
        // oracle.idm.util.npattrs system prop has list of attributes
        // that are defined to be non printable
        newStr = "*****";  
      }

      if ( isBinary )
      {
         byte [] encodedBytes = Base64.encode(val);
         tmpStr = ":: " + new String(encodedBytes);
      }
      else 
      {
         tmpStr += newStr;
         if ( newStr.startsWith(" ") || newStr.startsWith(":") )
         {
            tmpStr = ":: " + Base64.encode(newStr);
         }
         else  
         {
            for ( int i = 0; i < val.length; i++)
            {
               if ( ! isAscii(val[i]) || val[i] < 32 )
               {
                  tmpStr = ":: " + new String(Base64.encode(val));
                  break;
               }
            }
         }
      }

      tmpStr = type + tmpStr;
      len = tmpStr.length();

      endIndex = Math.min(len, maxLineLen);

      if ( wrap )
      {
         /* 
          * to break the line into multiple lines based on the max
          * width which is 77 chars per line. The new continued line will
          * start with a space.
          */
         while(true)
         {
            if ( asComment && firstLine )
            {
               sbuf.append(cmt);
               if ( endIndex == maxLineLen )
                  endIndex--;
               firstLine = false;
            }

            sbuf.append(tmpStr.substring(startIndex, endIndex));
            startIndex = endIndex;
            endIndex = Math.min(len - endIndex, maxLineLen);

            if (endIndex != 0)
            {
               sbuf.append(newLine);
               if ( asComment )
               {
                  sbuf.append(cmt).append(' ');
                  if ( endIndex == maxLineLen )
                     endIndex -= 2;
               }
               else
               {
                  sbuf.append(' ');
                  if ( endIndex == maxLineLen )
                     endIndex--;
               }
            }
            else
               break;

            endIndex += startIndex;
         }

         tmpStr = sbuf.toString();
      }

      return tmpStr;
   }
   
   private static boolean isAttrNonPrintable(String type)
   {
      boolean  retVal = false;
      String stype = type.toLowerCase();
      String lst = System.getProperty(NON_PRINTABLE_ATTRS);
      if ( lst != null )
      {
        String attrs[] = lst.split("\\s");
        for (int i = 0; ! retVal && attrs != null && i < attrs.length; i++)
          retVal = attrs[i].equalsIgnoreCase(type);
      }
      
      return retVal;
   }

   /**
    * returns ture if the given byte is ASCII. i.e &lt; 128
    */
   private static boolean isAscii(byte b)
   {
      return ( 0 == (b & (~0177)) );
   }

   /**
    * Returns whether the attribute is a Binary attribute or not.
    *
    * @return true if the attribute is created to contain binary values;
    * false otherwise
    */
   public boolean isBinary()
   {
      return isBinary;
   }

   /**
    * set this attribute as binary attribute.
    *
    * @param binary true if the attribute is created to contain binary values;
    * false otherwise
    */
   public boolean setBinary(boolean binary)
   {
      boolean prevVal = isBinary;
      isBinary = binary;

      return isBinary;
   }

   /**
    * Retrieves the string representation of an attribute
    * in an LDAP entry. For example:
    *
    * @return string representation of the attribute.
    */
   public String toString() 
   {
      return getAttributeAsString(false);
   }

   /**
    * Checks for the equality of the attribute with the given one.
    *
    * @param ldifAttr an Object that you want to check for equality 
    * @return ture if ldifAttr has same name as this object then
    */
   public boolean equals(Object ldifAttr)
   {
      boolean retVal = false;
      if (ldifAttr instanceof LDIFAttribute)
         retVal = name.equalsIgnoreCase(((LDIFAttribute)ldifAttr).name);

      return retVal;
   }

   /**
    * Returns a copy of this object
    * 
    * @return Object a copy of this object
    */
   public synchronized Object clone()
   {
      LDIFAttribute retAttr = null;
      Enumeration values = null;

      try {
         retAttr = (LDIFAttribute) super.clone();
      } catch (CloneNotSupportedException e) { 
         // this shouldn't happen, since we are Cloneable
       throw new InternalError();
      }

      retAttr.name = new String(this.name);
      retAttr.values = new Vector<byte[]> ();
      retAttr.isBinary = this.isBinary;

      values = this.getByteValues();
      while ( values.hasMoreElements() )
      {
         byte byteValues[] = (byte[]) values.nextElement();
         retAttr.addValue(byteValues);
      }

      return retAttr;
   }

   final class AttributeValueEnumerator implements Enumeration 
   {
      Vector vector;
      int count;
      boolean asBinary = false;

      AttributeValueEnumerator(Vector v, boolean asBinary) 
      {
         vector = v;
         count = 0;
         this.asBinary = asBinary;
      }

      public boolean hasMoreElements() 
      {
         return count < vector.size();
      }
      
      public Object nextElement() 
      {
         synchronized (vector) 
         {
            if (count < vector.size()) 
            {
               byte []bval =  (byte[])vector.elementAt(count++);
               if ( this.asBinary )
               {
                  byte [] retValBuf = new byte[bval.length];
                  System.arraycopy(bval, 0, retValBuf, 0, bval.length);

                  return retValBuf;
               }
               else
                  return bytes2string(bval);
            }
         }
         throw new NoSuchElementException("Reached End");
      }
   }
}
