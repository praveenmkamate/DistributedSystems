package service.core;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker {

    static int SEED_ID = 1001;
    static Map<Integer, ClientInfo> cache = new HashMap<Integer, ClientInfo>();

    public static Map<ClientInfo, List<Quotation>> responseMap = new HashMap<>();

    public static void main(String[] args) {

        Broker service = new Broker();

        String host;
        if (args.length == 0) {
            host = "localhost";
        } else {
            host = args[0];
        }

        ConnectionFactory factory =
                new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
        Connection connection = null;

        try {
            connection = factory.createConnection();
            connection.setClientID("broker");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("QUOTATIONS");
            Topic topic = session.createTopic("APPLICATIONS");
            MessageProducer producer = session.createProducer(topic);
            MessageConsumer consumer = session.createConsumer(queue);

            Queue requests = session.createQueue("REQUESTS");
            Queue responses = session.createQueue("RESPONSES");
            MessageConsumer clientConsumer = session.createConsumer(requests);
            MessageProducer clientProducer = session.createProducer(responses);

            connection.start();

            Thread clientProducerThread = new Thread(new ClientProducer(clientConsumer,producer,session));
            clientProducerThread.start();

            Thread clientConsumerThread = new Thread(new ClientConsumer(consumer,clientProducer,session));
            clientConsumerThread.start();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

}
