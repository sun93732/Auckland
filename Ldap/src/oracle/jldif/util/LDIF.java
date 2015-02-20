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

/**
 * A class that defines most common thing that pertain to LDAP data 
 * interchange format.
 */

public class LDIF 
{
   /**
    * Record change type - Add
    */
   public final static int RECORD_CHANGE_TYPE_ADD = 1;

   /**
    * Record change type - Delete
    */
   public final static int RECORD_CHANGE_TYPE_DELETE = 2;

   /**
    * Record chagne type - Modify 
    */
   public final static int RECORD_CHANGE_TYPE_MODIFY = 3;

   /**
    * Record change type - Moddn
    */
   public final static int RECORD_CHANGE_TYPE_MODDN = 4;

   /**
    * Record change type - ModRAdd
    */
   public final static int RECORD_CHANGE_TYPE_MODRADD = 5;

   /**
    * Record change type - Modrdn
    */
   public final static int RECORD_CHANGE_TYPE_MODRDN = 6;


   /**
    * Attribute change type - Add
    */
   public final static int ATTRIBUTE_CHANGE_TYPE_ADD = 5;

   /**
    * Attribute change type - Delete
    */
   public final static int ATTRIBUTE_CHANGE_TYPE_DELETE = 6;
   
   /**
    * Attribute change type - Replace
    */
   public final static int ATTRIBUTE_CHANGE_TYPE_REPLACE = 7;

   /**
    * Offset for DIP, due to difference in constant values.
    * Used inside the APIs and for DIP consumption only. Not to be publsihed.
    */
// JAVADOC_EXCLUDE
   public final static int DIP_ATTRCHGTYPE_OFFSET = 5;
// END_JAVADOC_EXCLUDE
 
}
