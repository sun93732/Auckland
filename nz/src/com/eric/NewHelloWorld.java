

/**
 * NewHelloWorld.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.eric;

    /*
     *  NewHelloWorld java interface
     */

    public interface NewHelloWorld {
          

        /**
          * Auto generated method signature
          * 
                    * @param getAge0
                
         */

         
                     public com.eric.GetAgeResponse getAge(

                        com.eric.GetAge getAge0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getAge0
            
          */
        public void startgetAge(

            com.eric.GetAge getAge0,

            final com.eric.NewHelloWorldCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param sayHello2
                
         */

         
                     public com.eric.SayHelloResponse sayHello(

                        com.eric.SayHello sayHello2)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param sayHello2
            
          */
        public void startsayHello(

            com.eric.SayHello sayHello2,

            final com.eric.NewHelloWorldCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    