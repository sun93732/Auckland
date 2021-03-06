/* $Header: entsec/oes/atzsrg/util/src/ldaptool/LdapModify.java /main/2 2010/03/25 18:47:26 sregmi Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
     This class is to provide ldapmodify capability so that we can use it
     to load an ldif file to OID.

     We mainly use this class during farm preparation and extend OID
     schema for atzsrg.


   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    sregmi      03/24/10 - Change the properties consumed
    dlin        03/12/10 - Creation
 */

/**
 *  @version $Header: entsec/oes/atzsrg/util/src/ldaptool/LdapModify.java /main/2 2010/03/25 18:47:26 sregmi Exp $
 *  @author  dlin
 *  @since   release specific (what release of product did this appear in)
 */

package ldaptool;

import java.io.IOException;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import oracle.jldif.util.*;



public class LdapModify {

    private static LdapContext nonSSLBind(String hostUri, String eDN,
                                          String pwd) throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, hostUri);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, eDN);
        env.put(Context.SECURITY_CREDENTIALS, pwd);

        // Obtain the Initial Context
        LdapContext dirContext = null;
        dirContext = new InitialLdapContext(env, null);
        if (dirContext != null) {
            System.out.println("User Authenticated --- using non SSL.");
        }
        return dirContext;
    }

    public static void ldapModify(LdapContext ctx, String inLdifFileName,
                                  boolean cont) throws Exception {
        LDIFRecord record = null;
        LDIFReader ldifReader = new LDIFReader(inLdifFileName);

        try {
            while ((record = ldifReader.nextRecord()) != null) {
                try {
                    switch (record.getChangeType()) {
                    case LDIF.RECORD_CHANGE_TYPE_ADD:
                        {
                            System.out.println("adding entry " +
                                               record.getDN());
                            Attributes attrs = record.getJNDIAttributes();
                            ctx.createSubcontext(record.getDN(), attrs);
                            break;
                        } // end of LDIF.RECORD_CHANGE_TYPE_ADD:

                    case LDIF.RECORD_CHANGE_TYPE_MODIFY:
                        {
                            System.out.println("modifying entry " +
                                               record.getDN());
                            ModificationItem[] modItem =
                                record.getJNDIModificationItems();
                            ctx.modifyAttributes(record.getDN(), modItem);
                            break;
                        } // end of LDIF.RECORD_CHANGE_TYPE_MODIFY:

                    case LDIF.RECORD_CHANGE_TYPE_DELETE:
                        {
                            System.out.println("modifying entry " +
                                               record.getDN());
                            ctx.destroySubcontext(record.getDN());
                            break;
                        } // end of case LDIF.RECORD_CHANGE_TYPE_DELETE:
                    } // end of switch

                } catch (NamingException e) {
                    System.err.println("Modify failed for entry " + record);
                    System.err.println("Error message = " + e.toString());

                    if (false == cont) {
                        throw e;
                    }
                } // end of catch
            } // end of while
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
        ldifReader.close();

    }

    public static void main(String[] args) throws Exception {

    	 String hostUri = "ldap://slc04sgp.us.oracle.com:3060";        
         String eDN = "cn=orcladmin";
         String pwd = "welcome";
         String ldifLoc = "c:\\Users\\shuaisun\\Desktop\\ftp\\ldif\\opssPS2SchemaVersion.ldif";

        System.out.println("LDAP hostUri: " + hostUri);        
        System.out.println("LDAP DN:   " + eDN);
        System.out.println("password:  " + pwd);
        System.out.println("ldifLoc:   " + ldifLoc);

        LdapContext dirContext = nonSSLBind(hostUri, eDN, pwd);
        ldapModify(dirContext, ldifLoc, true);
    }

}


