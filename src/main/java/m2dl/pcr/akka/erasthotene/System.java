package m2dl.pcr.akka.erasthotene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class System {

    public static final Logger log = LoggerFactory.getLogger(System.class);

    public static void main(String[] args) throws InterruptedException {

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        final ActorRef actorRef = actorSystem.actorOf(Props.create(CribleActor.class, 2),
                "parent-actor");

        for (int i = 3; i < 100; i++) {
            actorRef.tell(i, null);
        }

        Thread.sleep(1000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();

    }
}
