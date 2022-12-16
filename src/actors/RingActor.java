package actors;

import messages.Message;
import messages.QuitMessage;

public class RingActor extends Actor {
    RingActor nextActor;

    private int nRounds = 0;

    public RingActor() {
        super();
    }
    public RingActor(RingActor actor){
        super();
        this.nextActor=actor;
    }

    public void setActor(RingActor actor){
        this.nextActor=actor;
    }
    @Override
    public void onMessageReceived(Message message) {
            nextActor.send(message);
            //System.out.println(this + "message: " + message.getText() + " " + nRounds);
            nRounds++;
    }

    public int getnRounds(){ return nRounds; }
    public void setnRounds(int rounds){ nRounds=rounds; }
    public RingActor getNextActor(){ return nextActor;}
}
