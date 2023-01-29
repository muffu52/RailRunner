package RESTservices;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import postgresql.psqlConnect;

import org.json.JSONArray;

public class RESTResource extends ServerResource{
	@Get("json")
	 public String Present() {  
	     // Print the requested URI path  
		 psqlConnect psql = new psqlConnect();
		 JSONArray arr;
		 String company = (String) getRequestAttributes().get("company");
	     arr = psql.getTrainInfo(company);
		 
	     return arr.toString();
	 }  
}
