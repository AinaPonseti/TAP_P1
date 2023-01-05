package Validation;

import actors.ActorContext;
import messages.Message;
import messages.QuitMessage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class Validation {
    @Test
    /**
     * Tests the actor system using the ringActor and observer patern looking the final actor of the ring and counting the messages it has
     */
    void ringActorTests() {
        int size = 100, nMessages = 100, nRounds = 2;
        ActorContext.getInstance();
        Ring ring = new Ring(nRounds, nMessages);
        ring.createRing(size);
        for (int i = 0; i < nMessages; i++) {
            ring.sendMessageToRing(new Message(null, "hello"));
        }
        //ring.deleteRing();
        while (!ring.allFinalized()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(nMessages * nRounds, ring.getMessagesFinalActor());
    }

    @Test
    void ringPingPongTests() {
        int nMessages = 100, nRounds=3;
        ActorContext.getInstance();
        PingPong pingPong = new PingPong(nRounds);
        pingPong.createPingPong();
        pingPong.sendMessagePingPong(nMessages);
        while (!pingPong.allFinalized()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //pingPong.quitPingPong();
            assertEquals(nMessages*nRounds, pingPong.getMessagesFinalActor());

        }
    }
}

