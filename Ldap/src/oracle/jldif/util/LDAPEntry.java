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
import java.util.ResourceBundle;

import javax.naming.directory.DirContext;

/**
 * <p>
 * This abstract class represents an LDAP entry.  It is subclassed by
 * many of the objects in the oracle.ldap.util package - for example,
 * <code>User</code> and <code>Subscriber<code> classes.
 * </p>
 * <p>
 * The class provides some general LDAP search and modify methods to be
 * performed on an LDAP entry.
 * </p>
 */


public abstract class LDAPEntry implements Serializable{


   protected static ResourceBundle resBundle = ResourceBundle.getBundle(
                           "oracle.ldap.util.nls.UtilityResource");

   protected String entryDN = null;
   
   protected String inEntryId = null;
   protected int entryIdType = -1;
  //otected DirContext ctx = null;
   protected boolean validated = false;

   public LDAPEntry ()
   {}
 

   /**
    * Returns the DN of the entry (resolves the name if necessary).
    *
    * @param ctx             a valid DirContext
    * @exception UtilException
    */
   public String getDN( DirContext ctx )
           throws UtilException
   {
      resolve (ctx);
      return entryDN;
   }

   /**
    * Abstract method - implements validation of the LDAP entry
    * by resolving the DN
    *
    * @exception UtilException
    */
   public abstract void resolve( DirContext ctx )
      throws UtilException;
}


   
   
   
