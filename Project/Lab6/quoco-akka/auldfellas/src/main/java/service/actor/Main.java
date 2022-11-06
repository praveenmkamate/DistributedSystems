package service.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import message.Init;
import service.auldfellas.AFQService;

public class Main {
    public static void main(String args[]){
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Quoter.class), "auldfellas");
        ref.tell(new Init(new AFQService()), null);
        System.out.println("AuldFellas Initialized.");

        ActorSelection selection =
                system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("register", ref);
    }
}
