/* $Header: entsec/ldap/oracle/jldif/util/Version.java /st_entsec_11.1.1.7.0/1 2012/07/31 22:53:33 kchaubey Exp $ */

/* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    kchaubey    06/26/12 - Creation
 */

/**
 *  @version $Header: entsec/ldap/oracle/jldif/util/Version.java /st_entsec_11.1.1.7.0/1 2012/07/31 22:53:33 kchaubey Exp $
 *  @author  kchaubey
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.jldif.util;

public class Version implements Comparable<Version>
{
    private String versionStr;

    Version(String version)
    {
        this.versionStr = version;
    }

    public String getVersionString()
    {
        return versionStr;
    }

    public int compareTo(Version cVersion)
    {

        String[] vals1 = versionStr.split("\\.");
        String[] vals2 = cVersion.getVersionString().split("\\.");

        int i = 0;
        int j = 0;
        int length1 = vals1.length;
        int length2 = vals2.length;

        for (j = vals1.length - 1;; j--)
        {
            if (new Integer(vals1[j]) > 0)
                break;
            length1--;
        }

        for (j = vals2.length - 1;; j--)
        {
            if (new Integer(vals2[j]) > 0)
                break;
            length2--;
        }

        while (i < length1 && i < length2 && vals1[i].equals(vals2[i]))
        {
            i++;
        }

        if (i < length1 && i < length2)
        {
            int diff = new Integer(vals1[i]).compareTo(new Integer(vals2[i]));
            return diff < 0 ? -1 : diff == 0 ? 0 : 1;
        }

        return length1 < length2 ? -1 : length1 == length2 ? 0 : 1;
    }

    public Boolean isValidVersion()
    {
        if (versionStr.endsWith("."))
            return false;
        String[] vals = versionStr.split("\\.");
        try
        {
            for (int i = 0; i < vals.length; i++)
                Integer.parseInt(vals[i]);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

}
