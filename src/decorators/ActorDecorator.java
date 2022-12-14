package decorators;

import actors.Actor;
import messages.Message;

public abstract class ActorDecorator extends Actor {
    protected Actor decoratedActor;

    public ActorDecorator(Actor actor){
        super(actor.getName());
        this.decoratedActor = actor;
    }

    @Override
    public void send(Message message){
        decoratedActor.send(message);
    }

    @Override
    public void process(){
        decoratedActor.process();
    }
    @Override
    public void onMessageReceived(Message message) {
        decoratedActor.onMessageReceived(message);
    }
}
