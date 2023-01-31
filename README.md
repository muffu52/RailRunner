# RailRunner

A java-based project designed for a simple Train querying and booking system using a postgres database

![Description of the services](/RailRunner/flow.png?raw=true "Flow")

 1. RailRunner (Service B)
 
    This service consists of multiple REST APIs for Individual train details, Schedules, Trip details and subsequently ticket information by connecting to a postgres database.
    External JAR used: 
	 - postgresql-42.5.1
	 - org.restlet
	 - java-json

  
 2. WS_RailRunner (Service A)
 
    2 SOAP based services to search and book trains using the REST APIS in service B 
    - apache-tomcat-9.0.52
    - axis2-1.6.2
  

 3.  RailRunnerClient (Client)
     
     A simple client that is connected to the SOAP based service that calls the appropriate service and returns the desired information.
     
 Database (Postgres)
 
 - Train Information: This table would include information on the train itself, such as the number of cars, seating capacity, and type of train (e.g. high-speed, regional, etc.).

 - Train Schedule: This table would include information on the scheduled departure and arrival times for each train, as well as the route it will take.

 - Trip: This table would include information on the each trip, such as the departure and arrival stations, date and time, number of available seats and classes.

 - Ticket: This table would include information on ticket sales, including the date and time of purchase, the route, the passenger's name, and the fare paid.

 


