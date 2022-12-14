import actors.ActorContext;
import actors.HelloWorldActor;
import actors.InsultActor;
import decorators.ActorDecorator;
import decorators.EncryptionDecorator;
import decorators.FirewallDecorator;
import decorators.LambdaFirewallDecorator;
import messages.*;
import actors.ActorProxy;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.function.Function;
import java.util.function.Predicate;

public class app {
	public static void main(String[] args) {

		ActorContext.getInstance();

		//actor system demonstration
		System.out.println(" ------------------- HEllO WORLD -------------------");
		ActorProxy helloActor = new ActorProxy(ActorContext.spawnActor(new HelloWorldActor("hello")));
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
		ActorProxy insult = new ActorProxy(ActorContext.spawnActor(new InsultActor("insultActor")));

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
		FirewallDecorator firewallDecorator = new FirewallDecorator(ActorContext.lookup("hello"));
		System.out.println("Done.\n");

		System.out.println("Sending message from different senders...");
		firewallDecorator.send(new Message(ActorContext.lookup("insultActor"), "Hello from the insultActor!"));
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
		LambdaFirewallDecorator lambdaFirewallDecorator = new LambdaFirewallDecorator(ActorContext.lookup("insultActor"));
		System.out.println("Done.\n");

		Predicate<Message> lambda = message -> GetAllInsultsMessage.class.isInstance(message);
		lambdaFirewallDecorator.send(new AddClosureMessage(ActorContext.lookup("hello"), lambda));
		lambdaFirewallDecorator.send(new AddInsultMessage(ActorContext.lookup("hello"),"new insult"));
		lambdaFirewallDecorator.send(new GetAllInsultsMessage(ActorContext.lookup("hello")));

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
		ActorProxy helloActor1 = new ActorProxy(ActorContext.spawnActor(new EncryptionDecorator(new HelloWorldActor("helloActor1"))));
		ActorProxy helloActor2 = new ActorProxy(ActorContext.spawnActor(new HelloWorldActor("helloActor2")));
		System.out.println("Sending HelloWorld message...");
		helloActor1.send(new Message(helloActor2, "Hello World"));

		System.out.println("Waiting for the messages to arrive...");
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.\n");

		helloActor1.send(new QuitMessage());
		helloActor2.send(new QuitMessage());
	}
}
