package actors;

import messages.Message;
import messages.QuitMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class PingPongActor extends Actor{

    boolean roundsFinished=true;
    PingPongActor actor;
    int nMessages=0,totalMessages=0;
    Message message;
    public PingPongActor(){
        super("Pong");
        actor=new PingPongActor(this);
        message=new Message(this,"PONG");
        ActorContext.spawnActor(actor);
    }

    public PingPongActor(PingPongActor pingPongActor){
        super("Ping");
        actor= pingPongActor;
        message=new Message(this,"PING");
    }

    @Override
    public void onMessageReceived(Message message) {
        if (totalMessages > nMessages) {
            actor.send(this.message);
            nMessages++;
        }
    }

   public void addMessages(int nMessages){
        totalMessages=totalMessages+nMessages;
   }
    public PingPongActor getActor(){ return actor; }
    public int getnMessages(){ return nMessages; }
    public void setnMessages(int nMessages){ this.nMessages=nMessages; }
}
