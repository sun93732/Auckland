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

import java.util.Vector;

/**
 * This class provides some general methods to do the substitution in an
 * LDIF entry. The LDIF entry is represented in a Vector object. The 
 * substitution variables(Name,value) pairs can be provided as a Vector
 * <P> 
 * for example consider an LDIF entry represented in a Vector object
 *
 * <PRE>
 *    Sample LDIF entry in a vector object.
 *    -------------------------------
 *    | dn: uid=Heman,%domain_Name% |  --- Note the Substitution variable
 *    -------------------------------
 *    | ojectClass: top             |
 *    -------------------------------
 *    | %Atr_Typ%: HEMAN            |  --- Note the Substitution variable
 *    -------------------------------
 *    |                             |
 *    -------------------------------
 *    |        .......              |
 *    -------------------------------    
 *
 *    A vector containing the substitution variables(Name,value) pairs;
 *
 *    -------------------------------
 *    | domain Name                 |  --- the Substitution variable Name
 *    -------------------------------
 *    | dc=oracle,dc=com            |  --- the Substitution variable value
 *    -------------------------------
 *    | Atr_Typ                     |
 *    -------------------------------
 *    | uid                         |
 *    -------------------------------
 *
 * Use the Escape character '\', if your data contains a '%' symbol followed
 * by the Substitution variable
 * </PRE>
 * </p>
 * @see LDIFReader
 * @see LDIFWriter
 */
public class LDIFSubstitute
{
   /**
    * constant ESCAPE sequence character.
    */
   private static final char ESCAPE_CHAR = '\\';

   /**
    * This method searchs for the string schStr and replaces all the 
    * occurrences with repStr in any given string. 
    * 
    * @param str the String in which search and replace needs to be done.
    * @param schStr the String to be searched.
    * @param repStr the String which replaces.
    * @return String the replaced string. If search string is not found then
    * the orginal string is returned. If str or schStr or repStr is null then
    * null is returned.
    */
   private static String substitute(String str, String schStr, String repStr)
   {
      StringBuffer retStrBuf = new StringBuffer();
      if (str == null || schStr == null || repStr == null) 
         retStrBuf = null;
      else
      {
         schStr = "%"+schStr+"%";
         int sLen = schStr.length();
         int fromIndex = 0;
         int startIndex = str.indexOf(schStr,fromIndex);
         while (startIndex != -1)
         {
            if (startIndex != 0)
            {
               /* escape the substitution variable */
               if (str.charAt(startIndex - 1) == ESCAPE_CHAR)
               {
                  retStrBuf.append(str.substring(fromIndex,startIndex-1))
                        .append(str.substring(startIndex,startIndex+sLen));
                  fromIndex = startIndex + sLen;
                  startIndex = str.indexOf(schStr,fromIndex);
                  continue;
               }
            }

            /* 
             * match found. append until the match and instead of the
             * match string append the replacing string 
             */
            retStrBuf.append(str.substring(fromIndex,startIndex))
                       .append(repStr);
            fromIndex=startIndex + sLen;
            startIndex = str.indexOf(schStr,fromIndex);
         }
         /* append the remaining if any */
         retStrBuf.append(str.substring(fromIndex));
      }
      if (retStrBuf == null) 
         return null;
      
      return retStrBuf.toString();
   }


   /**
    * Search and replace the substitution variables in an LDIF entry 
    * contained in a Vector.
    *
    * @param ldifEntry the LDIF entry with elements as the attributes.
    * @param sAndRep the vector containing substitution variables 
    * name-value pairs
    * @param isUtf - TRUE Treat the Base 64 Encoded str as UTF8 str
    *                FALSE Treat the Base 64 Encoded str as platform's default
    *                encoded string.
    * @return Vector LDIF Entry after applying the substitution. When ldifEntry
    * is null or ldifEntry vector does not contain any attributes then null is 
    * returned. If sAndRep is null or there are no substitution variables then
    * the ldifEntry is returned as is.
    */
   /*ATIKA JAIN*/
   public static Vector substitute(Vector<String> ldifEntry, Vector sAndRep, boolean isUtf)
   {
      int ldifEntrySz = 0;
      int sAndRepSz = 0;
      Vector<String> retVect;
      String atrStr;
      String schStr;
      String repStr;

      /*
       * if the ldifentry is null or does not contain any attributes
       * then return null;
       */
      if (ldifEntry == null || (ldifEntrySz = ldifEntry.size())==0)
      {
         retVect = null;
      }
      /*
       * if the vector containing the substitution variable is null or is of 
       * zero size then return the same ldif entry
       */
      else if ( sAndRep == null || (sAndRepSz=sAndRep.size())==0)
      {
         retVect = ldifEntry;
      }
      else
      {
         retVect = new Vector<String>();
         /* For every attribute in the LDIF entry */
         for(int iCn = 0; iCn < ldifEntrySz; iCn++)
         {
            boolean base64Encoded = false;
            String tmpStr = (String)ldifEntry.elementAt(iCn);
            int startIndex = tmpStr.indexOf("::");

            if (startIndex != -1)
            {
               tmpStr = tmpStr.substring(0,startIndex+2) + 
                     ( isUtf ? Base64.utfDecode(tmpStr.substring(startIndex+2)) :
                          new String(Base64.decode(
                           tmpStr.substring(startIndex+2).getBytes())));
               base64Encoded = true;
            }
            atrStr = tmpStr;
            /* For every substitution variable, value in the vector*/
            for (int jCn = 0; jCn < sAndRepSz-1; jCn+=2)
            {
               schStr = (String)sAndRep.elementAt(jCn);
               repStr = (String)sAndRep.elementAt(jCn+1);
               atrStr = substitute(atrStr,schStr,repStr);
            }

            /* This will be ture always, unless someone intends to replace
             * null with a null in a null string.
             * Does it make any sense to do that ?
             */
            if (atrStr != null)
            {
               if (base64Encoded)
               {
                  startIndex = atrStr.indexOf("::");
                  atrStr = atrStr.substring(0,startIndex+2) +
                   ( isUtf ? Base64.utfEncode(atrStr.substring(startIndex+2)) :
                      new String(Base64.encode(
                           atrStr.substring(startIndex+2).getBytes())));
               }
               retVect.addElement(atrStr);
            }
         }
      }

      return retVect;
   }

   /**
    * Search and replace the substitution variables in an LDIF entry 
    * contained in a Vector.
    *
    * @param ldifEntry the LDIF entry with elements as the attributes.
    * @param sAndRep the vector containing substitution variables 
    * name-value pairs
    * @return Vector LDIF Entry after applying the substitution. When ldifEntry
    * is null or ldifEntry vector does not contain any attributes then null is 
    * returned. If sAndRep is null or there are no substitution variables then
    * the ldifEntry is returned as is.
    */
   public static Vector substitute(Vector<String> ldifEntry, Vector sAndRep)
   {
      return substitute(ldifEntry, sAndRep, false);
   }

   /*
    * Test Driver.
    *
    */
   public static void main(String args[]) 
   {
      String rep = "s_var";
      String str="%s_var%,dn: cn=Heman,%s_var%s_var%,dc=ORACLE,dc_COM%s_var%%s_var%";
      String repWith = "dc=IDC";
      System.out.println(str);
      System.out.println(substitute(str,rep,repWith));
   }
}
