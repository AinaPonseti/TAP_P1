package decorators;


import actors.Actor;
import actors.ActorContext;
import actors.ActorProxy;
import messages.*;

public class FirewallDecorator extends ActorDecorator{

    public FirewallDecorator(Actor actor) {
        super(actor);
    }

    @Override
    public void send(Message message){
        if (message.getFrom() != null){
            Actor sender = ActorContext.lookup(message.getFrom().getName());

            //if the actor is a proxy or it is not indexed
            if (message.getFrom() instanceof ActorProxy || sender == null){
                return;
            }
            decoratedActor.send(message);
        }
    }
}
