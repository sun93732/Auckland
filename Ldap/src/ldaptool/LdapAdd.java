/* $Header: entsec/oes/atzsrg/util/src/ldaptool/LdapAdd.java /main/2 2010/03/25 18:47:26 sregmi Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
 DESCRIPTION
 This class is to provide ldapadd capability so that we can use it
 to load an ldif file to OID.

 We mainly use this class during farm preparation and add the jps
 test node.

 PRIVATE CLASSES
 <list of private classes defined - with one-line descriptions>

 NOTES
 <other useful comments, qualifications, etc.>

 MODIFIED    (MM/DD/YY)
 dlin        03/12/10 - Creation
 */

/**
 *  @version $Header: entsec/oes/atzsrg/util/src/ldaptool/LdapAdd.java /main/2 2010/03/25 18:47:26 sregmi Exp $
 *  @author  dlin
 *  @since   release specific (what release of product did this appear in)
 */

package ldaptool;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.Attributes;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import oracle.jldif.util.*;


public class LdapAdd {

	private static LdapContext nonSSLBind(String hostUri, String eDN, String pwd)
			throws Exception {
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

	public static void ldapAdd(LdapContext ctx, String inLdifFileName)
			throws Exception {

		LDIFRecord record = null;
		LDIFReader ldifReader = new LDIFReader(inLdifFileName);

		while ((record = ldifReader.nextRecord()) != null) {
			Attributes attrs = record.getJNDIAttributes();
			System.out.println("adding new entry " + record.getDN() + "\n");
			ctx.createSubcontext(record.getDN(), attrs);
		}

		ldifReader.close();
	}

	public static void main(String[] args) throws Exception {

        String hostUri = "ldap://slc04sgp.us.oracle.com:3060";        
        String eDN = "cn=orcladmin";
        String pwd = "welcome";
        String ldifLoc = "c:\\Users\\shuaisun\\Desktop\\ftp\\ldif\\opssPS2Schema.ldif";

        System.out.println("LDAP hostUri: " + hostUri);        
        System.out.println("LDAP DN:   " + eDN);
        System.out.println("password:  " + pwd);
        System.out.println("ldifLoc:   " + ldifLoc);

        LdapContext dirContext = nonSSLBind(hostUri, eDN, pwd);
        ldapAdd(dirContext, ldifLoc);
    }

}
