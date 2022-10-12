package service.core;

import service.message.ClientApplicationMessage;
import service.message.QuotationResponseMessage;

import javax.jms.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClientConsumer implements Runnable{



    public static MessageConsumer consumer;
    public static MessageProducer clientProducer;

    public static Session session;
    public ClientConsumer(MessageConsumer consumer, MessageProducer clientProducer, Session session) {
        this.consumer = consumer;
        this.clientProducer = clientProducer;
        this.session = session;
    }

    public static LinkedList<Quotation> quotationsList = new LinkedList<Quotation>();


    @Override
    public void run() {
        while (true) {
            Message message = null;
            try {
                message = consumer.receive();

                if (message instanceof ObjectMessage) {
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof QuotationResponseMessage) {
                        QuotationResponseMessage response = (QuotationResponseMessage) content;
                        ClientInfo info = Broker.cache.get(response.id);
                        List<Quotation> quotes = Broker.responseMap.get(info);
                        quotes.add(response.quotation);
                        if(quotes.size() == 3){
                            ClientApplicationMessage cam = new ClientApplicationMessage(info, quotes);
                            Message msg = session.createObjectMessage(
                                    cam);
                            clientProducer.send(msg);
                        }
                    }

                    message.acknowledge();


                } else {
                    System.out.println("Unknown message type: " +
                            message.getClass().getCanonicalName());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                clientProducer.send(message);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException e){
                System.out.println("Null Point .");
            }
        }
    }
}
