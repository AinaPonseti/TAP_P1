import actors.ActorContext;
import actors.HelloWorldActor;
import messages.Message;
import actors.ActorProxy;
import messages.QuitMessage;

public class app {
	public static void main(String[] args) {
		ActorProxy helloActor = new ActorProxy(ActorContext.getInstance().spawnActor("hello", new HelloWorldActor()));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new QuitMessage(null));
	}
}
