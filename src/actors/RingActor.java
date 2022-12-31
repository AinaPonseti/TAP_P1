package actors;

import messages.Message;
import messages.QuitMessage;

import java.util.concurrent.Semaphore;

public class RingActor extends Actor {
    RingActor nextActor;

    private int nMessages = 0;
    private int totalMessages=10000;

    public RingActor() {
        super();
    }
    public RingActor(RingActor actor, int nMesaj){
        super();
        this.nextActor=actor;
        this.totalMessages=nMesaj;
    }

    public void setActor(RingActor actor){
        this.nextActor=actor;
    }
    @Override
    public void onMessageReceived(Message message) {
        if(totalMessages>nMessages){
            nextActor.send(message);
            //System.out.println(this + "message: " + message.getText() + " " + nMessages);
            nMessages++;
        }

    }

    public int getnMessages(){ return nMessages; }
    public void setnRounds(int rounds){ totalMessages=rounds; }
    public void addMessages(int nMessages){
        totalMessages=totalMessages+nMessages;
    }
    public RingActor getNextActor(){ return nextActor;}
}
