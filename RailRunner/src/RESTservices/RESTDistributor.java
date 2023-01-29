package RESTservices;

import org.restlet.Component;
import org.restlet.data.Protocol;

import RESTservices.RESTResource;

public class RESTDistributor {

	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Create a new Restlet component and add a HTTP server connector to it  
	     Component component = new Component();
             // if you have an HTTP connector listening on the same port (i.e. 8182), you will get an error
             // in this case change the port number (e.g. 8183)
	     component.getServers().add(Protocol.HTTP, 8182);
	     // Then attach it to the local host  
	     
	     component.getDefaultHost().attach("/trainDetails", RESTResource.class); 
	     component.getDefaultHost().attach("/trainDetails/{company}", RESTResource.class); 
	     component.getDefaultHost().attach("/trainSchedule", RESTSchedule.class);
	     component.getDefaultHost().attach("/trainSchedule/{departure}", RESTSchedule.class);
	     component.getDefaultHost().attach("/trainSchedule/{departure}/{arrival}", RESTSchedule.class);
	     component.getDefaultHost().attach("/trainSchedule/{departure}/{arrival}/{d_time}", RESTSchedule.class);
	     component.getDefaultHost().attach("/trainSchedule/{departure}/{arrival}/{d_time}/{a_time}", RESTSchedule.class);
	     component.getDefaultHost().attach("/trip", RESTTrip.class);
	     component.getDefaultHost().attach("/trip/{departure}", RESTTrip.class);
	     component.getDefaultHost().attach("/trip/{departure}/{arrival}", RESTTrip.class);
	     component.getDefaultHost().attach("/trip/{departure}/{arrival}/{date}", RESTTrip.class);
	     component.getDefaultHost().attach("/trip/{departure}/{arrival}/{date}/{d_time}", RESTTrip.class);
	     component.getDefaultHost().attach("/trip/{departure}/{arrival}/{date}/{d_time}/{a_time}", RESTTrip.class);
	     component.getDefaultHost().attach("/ticket", RESTTicket.class);
	     component.getDefaultHost().attach("/ticket/{name}", RESTTicket.class);
	     component.getDefaultHost().attach("/ticket/{name}/{date}", RESTTicket.class);
//
//	     component.getDefaultHost().attach("/trainSchedule", RESTSchedule.class); 
//	     component.getDefaultHost().attach("/trainSchedule", RESTSchedule.class); 
 
	     // Now, let's start the component!  
	     // Note that the HTTP server connector is also automatically started.  
	     component.start();  
	}	 
	

	

}
