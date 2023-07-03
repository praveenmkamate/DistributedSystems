package service.core;

import org.apache.activemq.*;
import service.message.QuotationRequestMessage;
import service.message.QuotationResponseMessage;

import javax.jms.*;
import javax.jms.Message;

public class Receiver {
    static AFQService service = new AFQService();

    public static void main(String args[]) {

        String host;
        if (args.length == 0){
            host = "localhost";
        } else {
            host = args[0];
        }

        try {
            //code to link to the broker
            ConnectionFactory factory =
                    new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
            Connection connection = factory.createConnection();
            connection.setClientID("auldfellas");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            //code to set up the channels
            Queue queue = session.createQueue("QUOTATIONS");
            Topic topic = session.createTopic("APPLICATIONS");
            MessageConsumer consumer = session.createConsumer(topic);
            MessageProducer producer = session.createProducer(queue);

            connection.start();

            while (true) {
                // Get the next message from the APPLICATION topic
                Message message = consumer.receive();
                // Check it is the right type of message
                if (message instanceof ObjectMessage) {
                    // It’s an Object Message
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof QuotationRequestMessage) {
                        // It’s a Quotation Request Message
                        QuotationRequestMessage request = (QuotationRequestMessage) content;
                        // Generate a quotation and send a quotation response message…
                        Quotation quotation = service.generateQuotation(request.info);
                        Message response = session.createObjectMessage(
                                new QuotationResponseMessage(request.id, quotation));
                        producer.send(response);
                    }
                } else {
                    System.out.println("Unknown message type: " +
                            message.getClass().getCanonicalName());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
