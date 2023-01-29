package tps.ws.deployment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;

import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter; 
//import org.json.JSONException;
import org.json.JSONObject;

public class BookTicket {
	
//	String departure = "Chelles Gournay";
//	String arrival = "Haussmann St Lazare";
//	String date = "";
//	String d_time = "";
//	String a_time = "";
	
	public String reserve(String departure, String arrival, String date, String d_time, String a_time, String name, String ticket_class){
		
		String r = null;
		r = getApiRequest(departure, arrival, date, d_time, a_time, ticket_class);
		if (r == "All" || r == "first class" ||r == "business class" ||r == "standard class") {
			return r +  " tickets are fully booked";
		}
		int resp = postApiRequest(r, name, ticket_class);
		if (resp == 200) {
			return "Ticket booked Successfully";
		}else if(resp == 406){
			return "Please enter the name";
		}else {
			return "Ticket cannot be booked";
		}
		
	}
	
	public static String getApiRequest(String departure, String arrival, String date, String d_time, String a_time, String ticket_class) {
		
		String link = "http://localhost:8182/trip";
		if(departure != null || departure.length() != 0) {
        	link = link + "/"+ departure;
        }
        if(arrival != null || arrival.length() != 0) {
        	link = link + "/"+ arrival;
        }
        if(date != null || date.length() != 0) {
        	link = link + "/"+ date;
        }
        if(d_time != null || d_time.length() != 0) {
        	link = link + "/"+ d_time;
        }
        if(a_time != null || a_time.length() != 0) {
        	link = link + "/"+ a_time;
        }
		
		String newUrlString = link.replaceAll(" ", "%20");
	
		String str = "";
		String a_s = "";
		String a_fs = "";
		String a_bs = "";
		String a_ss = "";
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
	            JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(result);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	if (jsonArray.length() > 0) {
		              JSONObject obj = null;
					try {
						obj = jsonArray.getJSONObject(0);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		              try {
						str = obj.get("id").toString();
						a_s = obj.get("available_seats").toString();
						a_fs = obj.get("available_seats_first_class").toString();
						a_bs = obj.get("available_seats_business_class").toString();
						a_ss = obj.get("available_seats_standard_class").toString();
						System.out.println(str);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		              int a_num = Integer.parseInt(a_s);
		              if (a_num == 0) {
		            	  return "All";
		              }else if(ticket_class == "first" ) {
		            	  int af_num = Integer.parseInt(a_fs);
		            	  if (af_num == 0) {
			            	  return "first class";
			              }
		              }else if(ticket_class == "business" ) {
		            	  int ab_num = Integer.parseInt(a_bs);
		            	  if (ab_num == 0) {
			            	  return "business class";
			              }
		              }else if(ticket_class == "standard" ) {
		            	  int as_num = Integer.parseInt(a_ss);
		            	  if (as_num == 0) {
			            	  return "standard class";
			              }
		              }
		             
	        		}
	            

	        } else {
	            System.out.println(responseCode);
	        }
			return str;
	
	    }
	
	

    public static int postApiRequest(String r, String name, String ticket_class) {
    	int responseCode = 0;
    	if(name == null || name.length() == 0) {
			responseCode = 406;
			return responseCode;
    	}
        
        // Url for making POST request
        URL postUrl = null;
		try {
			postUrl = new URL("http://localhost:8182/ticket");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) postUrl.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        // Set POST as request method
        try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Setting Header Parameters
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        // Adding Body section of POST request
        JSONObject item = new JSONObject();
        double fare = 2.05;
        if (ticket_class == "first") {
        	
        	fare = 5.05;
        }else if(ticket_class == "business") {
        	fare = 3.55;
        }
        
        long millis=System.currentTimeMillis();  
        
        // creating a new object of the class Date  
        java.sql.Date date = new java.sql.Date(millis);       
        System.out.println(date);
        try {
			item.put("trip_id", r);
			item.put("date_of_purchase", date);
	        item.put("passenger_name", name);
	        item.put("fare", fare);
	        item.put("ticket_class", ticket_class);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        System.out.println(item);
//        String params="trip_id="+r+"&date_of_purchase=2023-01-26&passenger_name=Hassan&fare=2.00&ticket_class=standard";
        String params = item.toString();
        
        
        byte[] out = params.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        connection.setFixedLengthStreamingMode(length);
        OutputStream wr = null;
		try {
			wr =  connection.getOutputStream();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			wr.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			connection.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Getting Response 
        
		try {
			responseCode = connection.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	// Checking ckode for 201 (Created)
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
        
            StringBuffer jsonResponseData = new StringBuffer();
	    String readLine = null;
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
			    new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    try {
			while ((readLine = bufferedReader.readLine()) != null) {
			    jsonResponseData.append(readLine + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(jsonResponseData.toString());

        } else {
             System.out.println(responseCode);
	}
        return responseCode;
    }
    
}


