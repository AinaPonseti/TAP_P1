package decorators;

import actors.Actor;
import messages.Message;

public abstract class ActorDecorator extends Actor {
    protected Actor decoratedActor;

    /**
     * Constructor for the decorator
     *
     * @param actor actor to decorate
     */
    public ActorDecorator(Actor actor) {
        super(actor.getName());
        decoratedActor = actor;
    }

    @Override
    public void send(Message message) {
        decoratedActor.send(message);
    }

    @Override
    public Message dequeueMessage() throws InterruptedException {
        return decoratedActor.dequeueMessage();
    }

    @Override
    public void onMessageReceived(Message message) {
        decoratedActor.onMessageReceived(message);
    }
}