package Validation;

import actors.ActorContext;
import actors.PingPongActor;
import messages.Message;
import messages.QuitMessage;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class PingPong {
    PingPongActor ping;
    int nRounds;
    Semaphore sem;

    public PingPong(){
        sem = new Semaphore(2);
        ping = new PingPongActor();
        ping.setSem(sem);
        ping.getActor().setSem(sem);
        nRounds = 15;
    }

    public void createPingPong(){
        ActorContext.spawnActor("Ping", ping);
    }

    public void sendMessagePingPong(int numMessages){
        System.out.println("Sending a message to the pingPong actors...");
        for(int i=0; i<numMessages; i++){
            ping.send(new Message(null, "PING"));
        }
        try {
            sem.acquire(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void quitPingPong(){
        ping.send(new QuitMessage());
        ping.getActor().send(new QuitMessage());
    }
}
