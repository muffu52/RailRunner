package postgresql;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; 
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RESTservices.RESTSchedule;

public class psqlConnect {
	private final String url = "jdbc:postgresql://localhost/postgres";
    private final String user = "postgres";
    private final String password = "Enayath52";
    
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//    	psqlConnect app = new psqlConnect();
//        app.getTrainInfo();
//    }
    
    
    
    public JSONArray getTrainInfo(String company) {
    	
        String SQL = "SELECT train_id, no_of_cars, capacty, train_type, company FROM public.train_information";
        if(company != null) {
        	SQL = SQL + " Where company = '" + company + "'";
        }
        SQL = SQL + " ;";
        JSONArray arr = null;
        try (Connection conn = connect();
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(SQL)) {
			arr = testArray(rs);
		} catch (SQLException ex) {
		    System.out.println(ex.getMessage());
		}
		
		return arr;
    }
    
    
    
    public JSONArray getTicketInfo(String name, String date) {
    	
        String SQL = "SELECT id, trip_id, date_of_purchase, passenger_name, fare, ticket_class FROM public.ticket";
        if(name != null) {
        	name = CheckSpace(name);
        	SQL = SQL + " Where passenger_name = '" + name + "'";
        }
        if(date != null) {
        	date = CheckSpace(date);
        	SQL = SQL + " AND date_of_purchase = '" + date + "'" ;
        }
        SQL = SQL + " ;";
        JSONArray arr = null;
        try (Connection conn = connect();
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(SQL)) {
			arr = testArray(rs);
		} catch (SQLException ex) {
		    System.out.println(ex.getMessage());
		}
		
		return arr;
    }

    public JSONArray getTrainSchedule(String departure, String arrival, String d_time, String a_time) {
    	
    	
        String SQL = "SELECT id, train_id, departure_station, arrival_station, departure_time, arrival_time FROM public.train_schedule";
        
        if(departure != null) {
        	departure = CheckSpace(departure);
        	SQL = SQL + " Where departure_station = '" + departure + "'";
        }
        if(arrival != null) {
        	arrival = CheckSpace(arrival);
        	SQL = SQL + " AND arrival_station = '" + arrival + "'" ;
        }
        if(d_time != null) {
        	SQL = SQL + " AND departure_time = '" + d_time + "'" ;
        }
        if(a_time != null) {
        	SQL = SQL + " AND arrival_time = '" + a_time + "'" ;
        }
        
        SQL = SQL + " ;";
        JSONArray arr = null;
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
        	arr = testArray(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return arr;
    }
    
    public String CheckSpace(String str) {
    	
    	String str1 = "";
    	
		try {
			str1 = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			
		}
		return str1;
		
		
    	
    }
    
    public JSONArray getTrainTrip(String departure, String arrival, String date, String d_time, String a_time) {
    	
    	
        String SQL = "SELECT id, train_id, departure_station, arrival_station, travel_date, departure_time, arrival_time, available_seats, available_seats_first_class, available_seats_business_class, available_seats_standard_class FROM public.trip ";
        
        if(departure != null) {
        	departure = CheckSpace(departure);
        	SQL = SQL + " Where departure_station = '" + departure + "'";
        }
        if(arrival != null) {
        	arrival = CheckSpace(arrival);
        	SQL = SQL + " AND arrival_station = '" + arrival + "'" ;
        }
        if(date != null) {
        	SQL = SQL + " AND travel_date = '" + date + "'" ;
        }
        if(d_time != null) {
        	SQL = SQL + " AND departure_time = '" + d_time + "'" ;
        }
        if(a_time != null) {
        	SQL = SQL + " AND arrival_time = '" + a_time + "'" ;
        }
        
        SQL = SQL + " ;";
        JSONArray arr = new JSONArray();
        String str = null;
		//        JSONArray arr = null;
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
        	arr = testArray(rs);
//        	if (arr.length() > 0) {
//                JSONObject obj =  arr.getJSONObject(0);
//                str = obj.get("id").toString();
//                
//                System.out.println(obj.get("id"));
//            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            arr.put(ex.getMessage());
        }
		
		return arr;
    }
    
    
    
    public String addTicketInfo(String trip_id, String date_of_purchase, String passenger_name, Double fare, String ticket_class) {
    	
        String SQL = "INSERT INTO public.ticket (id, trip_id, date_of_purchase, passenger_name, fare, ticket_class) VALUES(gen_random_uuid(),"
        		+ " '"+trip_id+"', TO_DATE('"+date_of_purchase+"','YYYY-MM-DD'),"
        		+ " '"+passenger_name+"', "+fare+", '"+ticket_class+"');";
        try  {
        	Connection conn = connect();
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(SQL);
	        updateTrip(trip_id, ticket_class);
	        return "Success";
		} catch (SQLException ex) {
		    System.out.println(ex.getMessage());
		    return ex.getMessage();
		}
		
    }
    
    public void updateTrip(String id, String ticket_class) {
    	String query = "update public.trip set available_seats = available_seats - 1, ";
    	try {
    		Connection conn = connect();
    		if (ticket_class == "first") {
    			query = query + " available_seats_first_class = available_seats_first_class - 1 ";
    		}else if(ticket_class == "business") {
    			query = query + " available_seats_business_class = available_seats_business_class - 1 ";
    		}else {
    			query = query + " available_seats_standard_class = available_seats_standard_class - 1 ";
    		}
    		query = query + "  where id = '"+ id + "';";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();
            
            conn.close();
    		
    	}catch(Exception e)
        {
    	      System.err.println("Got an exception! ");
    	      System.err.println(e.getMessage());
    	    }
    	
    }
    


    
	public JSONArray testArray(ResultSet resultSet ) throws SQLException {
    	
    	ResultSetMetaData md = resultSet.getMetaData();
    	int numCols = md.getColumnCount();
    	List<String> colNames = IntStream.range(0, numCols)
    	  .mapToObj(i -> {
    	      try {
    	          return md.getColumnName(i + 1);
    	      } catch (SQLException e) {
    	          e.printStackTrace();
    	          return "?";
    	      }
    	  })
    	  .collect(Collectors.toList());

    	JSONArray result = new JSONArray();
    	while (resultSet.next()) {
    	    JSONObject row = new JSONObject();
    	    colNames.forEach(cn -> {
    	        try {
    	            row.put(cn, resultSet.getObject(cn));
    	        } catch (JSONException | SQLException e) {
    	            e.printStackTrace();
    	        }
    	    });
    	    result.put(row);
    	}
    	return result;
    }

}
