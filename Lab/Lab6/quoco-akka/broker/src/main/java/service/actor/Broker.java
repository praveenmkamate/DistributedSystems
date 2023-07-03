package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import scala.concurrent.duration.Duration;
import service.core.ClientInfo;
import service.core.Quotation;
import service.messages.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Broker extends AbstractActor {

    static int SEED_ID = 0;
    List<ActorRef> actorRefs = new LinkedList<>();

    static Map<Integer, ClientInfo> cache = new HashMap<Integer, ClientInfo>();

    public static Map<Integer, List<Quotation>> responseMap = new HashMap<>();

    ActorRef clientReference;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        msg -> {
                            if (!msg.equals("register")) return;
                            actorRefs.add(getSender());
                        })

                .match(QuotationResponse.class,
                        msg -> {
                            List<Quotation> temp;
                            if (responseMap.containsKey(msg.getId())) {
                                temp = responseMap.get(msg.getId());
                            } else {
                                temp = new LinkedList<>();
                            }
                            temp.add(msg.getQuotation());
                            responseMap.put(msg.getId(), temp);
                        })

                .match(ApplicationRequest.class,
                        msg -> {
                            clientReference = getSender();
                            for (ActorRef ref : actorRefs) {
                                ref.tell(new QuotationRequest(SEED_ID, msg.getClientInfo()), getSelf());
                                cache.put(SEED_ID, msg.getClientInfo());
                            }
                            getContext().system().scheduler().scheduleOnce(
                                    Duration.create(2, TimeUnit.SECONDS),
                                    getSelf(),
                                    new RequestDeadline(SEED_ID++),
                                    getContext().dispatcher(), null);
                        })

                .match(RequestDeadline.class,
                        msg -> {
                            ClientInfo clientInfo = cache.get(msg.getId());
                            clientReference.tell(
                                    new ApplicationResponse(clientInfo, responseMap.get(msg.getId())), getSelf());
                        }).build();
    }
}
