package m2dl.pcr.akka.threeactors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class ParentActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Procedure<Object> hello = new HelloBehavior();
    private Procedure<Object> goodbye = new GoodByeBehavior();

    @Override
    public void onReceive(Object msg) throws Exception {
        hello.apply(msg);
    }

    private class HelloBehavior implements Procedure<Object> {

        private ActorRef helloRef = getContext().actorOf(Props.create(HelloActor.class),
                "hello-actor");

        public void apply(Object msg) throws Exception {
            log.info("Hello behavior");
            helloRef.tell(msg, getSelf());
            getContext().become(goodbye, false);
        }

    }

    private class GoodByeBehavior implements Procedure<Object> {

        private ActorRef goodbyeRef = getContext().actorOf(Props.create(GoodByeActor.class),
                "goodbye-actor");

        public void apply(Object msg) throws Exception {
            log.info("Goodbye behavior");
            goodbyeRef.tell(msg, getSelf());
            getContext().become(hello, false);
        }

    }

}
