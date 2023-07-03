package service.core;

import org.apache.activemq.ActiveMQConnectionFactory;
import service.message.ClientApplicationMessage;
import service.message.QuotationRequestMessage;
import service.message.QuotationResponseMessage;

import javax.jms.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Main {

	static Map<Integer,ClientInfo> cache = new HashMap<>();
	static int SEED_ID = 0;

	public static void main(String[] args) {

		String host;
		if (args.length == 0) {
			host = "localhost";
		} else {
			host = args[0];
		}

		ConnectionFactory factory =
				new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");
		Connection connection = null;
		try {
			connection = factory.createConnection();
			connection.setClientID("client");
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

			Queue requests = session.createQueue("REQUESTS");
			Queue responses = session.createQueue("RESPONSES");
			MessageProducer producer = session.createProducer(requests);
			MessageConsumer consumer = session.createConsumer(responses);

			connection.start();

			for(ClientInfo client:clients){
				QuotationRequestMessage quotationRequest =
						new QuotationRequestMessage(SEED_ID++, client);
				Message request = session.createObjectMessage(quotationRequest);
				cache.put(quotationRequest.id,quotationRequest.info);
				producer.send(request);
			}

			while (true){
				Message message = consumer.receive();
				if (message instanceof ObjectMessage) {
					Object content = ((ObjectMessage) message).getObject();
					if (content instanceof ClientApplicationMessage) {
						ClientApplicationMessage response = (ClientApplicationMessage) content;
						displayProfile(response.info);
						for (Quotation quote: response.quotations) {
							displayQuotation(quote);
						}
						System.out.println("\n");
					}
					message.acknowledge();
				} else {
					System.out.println("Unknown message type: " +
							message.getClass().getCanonicalName());
				}
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| License Number: " + String.format("%1$-19s", info.licenseNumber) + 
				" | No Claims: " + String.format("%1$-24s", info.noClaims+" years") +
				" | Penalty Points: " + String.format("%1$-19s", info.points)+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	/**
	 * Test Data
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")		
	};
}
