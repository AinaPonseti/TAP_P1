package Validation;

import actors.ActorContext;
import actors.ActorProxy;
import actors.RingActor;
import messages.Message;
import messages.QuitMessage;

import static java.lang.Thread.sleep;

public class Ring {
    RingActor ringIni;
    RingActor ringActor;
    ActorProxy ring;
    int nMessages;

    public Ring(){
        ringIni=new RingActor();
        ringActor=new RingActor(ringIni);
    }

    public void createRing(int sizeRing){
        ActorContext.spawnActor(("ring1"), ringActor);
        for (int i=0;i<=(sizeRing-3);i++){
            ringActor = new RingActor(ringActor);
            ActorContext.spawnActor(("ring"+i), ringActor);
        }
        ringIni.setActor(ringActor);
        ring = new ActorProxy(ActorContext.spawnActor("ring", ringIni));
    }

    public void sendMessageToRing(Message... messagesToSend){
        System.out.println("Sending a message to the ring...");
        for( var elem:messagesToSend){
            ring.send(elem);
        }
        nMessages = messagesToSend.length;
        int n = 0;
        // look if the last actor of the ring has finalizated sending messages
        while((ringActor.getnRounds()<=99)&&(n<nMessages)){
            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(ringActor.getnRounds()>99){
                n++;
                ringActor.setnRounds(0);
            }
        }
    }

   public void deleteRing(){
       RingActor rin2 = ringIni.getNextActor();
       ringIni.send(new QuitMessage());
       while(rin2!=ringIni){
           rin2.send(new QuitMessage());
           rin2=rin2.getNextActor();
       }
   }
}
