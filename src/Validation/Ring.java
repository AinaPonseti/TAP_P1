package Validation;

import actors.ActorContext;
import actors.ActorProxy;
import actors.RingActor;
import messages.Message;
import messages.QuitMessage;
import observer.MonitorService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Ring {
    RingActor ringEnd;
    RingActor ringActor;
    ActorProxy ring;
    int nMessages,rounds,size;
    MonitorService monitor = new MonitorService();

    public Ring(int rounds, int nMessages){
        ringEnd=new RingActor();
        this.rounds=rounds;
        this.nMessages=nMessages;
        ringActor=new RingActor(ringEnd,nMessages*rounds);
        monitor.monitorActor(ringActor);
    }

    public void createRing(int sizeRing){
        size=sizeRing;
        ActorContext.spawnActor(("ring1"), ringActor);
        for (int i=0;i<=(sizeRing-3);i++){
            ringActor = new RingActor(ringActor,nMessages*rounds);
            monitor.monitorActor(ringActor);
            ActorContext.spawnActor(("ring"+i), ringActor);
        }
        ringEnd.setActor(ringActor);
        ringEnd.setnRounds(nMessages*rounds);
        monitor.monitorActor(ringEnd);
        ActorContext.spawnActor(("ringEnd"), ringEnd);
        ring = new ActorProxy(ActorContext.spawnActor("ring", ringActor));
    }

    public void sendMessageToRing(Message messageToSend){
        ring.send(messageToSend);
        // look if the last actor of the ring has finalized sending messages
    }

   public void deleteRing(){

       RingActor rin2 = ringEnd.getNextActor();
       ringEnd.send(new QuitMessage());
       while(rin2!=ringEnd){
           rin2.send(new QuitMessage());
           rin2=rin2.getNextActor();
       }
   }

   public boolean allFinalized(){
        RingActor auxiliary = ringActor;
        for(int i=0; i<size; i++) {
            if (auxiliary.getnMessages()<nMessages*rounds) {
                return false;
            }
            auxiliary = auxiliary.getNextActor();
        }
        return true;
   }

   public int getMessagesFinalActor(){
        return ringActor.getnMessages();
   }
}
