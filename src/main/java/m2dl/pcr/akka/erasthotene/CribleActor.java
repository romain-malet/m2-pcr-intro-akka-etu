package m2dl.pcr.akka.erasthotene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class CribleActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef nextRef;
    private int numRef;

    private Procedure<Object> filter = new FilterBehavior();
    private Procedure<Object> creator = new CreatorBehavior();

    public CribleActor(int ref) {
        numRef = ref;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        creator.apply(msg);
    }

    private class CreatorBehavior implements Procedure<Object> {

        public void apply(Object msg) throws Exception {
            if (msg instanceof Integer && (Integer) msg % numRef != 0) {
                log.info("creator behavior with ref:" + msg);
                nextRef = getContext().actorOf(Props.create(CribleActor.class, msg),
                        "crible-actor");
                getContext().become(filter, false);
            }
        }

    }

    private class FilterBehavior implements Procedure<Object> {

        public void apply(Object msg) throws Exception {
            if (msg instanceof Integer && (Integer) msg % numRef != 0) {
                nextRef.tell(msg, getSelf());
            }
        }

    }

}
