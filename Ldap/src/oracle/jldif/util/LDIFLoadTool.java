/* Copyright (c) 2009, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
 DESCRIPTION
 <short description of component this file declares/defines>

 PRIVATE CLASSES
 <list of private classes defined - with one-line descriptions>

 NOTES
 <other useful comments, qualifications, etc.>

 MODIFIED    (MM/DD/YY)
 vijanaki    09/27/12 - XbranchMerge vijanaki_bug-14630717 from main
 kchaubey    07/31/12 - Backport kchaubey_bug-14175643 from
 kchaubey    06/24/12 - Backport rapurush_bug-14151044 from main
 kchaubey    06/22/12 - Backport kchaubey_bug-14059926 from main
 kchaubey    04/06/12 - bug 13924664
 vijanaki    11/10/10 - XbranchMerge vijanaki_bug-10177981 from
 st_entsec_11.1.1.4.0
 atijain     07/15/10 - Bug 9885286
 atijain     03/07/10 - Taking an XLIF File, generating an LDIF File and
 loading it.
 atijain     08/24/09 - Bug 8832160
 atijain     08/20/09 - Creation
 */
package oracle.jldif.util;

import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class LDIFLoadTool extends Task
{
    private String m_property;
    private String m_host;
    private String m_bindDN;
    private String m_password;
    private String m_file;
    private int m_port;
    private boolean m_ignoreError = true;
    private boolean isSSL = true;
    private boolean m_changeTypeAdd = false;
    private int m_maxRetryCount = 15;
    private String m_excludeVersionStr = null;
    private String tempFilesLocation = null;

    public void setTempFilesLocation (String tempLocation)
    {
        tempFilesLocation = tempLocation;
    }
    public void setSSL(boolean enableSSL)
    {
        isSSL = enableSSL;
    }

    public void setChangeTypeAdd(boolean changeTypeAdd)
    {
        m_changeTypeAdd = changeTypeAdd;
    }

    public void setProperty(String inProperty)
    {
        m_property = inProperty;
    }

    public void setHost(String inHost)
    {
        m_host = inHost;
    }

    public void setPort(int inPort)
    {
        m_port = inPort;
    }

    public void setBindDN(String inBindDN)
    {
        m_bindDN = inBindDN;
    }

    public void setPwd(String inPwd)
    {
        m_password = inPwd;
    }

    public void setFile(String inFile)
    {
        m_file = inFile;
    }

    public void setIgnoreError(boolean ignoreError)
    {
        m_ignoreError = ignoreError;
    }

    public void setMaxRetryCount(int maxRetryCount)
    {
        m_maxRetryCount = maxRetryCount;
    }

    public void setExcludeVersion(String version)
    {
        m_excludeVersionStr = version;
    }

    public void execute() throws BuildException
    {
        if (m_property == null)
            throw new BuildException("Error: No property name");

        if (m_host == null)
            throw new BuildException("Error: No Host Name");

        if (m_port == 0)
            throw new BuildException("Error: No Port Number");

        if (m_bindDN == null)
            throw new BuildException("Error: No bind DN");

        if (m_password == null)
            throw new BuildException("Error: No bind Password");

        if (m_file == null)
            throw new BuildException("Error: No LDIF/XLIFF File Specified");

        String retVal = null;
        try
        {
            loadLdifFile(m_host, m_port, m_bindDN, m_password, m_file,
                    m_ignoreError);
            retVal = "Loading Process Completed";
        }
        catch (NamingException ne)
        {
            retVal = "Connect details not specified Correctly.";
            throw new BuildException(retVal + ne.getMessage());
        }
        catch (UtilException ue)
        {
            retVal = "Error Loading the LDIF File";
            throw new BuildException(retVal + ue.getMessage());
        }
        finally
        {
            if (retVal != null)
                getProject().setNewProperty(m_property, retVal);
        }

    }

    public void loadLdifFile(String host, int port, String bindDN,
            String password, String file, boolean ignoreError)
            throws NamingException, UtilException
    {
        loadLdifFile(host, port, bindDN, password, file, ignoreError, isSSL,
                m_changeTypeAdd);
    }

    public void loadLdifFile(String host, int port, String bindDN,
            String password, String file, boolean ignoreError, boolean enableSSL)
            throws NamingException, UtilException
    {
        loadLdifFile(host, port, bindDN, password, file, ignoreError,
                enableSSL, m_changeTypeAdd);
    }

    public void loadLdifFile(String host, int port, String bindDN,
            String password, String file, boolean ignoreError,
            boolean enableSSL, boolean changeTypeAdd) throws NamingException,
            UtilException
    {
        loadLdifFile(host, port, bindDN, password, file, ignoreError,
                enableSSL, changeTypeAdd, m_maxRetryCount);
    }

    // overloaded loadLdifFile method with maxRetryCount as one of the inputs
    public void loadLdifFile(String host, int port, String bindDN,
            String password, String file, boolean ignoreError, int maxRetryCount)
            throws NamingException, UtilException
    {
        loadLdifFile(host, port, bindDN, password, file, ignoreError, isSSL,
                maxRetryCount);
    }

    // overloaded loadLdifFile method with maxRetryCount as one of the inputs
    public void loadLdifFile(String host, int port, String bindDN,
            String password, String file, boolean ignoreError,
            boolean enableSSL, int maxRetryCount) throws NamingException,
            UtilException
    {
        loadLdifFile(host, port, bindDN, password, file, ignoreError,
                enableSSL, m_changeTypeAdd, maxRetryCount);
    }

    // overloaded loadLdifFile method with maxRetryCount as one of the inputs
    public void loadLdifFile(String host, int port, String bindDN,
            String password, String file, boolean ignoreError,
            boolean enableSSL, boolean changeTypeAdd, int maxRetryCount)
            throws NamingException, UtilException
    {
        loadLdifFile(host, port, bindDN, password, file, ignoreError,
                enableSSL, changeTypeAdd, maxRetryCount, m_excludeVersionStr);
    }

    // overloaded loadLdifFile method with maxRetryCount and excludeVersion as
    // two of the inputs
    public void loadLdifFile(String host, int port, String bindDN,
            String password, String file, boolean ignoreError,
            boolean enableSSL, boolean changeTypeAdd, int maxRetryCount,
            String excludeVersionStr) throws NamingException, UtilException
    {
        InitialLdapContext ctx = null;
        if (enableSSL)
            ctx = ConnectionUtil.getSSLDirCtx(host, Integer.toString(port),
                    bindDN, password);
        else
            ctx = ConnectionUtil.getNonSSLDirCtx(host, Integer.toString(port),
                    bindDN, password);
        if (tempFilesLocation == null)
            tempFilesLocation = System.getProperty("java.io.tmpdir");
        LDIFLoader ldif = new LDIFLoader(ctx, file, null, maxRetryCount);
        ldif.changeTypeAdd = changeTypeAdd;
        ldif.tempFilesLocation = tempFilesLocation;
        ldif.load(ignoreError, null, excludeVersionStr);
        if (ctx != null)
            ctx.close();
    }
}

