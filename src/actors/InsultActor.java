package actors;

import messages.AddInsultMessage;
import messages.GetAllInsultsMessage;
import messages.GetInsultMessage;
import messages.Message;

import java.util.*;

public class InsultActor extends Actor {

    private List<String> insults = new ArrayList<>();

    /**
     * Constructor for the Actor class, initializes the messageQueue
     * @param name name of the actor
     */
    public InsultActor(String name) {
        super(name);
    }

    @Override
    public void onMessageReceived(Message message) {

        if (message instanceof AddInsultMessage){
            insults.add(message.getText());
        }

        Message answer = null;

        if (message instanceof GetInsultMessage){
            Random rand = new Random();
            answer = new Message(this, insults.get(rand.nextInt(insults.size())));
        }
        if (message instanceof GetAllInsultsMessage){
            answer = new Message(this, insults.toString());
        }

        if (answer != null){
            if (message.getFrom() instanceof ActorProxy){
                ((ActorProxy) message.getFrom()).sendResponse(answer);
            }
            else{
                message.getFrom().send(answer);
            }
        }
    }
}
