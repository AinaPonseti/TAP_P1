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
     *
     * @param name
     */
    public InsultActor(String name) {
        super(name);
    }

    @Override
    public void onMessageReceived(Message message) {
        if (message instanceof AddInsultMessage){
            insults.add(message.getText());
        }
        if (message instanceof GetInsultMessage){
            Random rand = new Random();
            Message answer = new Message(this, insults.get(rand.nextInt(insults.size())));
            ActorProxy dest = (ActorProxy) message.getFrom();
            dest.sendResponse(answer);
        }
        if (message instanceof GetAllInsultsMessage){
            Message newMessage = new Message(this, insults.toString());
            message.getFrom().send(newMessage);
        }
    }
}
