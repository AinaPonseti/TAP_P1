package decorators;

import actors.Actor;
import actors.ActorContext;
import actors.ActorProxy;
import messages.AddClosureMessage;
import messages.Message;
import messages.QuitMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LambdaFirewallDecorator extends ActorDecorator {

    private List<Predicate<Message>> filterList;

    /**
     * Constructor for the LambdaFirewallDecorator
     *
     * @param actor actor to decorate
     */
    public LambdaFirewallDecorator(Actor actor) {
        super(actor);
        filterList = new ArrayList<>();
    }

    @Override
    public void send(Message message) {
        if (checkIfValid(message) || message instanceof QuitMessage) {
            if (message instanceof AddClosureMessage){
                filterList.add(((AddClosureMessage) message).getPredicate());
            }
            else{
                messageQueue.add(message);
            }
        }
    }

    protected boolean checkIfValid(Message message) {

        //if the sender is null
        if (message.getFrom() == null) {
            return false;
        }

        Actor sender = ActorContext.lookup(message.getFrom().getName());
        if (message.getFrom() instanceof ActorProxy || sender == null) {
            return false;
        }

        //check if the message passes the filters
        return filterList.stream().allMatch(filter -> filter.test(message));
    }

    @Override
    public void onMessageReceived(Message message) {
        decoratedActor.onMessageReceived(message);
    }
}
