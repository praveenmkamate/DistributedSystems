package service.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import message.Init;
import service.dodgydrivers.DDQService;

public class Main {
    public static void main(String args[]) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Quoter.class), "dodgydrivers");
        ref.tell(new Init(new DDQService()), null);
        System.out.println("DodgyDrivers Initialized.");
        ActorSelection selection =
                system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("register", ref);
    }
}
