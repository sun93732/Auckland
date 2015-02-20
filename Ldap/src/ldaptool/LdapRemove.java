/* $Header: entsec/oes/atzsrg/util/src/ldaptool/LdapRemove.java /main/1 2010/11/29 01:17:13 acao Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    wcai        10/25/10 - Creation
 */

/**
 *  @version $Header: entsec/oes/atzsrg/util/src/ldaptool/LdapRemove.java /main/1 2010/11/29 01:17:13 acao Exp $
 *  @author  wcai    
 *  @since   release specific (what release of product did this appear in)
 */

package ldaptool;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;




public class LdapRemove {

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

    public static void ldapRemove(LdapContext ctx,
                               String subcontext) throws Exception {

        try {
            TreeMap<String, String> map = new TreeMap<String, String>();
            NamingEnumeration<NameClassPair> ne = ctx.list(subcontext);
            while (ne.hasMore()) {
                String name = ne.next().getNameInNamespace();
                map.put(new StringBuffer(name).reverse().toString(), name);
            }
            
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                ctx.destroySubcontext(map.get(it.next()));
            }
            ctx.destroySubcontext(subcontext);
        } catch (Exception ex) {
            System.out.println("Failed to delete the context because " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {

        String hostUri = System.getProperty("ldap.uri");        
        String eDN = "cn=orcladmin";
        String pwd = System.getProperty("ldap.credential");
        String subcontext = System.getProperty("subcontext");

        System.out.println("LDAP hostUri: " + hostUri);        
        System.out.println("LDAP DN:   " + eDN);
        System.out.println("password:  " + pwd);
        System.out.println("subcontext:   " + subcontext);

        LdapContext dirContext = nonSSLBind(hostUri, eDN, pwd);
        ldapRemove(dirContext, subcontext);

    }

}


