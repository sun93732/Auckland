/* $Header: entsec/ldap/oracle/jldif/util/LDIF2XLIFConvertorTool.java /main/1 2010/04/29 04:43:55 atijain Exp $ */

/* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved. */

/*
 DESCRIPTION
 <short description of component this file declares/defines>

 PRIVATE CLASSES
 <list of private classes defined - with one-line descriptions>

 NOTES
 <other useful comments, qualifications, etc.>

 MODIFIED    (MM/DD/YY)
 atijain     04/27/10 - Enh to convert Ldif file to xlif
 atijain     04/27/10 - Creation
 */

/**
 *  @version $Header: entsec/ldap/oracle/jldif/util/LDIF2XLIFConvertorTool.java /main/1 2010/04/29 04:43:55 atijain Exp $
 *  @author  atijain
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.jldif.util;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class LDIF2XLIFConvertorTool extends Task
{
    private String m_property;
    private String m_ldifFile;
    private String m_XlifFile;
    private String m_TranslatablesFile;

    public void setProperty(String inProperty)
    {
        m_property = inProperty;
    }

    public void setLdifFile(String inLdifFile)
    {
        m_ldifFile = inLdifFile;
    }

    public void setXlifFile(String inXlifFile)
    {
        m_XlifFile = inXlifFile;
    }

    public void setTranslatablesFile(String inTranslatablesFile)
    {
        m_TranslatablesFile = inTranslatablesFile;
    }

    public void execute() throws BuildException
    {
        if (m_property == null)
            throw new BuildException("Error: No property name");

        if (m_ldifFile == null)
            throw new BuildException("Error: No Ldif File Specified");

        if (m_XlifFile == null)
            throw new BuildException("Error: No Xlif File Specified");

        if (m_TranslatablesFile == null)
            throw new BuildException(
                "Error: No Translatable Elements Specified");

        String retVal = null;
        try
        {
            generateXlifFile(m_ldifFile, m_XlifFile, m_TranslatablesFile);
            retVal = "Generation Process Completed";
        }
        catch (Exception e)
        {
            retVal = "Error Generating the XLIFF File";
            throw new BuildException(retVal + e.getMessage());
        }
        finally
        {
            if (retVal != null)
                getProject().setNewProperty(m_property, retVal);
        }

    }

    private void generateXlifFile(String ldifFile, String xlifFile,
        String translatablesFile) throws Exception
    {
        XLIFFParser xlif = new XLIFFParser();
        String[] translatables = xlif.getTranslatableElems(translatablesFile);
        xlif.convertLDIF2XLF(ldifFile, xlifFile, translatables);
    }
}
