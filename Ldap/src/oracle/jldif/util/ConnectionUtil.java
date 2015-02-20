/* $Header: entsec/ldap/oracle/jldif/util/ConnectionUtil.java /main/2 2010/11/10 18:46:25 vijanaki Exp $ */

/* Copyright (c) 2009, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
 DESCRIPTION
 <short description of component this file declares/defines>

 PRIVATE CLASSES
 <list of private classes defined - with one-line descriptions>

 NOTES
 <other useful comments, qualifications, etc.>

 MODIFIED    (MM/DD/YY)
 vijanaki    11/10/10 - XbranchMerge vijanaki_bug-10177981 from
                        st_entsec_11.1.1.4.0
 atijain     08/20/09 - Creation
 */

/**
 *  @version $Header: entsec/ldap/oracle/jldif/util/ConnectionUtil.java /main/1 2009/11/04 21:29:28 atijain Exp $
 *  @author  atijain
 *  @since   release specific (what release of product did this appear in)
 */

/**

 *  This class provides a set of static functions to support the creation
 *  of JNDI DirContext connecting to OID.  SSL connection is supported.
 */

package oracle.jldif.util;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

/**
 * This class provides a set of static functions to support the creation of JNDI
 * DirContext connecting to OID. SSL connection is also supported.
 */

public class ConnectionUtil
{

    /**
     * Returns an <code>InitialLdapContext</code> using the connect information
     * provided. This connection will operate in the SSL mode The corresponding
     * OID server must be accepting SSL connections.
     * 
     * @param host
     *            The hostname where the SSL OID is running
     * @param port
     *            The port number on which the SSL OID is running
     * @param bindDN
     *            the bind DN (eg. cn=orcladmin)
     * @param bindPwd
     *            the bind password
     * @return an SSL <code>InitialLdapContext</code>
     * @exception NamingException
     */

    public static InitialLdapContext getSSLDirCtx(String host, String port,
        String bindDN, String bindPwd) throws NamingException
    {
        return (getSSLDirCtx(host, port, bindDN, bindPwd,
            "oracle.jldif.util.CustomSSLFactory"));
    }

    /**
     * Returns an <code>InitialLdapContext</code> using the connect information
     * provided - including the SSLSocketFactory implementation. This connection
     * will operate in the SSL mode. The corresponding OID server must be
     * accepting SSL connections. The sslSocketFactory parameter expects the
     * class name of a customized SSL Socket Factory implementation.
     * 
     * @param host
     *            The hostname where the SSL OID is running
     * @param port
     *            The port number on which the SSL OID is running
     * @param bindDN
     *            the bind DN (eg. cn=orcladmin)
     * @param bindPwd
     *            the bind password
     * @param sslSocketFactory
     *            the class name of the socket factory implementation to be used
     *            by JNDI
     * @return an SSL <code>InitialLdapContext</code>
     * @exception NamingException
     */

    public static InitialLdapContext getSSLDirCtx(String host, String port,
        String bindDN, String bindPwd, String sslSocketFactory)
        throws NamingException
    {
        Hashtable<String, String> hashtable =
            createCtxHash(host, port, bindDN, bindPwd);

        // java.util.Properties prop = System.getProperties();
        // prop.put("SSLSocketFactoryImplClass",
        // sslSocketFactory);
        // Security.setProperty("ssl.SocketFactory.provider",
        // sslSocketFactory);

        hashtable.put("java.naming.ldap.factory.socket", sslSocketFactory);
        hashtable.put(Context.SECURITY_PROTOCOL, "ssl");

        Thread.currentThread().setContextClassLoader(
            ConnectionUtil.class.getClassLoader());

        int i = 0;
        while (true)
        {
            i++;
            try
            {
                return (new InitialLdapContext(hashtable, null));
            }
            catch (NamingException ne)
            {
                // bug 4159320
                // System.out.println("Retry count " + i + " at " +
                // (new Date(System.currentTimeMillis())).toString());
                Throwable t = ne.getRootCause();
                if ((!((t instanceof SSLHandshakeException) || (t instanceof SSLException)))
                    || (i > 2))
                    throw ne;
            } // otherwise continue and retry again

        }
    }
    
    public static InitialLdapContext getNonSSLDirCtx(String host, String port,
            String bindDN, String bindPwd)
            throws NamingException
    {
        Hashtable<String, String> hashtable = createCtxHash(host, port, bindDN,
                bindPwd);

        Thread.currentThread().setContextClassLoader(
                ConnectionUtil.class.getClassLoader());
        return (new InitialLdapContext(hashtable, null));
    }

    private static Hashtable<String, String> createCtxHash(String host,
        String port, String bindDN, String bindPwd)
    {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();

        // Look through System Properties for Context Factory if available
        for (Enumeration<Object> e = System.getProperties().keys(); e
            .hasMoreElements();)
        {
            String key = (String) e.nextElement();
            if (key.startsWith("java.naming."))
            {

                /*
                 * To avoid NotContextException from OC4J setting
                 * java.naming.context.initial to this value
                 */

                if ((System.getProperty(key))
                    .equals("com.evermind.server.ApplicationInitialContextFactory"))
                {
                    continue;
                }

                hashtable.put(key, System.getProperty(key));
            }
        }
        // set the CONTEXT factory only if it has not been set
        // in the environment - set default to com.sun.jndi.ldap.LdapCtxFactory
        if (hashtable.get(Context.INITIAL_CONTEXT_FACTORY) == null)
        {
            hashtable.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        }
        else if (hashtable.get(Context.INITIAL_CONTEXT_FACTORY).equals(
            "weblogic.jndi.WLInitialContextFactory"))
        {
            /*
             * In Weblogic container, System property may be set to
             * "weblogic.jndi.WLInitialContextFactory". With weblogic JNDI,
             * connection to LDAP directory does not work. So, setting the ctx
             * factory to Sun JNDI
             */
            hashtable.remove(Context.INITIAL_CONTEXT_FACTORY);
            hashtable.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        }

        hashtable
            .put(Context.PROVIDER_URL, "ldap://" + host + ":" + port + "/");
        hashtable.put(Context.SECURITY_AUTHENTICATION, "simple");
        hashtable.put(Context.SECURITY_PRINCIPAL, bindDN);
        hashtable.put(Context.SECURITY_CREDENTIALS, bindPwd);

        // automatically following referrals
        hashtable.put(Context.REFERRAL, "follow");

        return hashtable;
    }

}
