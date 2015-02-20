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

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class CustomSSLFactory extends SSLSocketFactory
{
    private static SSLSocketFactory ossl = null;

    public CustomSSLFactory() throws SSLException
    {
        this(System.getProperties());
    }

    public CustomSSLFactory(Hashtable ht) throws SSLException
    {
        try
        {
            SSLContext sslCtx = SSLContext.getInstance("SSL");
            sslCtx.init(null, new TrustManager[] { new CustomTrustManager() },
                null);
            ossl = sslCtx.getSocketFactory();
        }
        catch (Exception e)
        {
            throw new SSLException(e.toString());
        }
    }

    public static SocketFactory getDefault()
    {
        try
        {
            return (new CustomSSLFactory());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public Socket createSocket(String s, int i) throws IOException
    {
        SSLSocket sock = (SSLSocket) ossl.createSocket(s, i);
        return init(sock);
    }

    public Socket createSocket(InetAddress iaddr, int i) throws IOException
    {
        SSLSocket sock = (SSLSocket) ossl.createSocket(iaddr, i);
        return init(sock);
    }

    public Socket createSocket(String s, int i, InetAddress iaddr, int j)
        throws IOException
    {
        SSLSocket sock = (SSLSocket) ossl.createSocket(s, i, iaddr, j);
        return init(sock);
    }

    public Socket createSocket(InetAddress iaddr1, int i, InetAddress iaddr2,
        int j) throws IOException
    {
        SSLSocket sock = (SSLSocket) ossl.createSocket(iaddr1, i, iaddr2, j);
        return init(sock);
    }

    public Socket createSocket(Socket s, String host, int port,
        boolean autoClose) throws IOException
    {
        SSLSocket sock = (SSLSocket) ossl
            .createSocket(s, host, port, autoClose);
        return init(sock);
    }

    private SSLSocket init(SSLSocket sock) throws IOException
    {
        sock.setUseClientMode(true);
        sock.setEnabledCipherSuites(ossl.getSupportedCipherSuites());
        sock.startHandshake();
        return sock;
    }

    public String[] getDefaultCipherSuites()
    {
        return (ossl.getDefaultCipherSuites());
    }

    public String[] getSupportedCipherSuites()
    {
        return (ossl.getSupportedCipherSuites());
    }

}
