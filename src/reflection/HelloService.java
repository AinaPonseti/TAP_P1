package reflection;

import actors.Actor;
import actors.ActorProxy;
import messages.Message;

public class HelloService implements Service{

    //Attributes
    private ActorProxy helloWorldActor;

    //Constructor
    public HelloService() {
        this.helloWorldActor = null;
    }

    public void setHelloWorldActor(ActorProxy helloWorldActor) {
        this.helloWorldActor = helloWorldActor;
    }

    @Override
    public void setActor(Actor actor) {
        if (actor instanceof ActorProxy){
            setHelloWorldActor((ActorProxy) actor);
        }
        else setHelloWorldActor(new ActorProxy(actor));
    }

    //Specific Methods

    public void print(String text){
        helloWorldActor.send(new Message(null, text));
    }
}
