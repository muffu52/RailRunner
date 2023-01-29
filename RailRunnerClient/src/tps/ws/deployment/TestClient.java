package tps.ws.deployment;

import java.rmi.RemoteException;
import tps.ws.deployment.BookTicketStub.Reserve;
import tps.ws.deployment.SearchTrainStub.Search;

public class TestClient {
	
	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		
		// Book my trip!
		// TODO Auto-generated method stub
//		BookTicketStub hwp = new BookTicketStub();
//		Reserve r = new Reserve();
//		r.setDeparture("Chelles Gournay");
//		r.setArrival("Haussmann St Lazare");
//		r.setDate("");
//		r.setD_time("");
//		r.setA_time("");
//		r.setName("Hamza");
//		r.setTicket_class("first"); // standard, first, business
//		System.out.print(hwp.reserve(r).get_return());
		
		// Search 
		SearchTrainStub sts = new SearchTrainStub();
		Search s = new Search();
		s.setType("schedule"); // schedule, trip, ticket, default (empty) -> trainDetails
		s.setDeparture("Chelles Gournay");
		s.setArrival("");
		s.setDate("");
		s.setD_time("");
		s.setA_time("");
		s.setCompany("");
		s.setName("");
		System.out.print(sts.search(s).get_return());
	}

}
