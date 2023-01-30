# RailRunner

A java-based project designed for a simple Train querying and booking system using a postgres database

![Description of the services](/RailRunner/flow.png?raw=true "Flow")

 1. RailRunner (Service B)
 
    This service consists of multiple REST APIs for Individual train details, Schedules, Trip details and subsequently ticket information by connecting to a postgres database.
    External JAR used: 
	 - postgresql-42.5.1
	 - org.restlet
	 - java-json

  
 3. WS_RailRunner (Service A)
 
    2 SOAP based services to search and book trains using the REST APIS in service B 
  

 4.  RailRunnerClient (Client)
     
     A simple client that is connected to the SOAP based service that calls the appropriate service and returns the desired information.

 


