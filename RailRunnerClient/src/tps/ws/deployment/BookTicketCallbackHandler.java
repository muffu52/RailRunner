
/**
 * BookTicketCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package tps.ws.deployment;

    /**
     *  BookTicketCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class BookTicketCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public BookTicketCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public BookTicketCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getApiRequest method
            * override this method for handling normal response from getApiRequest operation
            */
           public void receiveResultgetApiRequest(
                    tps.ws.deployment.BookTicketStub.GetApiRequestResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getApiRequest operation
           */
            public void receiveErrorgetApiRequest(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for reserve method
            * override this method for handling normal response from reserve operation
            */
           public void receiveResultreserve(
                    tps.ws.deployment.BookTicketStub.ReserveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reserve operation
           */
            public void receiveErrorreserve(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for postApiRequest method
            * override this method for handling normal response from postApiRequest operation
            */
           public void receiveResultpostApiRequest(
                    tps.ws.deployment.BookTicketStub.PostApiRequestResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from postApiRequest operation
           */
            public void receiveErrorpostApiRequest(java.lang.Exception e) {
            }
                


    }
    