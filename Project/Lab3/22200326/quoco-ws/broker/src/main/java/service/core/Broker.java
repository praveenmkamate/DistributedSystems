package service.core;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.Executors;

import static javax.jws.soap.SOAPBinding.Style.DOCUMENT;

@WebService
@SOAPBinding(style = DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class Broker {

    public static LinkedList<URL> wsdlList = new LinkedList<URL>();

    public static void main(String[] args) {
        try {
            Endpoint endpoint = Endpoint.create(new Broker());
            HttpServer server = HttpServer.create(new InetSocketAddress(9000), 5);
            server.setExecutor(Executors.newFixedThreadPool(5));
            HttpContext context = server.createContext("/broker");
            endpoint.publish(context);
            server.start();

            JmDNS jmDNS = JmDNS.create(InetAddress.getLocalHost());
            jmDNS.addServiceListener("_quoter._tcp.local.", new WSDLServiceListener());
            System.out.println("Broker Service started!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class WSDLServiceListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            String url = event.getInfo().getURLs()[0];
            if(url != null){
                try {
                    wsdlList.add(new URL(url));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


    @WebMethod
    public LinkedList<Quotation> getQuotations(ClientInfo info) {

        LinkedList<Quotation> quotationList = new LinkedList<Quotation>();

        try {
            for (URL url : wsdlList) {
                QName serviceName =
                        new QName("http://core.service/", "QuoterService");
                Service service = Service.create(url, serviceName);
                QName portName = new QName("http://core.service/", "QuoterPort");
                QuoterService quoterService =
                        service.getPort(portName, QuoterService.class);
                quotationList.add(quoterService.generateQuotation(info));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quotationList;

    }

}
