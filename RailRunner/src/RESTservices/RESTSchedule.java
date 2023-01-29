package RESTservices;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import postgresql.psqlConnect;

import org.json.JSONArray;

public class RESTSchedule extends ServerResource {
	
	@Get("json")
	 public String present() {  
	     // Print the requested URI path  
		 psqlConnect psql = new psqlConnect();
		 JSONArray arr;
		 String departure = (String) getRequestAttributes().get("departure");
		 String arrival = (String) getRequestAttributes().get("arrival");
		 String d_time = (String) getRequestAttributes().get("d_time");
		 String a_time = (String) getRequestAttributes().get("a_time");
		 
		arr = psql.getTrainSchedule(departure, arrival, d_time, a_time);
		 
	     return arr.toString();
	 }  

}
