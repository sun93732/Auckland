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

import java.util.StringTokenizer;

import javax.naming.NamingException;

/**
 * This is the superclass of all exceptions defined in oracle.ldap.util
 * package
 */

public class UtilException extends Exception
{
   protected int ldapErrorCode = -1;
   protected int passwordPolicyErrorCode = -1;
   protected Exception e = null;
   protected boolean isNamingException = false;
   protected boolean hasPasswordPolicyError = false;
   protected boolean hasLDAPErrorCode = false;
   protected NamingException ne = null;
   
   protected UtilException() { super(); }

   /**
    * Constructor for UtilException allowing an error message to be 
    * specified
    * @param s the error string to be stored
    */
   protected UtilException( String s ) { super(s); }

   /**
    * Constructor for UtilException allowing an error message to be
    * specified.  It also allows the option to store the exception through
    * the second argument.  If ine is an instance of the NamingException,
    * the error string will be parsed and the LDAP error code is extracted.
    * This can be obtained using Util.getLDAPErrorCode().
    *
    * @param s the error string to be stored
    * @param ine the Exception to be stored.
    */
   private UtilException(NamingException ine )
   {
      isNamingException = true;
      parseErrorCode(ine);
   }

   public UtilException(Exception e)
   {
    super(e);
   }

   protected UtilException( String s, Exception ine )
   {
      super(s + " " + ine.getMessage());
      if ( ine instanceof NamingException )
      {
         ne = (NamingException)ine;
         isNamingException = true;
         parseErrorCode( (NamingException) ine );
      }
      e = ine;
   }


   public NamingException returnNamingException()
   {
      return ne;
   }
   /**
    * Determines if the exception stored is a NamingException.  The exception
    * stored in Util.Exception is not root cause of this exception.  It is
    * the cause of this UtilException to be thrown but needs not be the root
    * cause of the Exception stack
    */
   public boolean isNamingException()
   {
      return isNamingException;
   }

   /**
    * Returns true if the LDAP error code indictates a password policy
    * error.  If the cause of this <code>UtilException</code> is not a
    * NamingException or if it is unable to retrieve the LDAP error code
    * from the error string, -1 is returned.
    */

   public boolean hasPasswordPolicyError()
   {
      return hasPasswordPolicyError;
   }

   /**
    * Returns true if the error string contains an LDAP error code.
    * error.  If the cause of this <code>UtilException</code> is not a
    * NamingException or if it is unable to retrieve the LDAP error code
    * from the error string, -1 is returned.
    */

   public boolean hasLDAPErrorCode()
   {
      return hasLDAPErrorCode;
   }

   /**
    * Returns the LDAP error code from the exception if it is available
    */

   public int getLDAPErrorCode()
   {
      return ldapErrorCode;
   }

   /**
    * Returns the password policy error code from the exception
    * if it is available
    */

   public int getPasswordPolicyErrorCode()
   {
      return passwordPolicyErrorCode;
   }
   /**
    * A static method to allow the parsing of the error string in the
    * NamingException to extract the LDAP error code.
    * 
    * @param ne a NamingException
    */
   public static int getLDAPErrorCode( NamingException ne )
   {
      return ((new UtilException(ne)).getLDAPErrorCode());
   }

   /**
    * Parses the error string in the NamingException to extract the error
    * code
    * 
    * @param ne a NamingException
    * @returns int the LDAP error code; -1 if it is unable to extract the
    *              error code from the error string
    */
   private void parseErrorCode( NamingException ne )
   {
      String s = ne.toString();
      //System.out.println(s);
      int i = s.indexOf("LDAP: error code");
      
      if (i > -1)
      {
         hasLDAPErrorCode = true;
         StringTokenizer st = new StringTokenizer(s.substring(i));
         st.nextToken();
         st.nextToken();
         st.nextToken();
         if (st.hasMoreTokens())
         {
            try
            {
               ldapErrorCode = (new Integer(st.nextToken())).intValue();
            }
            catch (NumberFormatException nfe) {}
         }
      }

      i = s.indexOf(":"); //Password Policy Error");;

      if (i > -1)
      {
         StringTokenizer st = new StringTokenizer(s.substring(i),":");

         while (st.hasMoreTokens())
         {
            try
            {
               passwordPolicyErrorCode = (new Integer(st.nextToken())).intValue();
               if ((passwordPolicyErrorCode > 999) &&
                   (passwordPolicyErrorCode < 10000)) // must be a 4digit int
               {
                 hasPasswordPolicyError = true;
                 break;
               }
            }
            catch (NumberFormatException nfe) { }
         }
      }
   }
}
