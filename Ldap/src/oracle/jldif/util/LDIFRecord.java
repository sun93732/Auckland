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

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.ModificationItem;

/**
 * LDIFRecord represents a single entry in an LDIF file, consisting of 
 * a distinguished name (DN) and zero or more attributes. 
 */
public class LDIFRecord implements Cloneable, Serializable
{
   /**
    * DN
    */
   private String dn = null;

   /**
    * A vector containing LDIFAttributes object
    */
   private Vector<LDIFAttribute> attrs = null;
   
   /**
    * A change type (if any)
    */
   private int changeType = LDIF.RECORD_CHANGE_TYPE_ADD;

   /**
    * A new rdn - will be set if the change type is moddn 
    */
   private String newRdn = null;
   
   /**
    * Delete Old RDN - will be set if the change type is moddn 
    */
   private boolean deleteOldRdn = false;

   /**
    * A new superior - will be set if the change type is moddn 
    */
   private String newSuperior = null;

   /**
    * A flag to indicate that changetype was specified. Useful when
    * user explicitly specifies the changetype:add to add a record.
    */
   private boolean expChgType = false;

   /**
    * Constructs a LDIFRecord object without dn and attribute values set.
    */
   public LDIFRecord()
   {
      this(null);
   }

   /**
    * Constructs a record with the specified dn.
    * @param dn the distinguished name of the new entry
    */
   public LDIFRecord( String dn )
   {
        this.dn = dn; 
        attrs = new Vector<LDIFAttribute>();
   }

   /**
    * Returns the newRdn if the record change type is moddn
    * @return the new RDN of the <CODE>LDIFRecord</CODE>. If the change 
    * type of the record is not <i>moddn/modrdn</i> then null is returned.
    */
   public String getNewRdn()
   {
      return newRdn;
   }

   /**
    * Returns the newSuperior if the record change type is moddn
    * @return the new superior of the <CODE>LDIFRecord</CODE>. If the change 
    * type of the record is not <i>moddn/modrdn</i> then null is returned.
    */
   public String getNewSuperior()
   {
      return newSuperior;
   }

   /**
    * Returns true if deleteOldRdn is set. Otherwise false is returned
    * in all other cases.
    * @return true if deleteOldRdn is true.
    */
   public boolean getDeleteOldRdn()
   {
      return deleteOldRdn;
   }

   /**
    * Adds an attribute to this record. If the attribute already exists
    * and its change type is same as the specified attribute then only the 
    * value of the specified attribute will be added to that attribute which
    * which is existing in the record. However if the record contains two 
    * attributes which are same but with different change type then the 
    * comparison will be done with the attribute that was added most recently.
    *
    * @param atr The LDIFAttribute object which is to be added.
    */
   public void addAttribute(LDIFAttribute atr)
   {
      if (atr != null)
      {
         int index = attrs.lastIndexOf(atr);
         if (index == -1)
            attrs.addElement(atr);
         else
         {
            LDIFAttribute ldifAttr = (LDIFAttribute)attrs.elementAt(index);
            if ( ldifAttr.getChangeType() == atr.getChangeType() )
               ldifAttr.addValue(atr.getStringValueArray());
            else
               attrs.addElement(atr);
         }
      }
   }

   /**
    * Adds an attribute to this record.
    *
    * @param attrName attribute name
    * @param attrVal attribute value
    * @param chgType attribute change type
    */
   public void add(String attrName, String attrVal, int chgType)
   {
      LDIFAttribute attr = null;
      if ( null != attrName )
      {
         attr = new LDIFAttribute(attrName, attrVal);
         attr.setChangeType(chgType);
         addAttribute(attr);
      }
   }

   /**
    * Returns the distinguished name of the current record.
    *
    * @return distinguished name of the current record.
    */
   public String getDN() 
   {
      return dn;
   }

   /**
    * Set the Dn of this record.
    * 
    * @param dn the distinguished name that will be set in the current record.
    */
   public void setDN(String dn)
   {
      this.dn = dn;
   }

   /**
    * Use this method to set that change type was specified by the user
    * 
    * @param expChgType true when the user has specified chg type. 
    * Otherwise false.
    */
   void setExpChgType(boolean expChgType)
   {
      this.expChgType = expChgType;
   }

   /**
    * This method will return true if while creating of this entry
    * the changetype was explicitly specified. This useful while printing
    * of the entry only
    *
    * @return ture when chagnetype:add was specified
    */
   boolean getExpChgType()
   {
      return this.expChgType;
   }

   /**
    * Returns the LDIFAttribute object of the specified attribute name.
    *
    * @param attrName Name of the attribute.
    */
   public synchronized LDIFAttribute getAttribute(String attrName)
   {
      LDIFAttribute retAttrib = null;
      for (int i=0; i < attrs.size(); i++)
      {
         retAttrib = (LDIFAttribute)attrs.elementAt(i);
         if (attrName.equalsIgnoreCase(retAttrib.getName()))
            break; 
         retAttrib = null;
      }
         
      return retAttrib;
   }

   /**
    * Removes the LDIFAttribute object of the specified attribute name.
    *
    * @param attrName Name of the attribute.
    */
   public synchronized void removeAttribute(String attrName)
   {
      this.removeAttribute(attrName, false);
   }

   /**
    * Returns the LDIFAttribute object of the specified attribute name.
    *
    * @param attrName Name of the attribute.
    * @param baseName whether the attrName is a basename or with subtypes
    */
   public synchronized void removeAttribute(String attrName, boolean baseName)
   {
      LDIFAttribute attr = null;
      int sz = attrs.size();
      int i = 0;
      String aName = null;

      while ( i < sz )
      {
         attr = (LDIFAttribute)attrs.elementAt(i);
         aName = baseName ? attr.getBaseName() : attr.getName();
         if ( attrName.equalsIgnoreCase(aName) )
         {
            attrs.removeElement(attr);
            if ( !baseName )
               break;
            else
               sz--;
         }
         else 
         {
            i++; 
         }
      }
   }

   /**
    * Returns the LDIFAttribute object at the specified index in a record
    * @param index - an index into this ldifrecord
    * @return LDIFAttribute at the specified index
    * @throws ArrayIndexOutOfBoundsException if an invalid index was given. 
    */
   public synchronized LDIFAttribute getAttribute(int index)
   {
      if (index < 0 || index >= this.size() )
         throw new IndexOutOfBoundsException("Invalid Index : "+index);
        
      return (LDIFAttribute)attrs.elementAt(index);
   }

   /**
    * Returns an Enumeration of the attributes in this record.
    *
    * @return an enumeration containing all the LDIFAttribute objects.
    */
   public Enumeration getAll()
   {
      return attrs.elements();
   }

   /** 
    * Returns true if the ldifrecord contains the specified attribute.
    * @param attrName the attribute Name
    * @return boolean  true if the record contains specified attribute, 
    * false otherwise
    */
   public boolean contains(String attrName)
   {
      LDIFAttribute attribute = getAttribute(attrName);

      if ( attribute == null)
         return false;
      else
         return true;
   }

   /**
    * Retrieves an enumeration of the ids of the attributes in this record
    * as <CODE>String</CODE> objects.
    * 
    * @return A non-null enumeration of the attributes' ids in this record
    * set. If attribute set has zero attributes, an empty enumeration is
    * returned.
    */
   public synchronized Enumeration getIDs()
   {
      Vector<String> retVect = new Vector<String>();
      
      for (int i = 0; i < attrs.size(); i++)
      {
         LDIFAttribute attribute = (LDIFAttribute) attrs.elementAt(i);
         retVect.addElement(attribute.getName());
      }

      return retVect.elements();
   }

   /**
    * Retrieves the change type of this record. Change type constants are 
    * defined in LDIF class.
    *
    * @return the record change type.
    */
   public int getChangeType()
   {
      return changeType;
   }
      
   /**
    * set the record change type.
    * 
    * @param changeType record change type.
    */
   public void setChangeType(int changeType)
   {
      this.changeType = changeType;
   }

   /**
    * Returns the number of attributes in this record. 
    * Count does not include the dn.
    *
    * @return number of attributes in this record.
    */
   public int size()
   {
      return attrs.size();
   }

   /**
    * to create a replica of this object.
    *
    * @return a replica of this object.
    */
   public Object clone()
   {
      //[TBD]
      return null;
   }

   /**
    * Sets the new RDN if the record change type is MODDN
    * @param newRdn the New RDN
    */
   synchronized void setNewRdn(String newRdn, boolean deleteOldRdn, 
                                                   String newSuperior )
   {
      if (changeType == LDIF.RECORD_CHANGE_TYPE_MODDN )
      {
         this.newRdn = newRdn;
         this.deleteOldRdn = deleteOldRdn;
         this.newSuperior = newSuperior;
      }
   }
   
   /**
    * Use this method to Obtain all the attriubtes of an LDIFRecord
    * represented as <CODE>javax.naming.directory.Attributes</CODE> object.
    * 
    * @return javax.naming.directory.Attributes object representing all
    * the attributes in this record. Returns <CODE>null</CODE> if this
    * record does not contain any attribute.
    */
   public synchronized Attributes getJNDIAttributes() 
   {
      Attributes retAttrs =  null;
      LDIFAttribute ldifAttribute = null;
      int noAttrs = attrs.size();

      /* This record does contain attributes */
      if ( noAttrs != 0 )
      {
         /* BasicAttribute is an implementation of Attributes interface */
         retAttrs = new BasicAttributes();

         /* for every attribute */
         for (int i=0; i < noAttrs; i++)
         {
            ldifAttribute = (LDIFAttribute)attrs.elementAt(i);
            /* add the Attribute to the Attributes list */
            retAttrs.put(ldifAttribute.getJNDIAttribute());
         }
      }

      return retAttrs;
   }

   /**
    * Use this method to Obtain all the attriubtes of an LDIFRecord
    * represented as an array of 
    * <CODE>javax.naming.directory.ModificationItem</CODE> objects.
    *
    * @return javax.naming.directory.Attributes object representing all
    * the attributes in this record. Returns <CODE>null</CODE> if this
    * record does not contain any attribute.
    */
   public synchronized ModificationItem [] getJNDIModificationItems()
   {
      ModificationItem [] retModItems =  null;
      LDIFAttribute ldifAttribute = null;
      int noAttrs = attrs.size();

      /* This record does contain attributes */
      if ( noAttrs != 0 )
      {
         retModItems = new ModificationItem[noAttrs];

         /* for every attribute */
         for (int i=0; i < noAttrs;i++)
         {
            ldifAttribute = (LDIFAttribute)attrs.elementAt(i);
            /* add the Attribute to the Attributes list */
            retModItems[i] = ldifAttribute.getJNDIModificationItem();
         }
      }

      return retModItems;
   }

   /**
    * This method returns the LDIFRecord as string.
    *
    * @param asComment if ture the first character of the line will be 
    * '#', indicating that it is a comment line.
    * @return a string representation of the LDIF record
    */
   String getRecordAsString(boolean asComment)
   {
      return getRecordAsString(asComment, true);
   }

   /**
    * This method returns the LDIFRecord as string.
    *
    * @param asComment if ture the first character of the line will be 
    * '#', indicating that it is a comment line.
    * @param wrap if true the line will be wrapped and continued in the next
    * line with ' ' at the begining.
    * @return a string representation of the LDIF record
    */
   String getRecordAsString(boolean asComment, boolean wrap)
   {
      Enumeration attributes = getAll();
      String newLine = System.getProperty("line.separator");
      StringBuffer retBuf = new StringBuffer();
      String cmt = asComment ? "#" : "";
     
      if (dn!=null)
         retBuf.append(
               LDIFAttribute.getAttrAsLDIFStr("dn", getDN(), asComment, wrap))
            .append(newLine);
      
      if (changeType == LDIF.RECORD_CHANGE_TYPE_DELETE)
         retBuf.append(cmt+"changetype: delete").append(newLine);

      else if (changeType == LDIF.RECORD_CHANGE_TYPE_MODIFY)
         retBuf.append(cmt+"changetype: modify").append(newLine);
      
      else if (changeType == LDIF.RECORD_CHANGE_TYPE_MODDN)
         retBuf.append(cmt+"changetype: moddn").append(newLine);
      
      if ( changeType == LDIF.RECORD_CHANGE_TYPE_ADD )
      {
         if ( expChgType )
            retBuf.append(cmt+"changetype: add").append(newLine);
            
         while(attributes.hasMoreElements())
         {
            LDIFAttribute attribute = (LDIFAttribute)attributes.nextElement();
            int atrChangeType = attribute.getChangeType();
            String atrName = attribute.getName();
            Enumeration values = attribute.getByteValues();

            while ( values.hasMoreElements() )
            {
               byte byteValues[] = (byte[]) values.nextElement();
               retBuf.append(LDIFAttribute.getAttrAsLDIFStr(atrName, 
                        byteValues, asComment, wrap, attribute.isBinary()))
                  .append(newLine);
            }
         }
      }
      else if ( changeType == LDIF.RECORD_CHANGE_TYPE_MODDN )
      {
         retBuf.append(LDIFAttribute.getAttrAsLDIFStr("newrdn", newRdn, 
                                             asComment, wrap))
               .append(newLine).append(cmt+"deleteoldrdn: ");
         if (deleteOldRdn)
            retBuf.append("1");
         else
            retBuf.append("0");
         retBuf.append(newLine);
         if ( newSuperior != null)
            retBuf.append(LDIFAttribute.getAttrAsLDIFStr("newsuperior", 
                                       newSuperior, asComment, wrap))
                  .append(newLine);
      }
      else
      {
         while(attributes.hasMoreElements())
            retBuf.append(((LDIFAttribute)attributes.nextElement()).
                                       getAttributeAsString(asComment, wrap));
      }

      return retBuf.toString();
   }

   /**
    * A string representation of this object
    */
   public String toString()
   {
      return getRecordAsString(false);
   }
}
