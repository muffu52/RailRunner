package tps.ws.deployment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SearchTrain {
//	type = "Schedule";
//	departure = "Chelles Gournay";
//	arrival = "";
//	date = "";
//	d_time = "";
//	a_time = "";
//	company = "";
	
	
	public String search(String type, String departure, String arrival, String date, String d_time, String a_time, String company, String name){

//		type = "Schedule";
//		departure = "Chelles Gournay";
//		arrival = "";
//		date = "";
//		d_time = "";
//		a_time = "";
//		company = "";
		String r = null;
		r = getApiRequest(type, departure, arrival,  date, d_time, a_time, company, name);
		
		return r;
	}
	
	public static String getApiRequest(String type, String departure, String arrival, String date, String d_time, String a_time, String company, String name) {
		
	    String link = "";
	    
//		type = "Schedule";
//		departure = "Chelles Gournay";
//		arrival = "";
//		date = "";
//		d_time = "";
//		a_time = "";
//		company = "";
	    
	    switch(type) {
	    case "schedule":
	    	
	    	link = "http://localhost:8182/trainSchedule";
			
			if (departure != "") {
				link =  "http://localhost:8182/trainSchedule/"+ departure;
				}
			if (arrival != "") {
				link = "http://localhost:8182/trainSchedule/"+ departure +"/"+arrival;
				}
			if (d_time != "") {
				link = "http://localhost:8182/trainSchedule/"+departure +"/"+arrival +"/"+d_time;
				}
			if (a_time != "") {
				link = "http://localhost:8182/trainSchedule/"+departure +"/"+arrival +"/"+d_time+"/"+a_time;
				}
	      break;
	    case "trip":
	    	link = "http://localhost:8182/trip";
			
			if (departure != "") {
				link =  "http://localhost:8182/trip/"+ departure;
				}
			if (arrival != "") {
				link = "http://localhost:8182/trip/"+ departure +"/"+arrival;
				}
			if (date != "") {
				link = "http://localhost:8182/trip/"+departure +"/"+arrival +"/"+date;
				}
			if (d_time != "") {
				link = "http://localhost:8182/trip/"+departure +"/"+arrival +"/"+date +"/"+d_time;
				}
			if (a_time != "") {
				link = "http://localhost:8182/trip/"+departure +"/"+arrival +"/"+date +"/"+d_time+"/"+a_time;
				}
	      // code block
	      break;
	    case "ticket":
	    	link = "http://localhost:8182/ticket";
			
			if (name != "") {
				link =  "http://localhost:8182/ticket/"+ name;
				}
			if (date != "") {
				link = "http://localhost:8182/ticket/"+ name +"/"+date;
				}
		      break;
	    default:
	    	link = "http://localhost:8182/trainDetails";
			if (company != "") {
				link = "http://localhost:8182/trainDetails/"+company;
			}
	      // code block
	    }
	   

		
		String newUrlString = link.replaceAll(" ", "%20");

		
		URL getUrl = null;
		try {
			getUrl = new URL(newUrlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        HttpURLConnection conection = null;
		try {
			conection = (HttpURLConnection) getUrl.openConnection();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        
        // Set request method
        try {
			conection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String result = "";
        // Getting response code
        int responseCode = 0;
		try {
			responseCode = conection.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // If responseCode is 200 means we get data successfully
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
            StringBuffer jsonResponseData = new StringBuffer();
            String readLine = null;
            
            // Append response line by line
            try {
				while ((readLine = in.readLine()) != null) {
				    jsonResponseData.append(readLine);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
            
            try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // Print result in string format
            System.out.println("JSON String Data " + jsonResponseData.toString());
            result = jsonResponseData.toString();
        } else {
            System.out.println(responseCode);
        }
		return result;
	
	    }
}
