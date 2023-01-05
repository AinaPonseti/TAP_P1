package Validation;

import actors.ActorContext;
import actors.PingPongActor;
import actors.RingActor;
import messages.Message;
import messages.QuitMessage;
import observer.MonitorService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class PingPong {
    PingPongActor ping;
    int nRounds,nMessages;
    MonitorService monitor = new MonitorService();

    public PingPong(int rounds){
        ping = new PingPongActor();
        nRounds = rounds;
    }
    public void createPingPong(){
        ActorContext.spawnActor("Ping", ping);
    }

    public void sendMessagePingPong(int numMessages){
        nMessages=numMessages;
        ping.addMessages(nMessages*nRounds);
        ping.getActor().addMessages(nMessages*nRounds);
        for(int i=0; i<numMessages; i++){
            ping.send(new Message(null, "PING"));
        }
    }

    public void quitPingPong(){
        ping.send(new QuitMessage());
        ping.getActor().send(new QuitMessage());
    }


    public boolean allFinalized() {
        PingPongActor auxiliary = ping;
        for(int i=0; i<2; i++) {
            if (auxiliary.getnMessages()<nMessages*nRounds) {
                return false;
            }
            auxiliary = auxiliary.getActor();
        }
        return true;
    }

    public int getMessagesFinalActor(){
        return ping.getActor().getnMessages();
    }
}
