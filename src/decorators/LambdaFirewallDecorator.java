package decorators;

import actors.Actor;
import actors.ActorContext;
import actors.ActorProxy;
import messages.AddClosureMessage;
import messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Predicate;

public class LambdaFirewallDecorator extends FirewallDecorator {

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
        if (checkIfValid(message)) {
            if (message instanceof AddClosureMessage){
                addFilter(((AddClosureMessage) message).getPredicate());
            }
            else{
                decoratedActor.send(message);
            }
        }
    }

    private void addFilter(Predicate<Message> filter) {
        filterList.add(filter);
    }

    @Override
    protected boolean checkIfValid(Message message) {

        //if the sender is null
        if (message.getFrom() == null) {
            return false;
        }

        //if the sender is a proxy or if it is not indexed
        Actor sender = ActorContext.lookup(message.getFrom().getName());
        if (message.getFrom() instanceof ActorProxy || sender == null) {
            return false;
        }

        //check if the message passes the filters
        return filterList.stream().allMatch(filter -> filter.test(message));
    }



    @Override
    public void onMessageReceived(Message message) {
        if (message instanceof AddClosureMessage){
            addFilter(((AddClosureMessage) message).getPredicate());
        }
        else{
            decoratedActor.onMessageReceived(message);
        }
    }
}
