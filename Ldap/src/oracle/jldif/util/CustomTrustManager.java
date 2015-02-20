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

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class CustomTrustManager implements X509TrustManager
{
    private X509TrustManager nativeTm;

    public CustomTrustManager()
    {
        try
        {
            TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            nativeTm = (X509TrustManager) (tmf.getTrustManagers())[0];
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws java.security.cert.CertificateException
    {
        return;
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException
    {
        throw new CertificateException(
            "TrustManager: checkClientTrusted failed.Only NO AUTH Supported");
    }

    public X509Certificate getAcceptedIssuers()[]
    {
        return nativeTm.getAcceptedIssuers();
    }
}
