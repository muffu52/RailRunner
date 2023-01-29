package RESTservices;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.ext.json.JsonRepresentation;

import postgresql.psqlConnect;

public class RESTTicket extends ServerResource  {
	
	@Get("json")
	 public String present() {  
	     // Print the requested URI path  
		 psqlConnect psql = new psqlConnect();
		 JSONArray arr;
		 String name = (String) getRequestAttributes().get("name");
		 String date = (String) getRequestAttributes().get("date");
		 if (date != null) {
			 try {
				    new SimpleDateFormat("yyyy-DD-mm").parse(date);
				    arr = psql.getTicketInfo(name, date);
				    return arr.toString();
				} catch (ParseException e) {
				    return "Date must be in YYYY-MM-DD format";
				}
		 }else {
			 arr = psql.getTicketInfo(name, date);
			 return arr.toString();
		 } 
	     
	 }  
	@Post("json")
    public String acceptJsonRepresentation(JsonRepresentation entity) {  
	
	    
	    JSONObject json = new JSONObject();
	    
	    try {
	        json = entity.getJsonObject();
	        
	        if(!json.has("trip_id")) {
	        	return "trip_id is required";
	        }
			if(!json.has("date_of_purchase")) {
				return "date_of_purchase is required";
				        	
	        }
			if(!json.has("passenger_name")) {
				return "passenger_name is required";
				
			}
			if(!json.has("fare")) {
				return "fare is required";
				
			}
			if(!json.has("ticket_class")) {
				return "ticket_class is required";
				
			}
			
			String trip_id = (String) json.get("trip_id");
	        String date_of_purchase = (String) json.get("date_of_purchase"); 
	        String passenger_name = (String) json.get("passenger_name"); 
	        Double fare = (Double) json.get("fare"); 
	        String ticket_class = (String) json.get("ticket_class"); 
	        System.out.println(json);
	        
	        psqlConnect psql = new psqlConnect();
	        String result = psql.addTicketInfo(trip_id, date_of_purchase, passenger_name, fare, ticket_class);
	        return result;
	        

	    } catch (JSONException e) {
	        setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
	        return "BAD REQUEST";
	    }
  
    }

}
