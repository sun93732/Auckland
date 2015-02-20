/* $Header: entsec/ldap/oracle/jldif/util/XLIFFParser.java /st_entsec_11.1.1.7.0/4 2012/09/27 22:35:43 vijanaki Exp $ */

/* Copyright (c) 2010, 2012, Oracle and/or its affiliates. 
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
 rapurush    03/14/12 - XbranchMerge rapurush_bug-13653557 from
                        entsec_11.1.1.4.0_dwg
 atijain     09/16/10 - bug 10122066
 atijain     04/27/10 - Enh to convert Ldif file to xlif
 atijain     03/07/10 - This file parses an XLIFF File
 atijain     03/07/10 - Creation
 */

/**
 *  @version $Header: entsec/ldap/oracle/jldif/util/XLIFFParser.java /st_entsec_11.1.1.7.0/4 2012/09/27 22:35:43 vijanaki Exp $
 *  @author  atijain 
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.jldif.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XLIFFParser
{
    private final DocumentBuilder m_documentBuilder;
    private final Transformer m_transformer;
    private Map<String, Integer> m_map = null;
    private static Element fileNode, bodyNode = null;
    private static Vector<LDIFRecord> ldifRecords = new Vector<LDIFRecord>();
    private static Element m_root;
    private static boolean flag = true;
    protected boolean changeTypeAdd = false;
    protected String tempFilesLocation;
    
    public XLIFFParser() throws Exception
    {
        m_map = new HashMap<String, Integer>();
        try
        {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            m_documentBuilder = factory.newDocumentBuilder();

            TransformerFactory transFactory = TransformerFactory.newInstance();
            m_transformer = transFactory.newTransformer();
            m_transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            m_transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        }
        catch (Exception ex)
        {
            throw new Exception("XML Configuration error", ex);
        }
    }

    public Document parseConfiguration(File source) throws IOException,
        SAXException
    {
        Document document = null;
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(source);
            document = m_documentBuilder.parse(source);
        }
        finally
        {
            if (inputStream != null)
                inputStream.close();
        }
        return document;
    }

    public void writeDocument(Document document, File filePath)
        throws IOException
    {
        OutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(filePath);
            m_transformer.transform(new DOMSource(document), new StreamResult(
                outputStream));
        }
        catch (TransformerException e)
        {
            IOException ioe = new IOException(e.getMessage());
            ioe.initCause(e);
            throw ioe;
        }
        finally
        {
            if (outputStream != null)
                outputStream.close();
        }
    }

    private static String getTargetLanguage()
    {
        return fileNode.getAttributes().getNamedItem("target-language")
            .getNodeValue();
    }

    private static Element getElement(NodeList nl, String elem)
    {
        for (int i = 0; i < nl.getLength(); i++)
        {
            if (nl.item(i).getNodeName().equals(elem))
                return (Element) nl.item(i);
        }
        return null;
    }

    private static String constructDN(String dn)
    {
        String str = new String();
        StringTokenizer s = new StringTokenizer(dn, "#012", true);
        while (s.hasMoreElements())
        {
            String token = s.nextToken();
            if (token.equals("#"))
            {
                int tok = Integer.valueOf(s.nextToken());
                switch (tok)
                {
                    case 0:
                        str = str.concat(" ");
                        break;
                    case 1:
                        str = str.concat(",");
                        break;
                    case 2:
                        str = str.concat("=");
                        break;
                    default:
                        break;
                }
            }
            else
                str = str.concat(token);

        }
        return str;
    }

    public Vector<LDIFRecord> parseXLFFile(String fileName) throws Exception
    {
        XLIFFParser parser = new XLIFFParser();
        File xliffFile = new File(fileName);
        String file = new File(fileName).getName();
        String fname = tempFilesLocation + File.separator + file;
        String tempFile = fname.substring(0, fname.lastIndexOf("."));
        Document document = parser.parseConfiguration(xliffFile);
        Element docElem = document.getDocumentElement();
        fileNode = getElement(docElem.getChildNodes(), "file");
        bodyNode = getElement(fileNode.getChildNodes(), "body");
        NodeList groupNodes = bodyNode.getElementsByTagName("group");
        try
        {
            LDIFWriter ldifWriter =
                new LDIFWriter(tempFile + getDateTime() + ".dat");
            LDIFRecord record = null;
            for (int i = 0; i < groupNodes.getLength(); i++)
            {
                Element node = (Element) groupNodes.item(i);
                record =
                    new LDIFRecord(constructDN(node.getAttributes()
                        .getNamedItem("resname").getNodeValue()));
                record.setChangeType(LDIF.RECORD_CHANGE_TYPE_MODIFY);
                String attrName, attrValue = null;
                NodeList transUnitList =
                    node.getElementsByTagName("trans-unit");
                for (int j = 0; j < transUnitList.getLength(); j++)
                {
                    Element unit = (Element) transUnitList.item(j);
                    attrName =
                        unit.getAttribute("id").concat(";lang-").concat(
                            getTargetLanguage());
                    attrValue =
                        unit.getElementsByTagName("target").item(0)
                            .getTextContent();
                    if (changeTypeAdd)
                      record.add(attrName, attrValue,
                            LDIF.ATTRIBUTE_CHANGE_TYPE_ADD);
                    else
                        record.add(attrName, attrValue,
                            LDIF.ATTRIBUTE_CHANGE_TYPE_REPLACE);
                }
                ldifRecords.add(record);
                ldifWriter.writeEntry(record);
                record = null;
            }
        }
        catch (FileNotFoundException fe)
        {
            System.out.println("Temp LDIF File not created: IGNORING ERROR");
        }
        return ldifRecords;
    }

    private final static String getDateTime()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ssa");
        df.setTimeZone(TimeZone.getTimeZone("PST"));
        return df.format(new Date());
    }

    private String constructResname(String dn)
    {
        String resname = new String();
        StringTokenizer sToken = new StringTokenizer(dn, ",= ' '", true);
        while (sToken.hasMoreElements())
        {
            String token = sToken.nextToken();
            if (token.equals(" "))
                token = "#0";
            else if (token.equals(","))
                token = "#1";
            else if (token.equals("="))
                token = "#2";

            resname = resname.concat(token);
        }

        return resname;
    }

    public void convertLDIF2XLF(String ldifFile, String xlifFile,
        String translatables[]) throws Exception
    {
        LDIFReader ldifReader = new LDIFReader(ldifFile);
        LDIFRecord record = null;
        XLIFFParser xlif = new XLIFFParser();
        Document doc = xlif.createNewInstance(ldifFile);
        bodyNode = getChildElement(fileNode, "body");

        while ((record = ldifReader.nextRecord()) != null)
        {
            Element groupNode = doc.createElementNS("", "group");
            for (int j = 0; j < translatables.length; j++)
            {
                if (record.getAttribute(translatables[j]) != null)
                {
                    createGroupNode(groupNode, record.getDN());
                    Element transNode = doc.createElementNS("", "trans-unit");
                    groupNode.appendChild(transNode);
                    transNode.setAttribute("id", translatables[j]);
                    transNode.setAttribute("maxbytes", "4000");
                    transNode.setAttribute("translate", "yes");
                    String val =
                        record.getAttribute(translatables[j]).getValue();
                    if (val == null)
                        val = "";
                    setTextNode(transNode, "", "source", val);
                    setTextNode(transNode, "", "target", val);
                }
            }
            flag = true;
        }

        xlif.writeDocument(doc, new File(xlifFile));

    }

    private void createGroupNode(Element groupNode, String dn)
    {
        if (flag)
        {
            bodyNode.appendChild(groupNode);
            groupNode.setAttribute("restype", "LDAPEntry");
            groupNode.setAttribute("resname", constructResname(dn));
            flag = false;
        }
    }
    
    public Document createNewInstance(String ldifFile) throws Exception
    {

        Document document = m_documentBuilder.newDocument();
        m_root = document.createElement("xliff");
        m_root.setAttribute("xmlns:xsi",
            "http://www.w3.org/2001/XMLSchema-instance");
        m_root.setAttribute("xmlnx", "urn:oasis:names:tc:xliff:document:1.1");
        m_root.setAttribute("version", "1.0");

        document.appendChild(m_root);
        String namespaceURI = m_root.getNamespaceURI();

        fileNode = document.createElementNS(namespaceURI, "file");
        fileNode.setAttribute("original", ldifFile);
        fileNode.setAttribute("source-language", "en");
        fileNode.setAttribute("target-language", "en");
        fileNode.setAttribute("datatype", "x-oracle-ldif");

        setTextNode(fileNode, namespaceURI, "body", "");
        m_root.appendChild(fileNode);
        return document;
    }

    protected void setTextNode(Element root, String namespaceURI, String name,
        String value)
    {
        Document document = root.getOwnerDocument();
        Element e = getChildElement(root, name);
        if (e == null || e.getParentNode() != root)
        {
            e = document.createElementNS(namespaceURI, name);
            e.appendChild(document.createTextNode(value));
            root.appendChild(e);
        }
        else
        {
            if (e.getChildNodes().getLength() == 0)
                e.appendChild(document.createTextNode(value));
            else
                e.getChildNodes().item(0).setNodeValue(value);
        }
    }

    protected Element getChildElement(Element root, String name)
    {
        Element childElement = null;
        String nsURI = root.getNamespaceURI();

        NodeList elementList = root.getElementsByTagNameNS(nsURI, name);
        if (elementList.getLength() > 0)
            childElement = (Element) elementList.item(0);

        return childElement;
    }

    public String[] getTranslatableElems(String transFilePath)
    {
        String[] transElemArray = null;
        Vector<String> transElems = new Vector<String>(1);
        File transFile = new File(transFilePath);
        try
        {

            FileInputStream fstream = new FileInputStream(transFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)
            {
                transElems.add(strLine);
            }
            transElemArray = new String[transElems.capacity()];
            transElems.toArray(transElemArray);

            in.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        return transElemArray;
    }

}

