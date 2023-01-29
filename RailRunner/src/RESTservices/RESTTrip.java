package RESTservices;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import postgresql.psqlConnect;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;

public class RESTTrip extends ServerResource {
	
	@Get("json")
	 public String present() {  
	     // Print the requested URI path  
		 psqlConnect psql = new psqlConnect();
		 JSONArray arr;
		 String departure = (String) getRequestAttributes().get("departure");
		 String arrival = (String) getRequestAttributes().get("arrival");
		 String date = (String) getRequestAttributes().get("date");
		 String d_time = (String) getRequestAttributes().get("d_time");
		 String a_time = (String) getRequestAttributes().get("a_time");
		 if (date != null) {
			 try {
				    new SimpleDateFormat("yyyy-DD-mm").parse(date);
				    arr = psql.getTrainTrip(departure, arrival, date, d_time, a_time);
				    return arr.toString();
				} catch (ParseException e) {
				    return "Date must be in YYYY-MM-DD format";
				}
		 }else {
			 arr = psql.getTrainTrip(departure, arrival, date, d_time, a_time);
			 return arr.toString();
		 }
	
	     
	 }  
	
	 

}
