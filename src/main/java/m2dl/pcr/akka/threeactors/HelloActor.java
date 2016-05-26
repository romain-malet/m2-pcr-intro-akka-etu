package m2dl.pcr.akka.threeactors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HelloActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof String) {
            log.info("Hello " + msg + "!");
        } else {
            unhandled(msg);
        }

    }

}
