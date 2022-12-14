package decorators;


import actors.Actor;
import actors.ActorContext;
import actors.ActorProxy;
import messages.*;

public class FirewallDecorator extends ActorDecorator {

    public FirewallDecorator(Actor actor) {
        super(actor);
    }


    @Override
    public void send(Message message) {
        if (checkIfValid(message)) {
            decoratedActor.send(message);
        }
    }

    /**
     * Method that checks if a message has a valid sender
     *
     * @param message message to evaluate
     * @return boolean (true -> valid, false -> not valid)
     */
    protected boolean checkIfValid(Message message) {

        if (message.getFrom() != null) {
            Actor sender = ActorContext.lookup(message.getFrom().getName());

            //if the actor is a proxy or if it is not indexed
            if (message.getFrom() instanceof ActorProxy || sender == null) {
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public void onMessageReceived(Message message) {
        decoratedActor.onMessageReceived(message);
    }
}