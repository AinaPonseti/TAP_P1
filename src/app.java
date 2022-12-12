import actors.ActorContext;
import actors.HelloWorldActor;
import actors.InsultActor;
import messages.AddInsultMessage;
import messages.GetInsultMessage;
import messages.Message;
import actors.ActorProxy;
import messages.QuitMessage;

public class app {
	public static void main(String[] args) {

		ActorContext.getInstance();

		//actor system demonstration
		System.out.println(" ------------------- HEllO WORLD -------------------");
		ActorProxy helloActor = new ActorProxy(ActorContext.spawnActor("hello", new HelloWorldActor()));
		System.out.println("Sending 4 concurrent Hello World messages...");
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		System.out.println("Done.\n");
		helloActor.send(new QuitMessage());

		System.out.println("Waiting for the messages to arrive...");
		try {
			Thread.sleep(500);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.\n");

		//actor proxy demonstration
		System.out.println(" ------------------- INSULTS -------------------");
		ActorProxy insult = new ActorProxy(ActorContext.spawnActor("insultActor", new InsultActor()));

		System.out.println("Adding insults...");
		insult.send(new AddInsultMessage(null, "stupid"));
		System.out.println("'Stupid' added");
		insult.send(new AddInsultMessage(null, "idiot"));
		System.out.println("'Idiot' added");
		insult.send(new AddInsultMessage(null, "moron"));
		System.out.println("'Moron' added");
		insult.send(new AddInsultMessage(null, "asshole"));
		System.out.println("'Asshole' added");
		insult.send(new AddInsultMessage(null, "jerk"));
		System.out.println("'Jerk' added");
		System.out.println("Done.\n");

		System.out.println("Getting 3 random insults...");
		insult.send(new GetInsultMessage());
		insult.send(new GetInsultMessage());
		insult.send(new GetInsultMessage());

		try {
			Message result = insult.receive();
			System.out.println(result.getText());
			result = insult.receive();
			System.out.println(result.getText());
			result = insult.receive();
			System.out.println(result.getText());
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.");
		insult.send(new QuitMessage());
	}
}
