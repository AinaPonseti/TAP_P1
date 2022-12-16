import actors.*;
import decorators.EncryptionDecorator;
import decorators.FirewallDecorator;
import decorators.LambdaFirewallDecorator;
import messages.*;
import java.util.function.Predicate;

public class app {
	public static void main(String[] args) {

		ActorContext actorContext = ActorContext.getInstance();

		//actor system demonstration
		System.out.println(" ------------------- HEllO WORLD -------------------");
		ActorProxy helloActor = (ActorProxy) actorContext.spawnActor(new ActorProxy(new HelloWorldActor("hello")));
		System.out.println("Sending 4 concurrent Hello World messages...");
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		System.out.println("Done.\n");

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
		ActorProxy insult = (ActorProxy) actorContext.spawnActor(new ActorProxy(new InsultActor("insultActor")));

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


		//decorators demonstration
		System.out.println(" ------------------- DECORATORS -------------------");
		System.out.println("Creating a Firewall decorator for the helloWorldActor...");
		FirewallDecorator firewallDecorator = (FirewallDecorator) actorContext.spawnActor(new FirewallDecorator(new HelloWorldActor("firewallHello")));
		InsultActor insultActor = (InsultActor) actorContext.spawnActor(new InsultActor("insultActor"));
		System.out.println("Done.\n");

		System.out.println("Sending message from different senders...");
		firewallDecorator.send(new Message(insultActor, "Hello from the insultActor!"));
		firewallDecorator.send(new Message(null, "Hello from null!"));
		firewallDecorator.send(new Message(insult, "Hello from the proxy for the insultActor!"));

		System.out.println("Waiting for the messages to arrive...");
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.\n");

		System.out.println("Creating a LambdaFirewallDecorator for the insultActor...");
		LambdaFirewallDecorator lambdaFirewallDecorator = (LambdaFirewallDecorator) actorContext.spawnActor(new LambdaFirewallDecorator(insult));
		System.out.println("Done.\n");

		Predicate<Message> lambda = message -> GetAllInsultsMessage.class.isInstance(message);
		lambdaFirewallDecorator.send(new AddClosureMessage(firewallDecorator, lambda));
		lambdaFirewallDecorator.send(new AddInsultMessage(firewallDecorator,"new insult"));
		lambdaFirewallDecorator.send(new GetAllInsultsMessage(firewallDecorator));

		System.out.println("Waiting for the messages to arrive...");
		try {
			Thread.sleep(500);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.\n");

		insult.send(new QuitMessage());
		helloActor.send(new QuitMessage());

		System.out.println("Creating an EncryptionDecorator for a helloWorldActor...");
		EncryptionDecorator encryptionDecorator = (EncryptionDecorator) ActorContext.spawnActor(new EncryptionDecorator(new HelloWorldActor("encryptionDecorator")));
		System.out.println("Sending HelloWorld message...");
		encryptionDecorator.send(new Message(helloActor, "Hello World from the EncryptionDecorator!"));

		System.out.println("Waiting for the message to arrive...");
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.\n");

		encryptionDecorator.send(new QuitMessage());
		helloActor.send(new QuitMessage());
		insultActor.send(new QuitMessage());
		firewallDecorator.send(new QuitMessage());
		lambdaFirewallDecorator.send(new QuitMessage());
		insult.send(new QuitMessage());
	}
}
