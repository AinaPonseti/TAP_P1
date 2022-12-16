package actors;

import messages.Message;
import messages.QuitMessage;

import java.util.concurrent.Semaphore;

public class PingPongActor extends Actor{

    PingPongActor actor;
    int nMessages=0;
    Semaphore sem;
    Message ping;
    Message pong;
    public PingPongActor(){
        super();
        actor=new PingPongActor(this, sem);
        pong=new Message(this,"PONG");
        ActorContext.spawnActor("Pong",actor);
    }

    public PingPongActor(PingPongActor pingPongActor, Semaphore sem){
        super();
        actor= pingPongActor;
        ping=new Message(this,"PING");
    }

    @Override
    public void onMessageReceived(Message message) {
        if(nMessages==0){
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(nMessages<100){
            if(message.getText().equals("PING")){
                actor.send(pong);
            }else {actor.send(ping);}
            nMessages++;
        }else sem.release();
    }

    public void setSem(Semaphore sem){this.sem=sem;}
    public PingPongActor getActor(){ return actor; }
    public int getnMessages(){ return nMessages; }

    public void setnMessages(int nMessages){ this.nMessages=nMessages; }
}
