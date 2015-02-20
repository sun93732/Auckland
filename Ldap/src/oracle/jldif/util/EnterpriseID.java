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
 bkottaha     04/10/10 - Creation
 */

package oracle.jldif.util;

import java.util.Calendar;
import java.util.Date;
import java.lang.InterruptedException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.math.BigInteger;
import java.lang.Long;
import java.nio.ByteBuffer;
import java.lang.Cloneable;

final public class EnterpriseID implements Cloneable {

  //Constants
  private static final long TIME_OFFSET = 1270164433755L; //04/01/2010 4:26pm
  private static final long TIME_RANGE  = 1099511627776L; // 2^40 ~ 34.84 years

  private static final String MESSAGE_DIGEST_ALGORITHM  = "SHA-1";
  private static final int NODE_ADDRESS_SIZE_IN_BYTES   = 2;
  private static final int TIME_COMPONENT_SIZE_IN_BYTES = 5;
  private static final int ENTERPRISE_ID_SIZE_IN_BYTES  = 7;
  private static final int ENT_ID_OUT_SIZE_IN_BYTES = 8;

  private long lastSystemTime;
  private BigInteger timeRange = new BigInteger(Long.toString(TIME_RANGE));
  private byte[] nodeID = getNodeIDComponent();
  private static EnterpriseID tenantIDGenerator = new EnterpriseID();

  static EnterpriseID getInstance() {
    return tenantIDGenerator;
  }  

  public Long generateEnterpriseID() {

    long tenantID = 0;
  
    // Ensure generated value is outside reserved range
    while (tenantID < 11) {
      byte[] enterpriseID = new byte[ENT_ID_OUT_SIZE_IN_BYTES];
      byte[] tc = getTimeComponent();
      System.arraycopy(nodeID,0,enterpriseID,1,NODE_ADDRESS_SIZE_IN_BYTES);
      System.arraycopy(tc,0,enterpriseID,3,TIME_COMPONENT_SIZE_IN_BYTES);
      ByteBuffer bb = ByteBuffer.wrap(enterpriseID);
      tenantID = bb.getLong();
    }
    
    return new Long(tenantID);
  }
  
  private static byte[] getNodeIDComponent() {

    byte[] hash = null;

    try {

      MessageDigest messageDigest = 
          MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
      InetAddress nodeAddress = InetAddress.getLocalHost();

      if(!nodeAddress.isLoopbackAddress()){
        //Add hostname and address to the MessageDigest
        messageDigest.update(nodeAddress.getCanonicalHostName().getBytes());
        messageDigest.update(nodeAddress.getAddress());
      }
      
      //Add System Property Values to the MessageDigest
      Collection systemValues = System.getProperties().values();
      Iterator systemValuesIter = systemValues.iterator();
      while(systemValuesIter.hasNext()){
        messageDigest.update(((String)systemValuesIter.next()).getBytes());
      }

      hash = messageDigest.digest();

    } catch (NoSuchAlgorithmException e) {
      //Will not happen as we use a standard MessageDigest provided in Java.
    } catch (UnknownHostException e) {
      //Unlikely to fail since we are fetching InetAddress of localhost
      //If at all,then Ignore.We can do without the IP Address Information
    }
    
    // Only grab the maxium number of bytes we can use
    byte[] nodeID = new byte[NODE_ADDRESS_SIZE_IN_BYTES];
    System.arraycopy(hash,0,nodeID,0,NODE_ADDRESS_SIZE_IN_BYTES);

    return nodeID;

  }

  private byte[] getTimeComponent() {
    long ticks = getTimeOffset();
    ticks = ticks - TIME_OFFSET;
    BigInteger bTicks = new BigInteger(Long.toString(ticks));

    //Bring it to our desired range
    bTicks = bTicks.mod(timeRange);
    byte[] hash1 = bTicks.toByteArray();
    byte[] hash2 = new byte[TIME_COMPONENT_SIZE_IN_BYTES];
    System.arraycopy(hash1,0,hash2,TIME_COMPONENT_SIZE_IN_BYTES-hash1.length,
                     hash1.length);
    return hash2;
  }

  private synchronized long getTimeOffset() {
    long currentSystemTime = System.currentTimeMillis();
    while(currentSystemTime == lastSystemTime) {
      try {
        Thread.currentThread().sleep(1);
        currentSystemTime = System.currentTimeMillis();
      } catch (InterruptedException e) {
      }
    }
    lastSystemTime = currentSystemTime;
    return currentSystemTime;
  }


  public static void main(String[] args) {

    EnterpriseID tidg = new EnterpriseID();
    System.out.println(tidg.generateEnterpriseID());

  } 

}
