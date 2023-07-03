package service.core;

import service.message.ClientApplicationMessage;
import service.message.QuotationRequestMessage;

import javax.jms.*;
import java.util.ArrayList;

public class ClientProducer implements Runnable {

    public static MessageConsumer clientConsumer;
    public static MessageProducer producer;

    public static Session session;

    public ClientProducer(MessageConsumer clientConsumer, MessageProducer producer, Session session) {
        this.producer = producer;
        this.clientConsumer = clientConsumer;
        this.session = session;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Message message = clientConsumer.receive();
                // Check it is the right type of message
                if (message instanceof ObjectMessage) {
                    // It’s an Object Message
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof QuotationRequestMessage) {
                        // It’s a Quotation Request Message
                        QuotationRequestMessage request = (QuotationRequestMessage) content;
                        Broker.cache.put(Broker.SEED_ID,request.info);
                        Broker.responseMap.put(request.info,new ArrayList<Quotation>());
                        message.acknowledge();

                        Message msg = session.createObjectMessage(
                                new QuotationRequestMessage(Broker.SEED_ID++, request.info));
                        producer.send(msg);
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


            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
