package service.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Client;
import service.core.ClientInfo;
import service.messages.ApplicationRequest;

public class Main {

    /**
     * This is the starting point for the application. Here, we must
     * get a reference to the Broker Service and then invoke the
     * getQuotations() method on that service.
     * <p>
     * Finally, you should print out all quotations returned
     * by the service.
     *
     * @param args
     */

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Client.class), "client");

        ActorSelection selection = system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");


        for (ClientInfo info : clients) {
            selection.tell(new ApplicationRequest(info), ref);
        }

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
