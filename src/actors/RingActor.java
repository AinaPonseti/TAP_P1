package actors;

import messages.Message;
import messages.QuitMessage;
import java.util.concurrent.Semaphore;

public class RingActor extends Actor {
    RingActor nextActor;

    private int nMessages = 0;
    private int totalMessages=10000000;

    public RingActor() {
        super("Ring");
    }
    public RingActor(int i) {
        super("Ring"+i);
    }
    public RingActor(RingActor actor, int nMesaj){
        super("Ring");
        this.nextActor=actor;
        this.totalMessages=nMesaj;
    }

    public void setActor(RingActor actor){
        this.nextActor=actor;
    }
    @Override
    public void onMessageReceived(Message message) {
        if(totalMessages<10000000){
            if(totalMessages>nMessages){
                nMessages++;
                nextActor.send(message);
            }
        }else nextActor.send(message);


    }

    public int getnMessages(){ return nMessages; }
    public void setnRounds(int rounds){ totalMessages=rounds; }
    public void addMessages(int nMessages){
        totalMessages=totalMessages+nMessages;
    }
    public RingActor getNextActor(){ return nextActor;}
}
