import Validation.PingPong;
import Validation.Ring;
import actors.*;

import decorators.EncryptionDecorator;
import decorators.FirewallDecorator;
import decorators.LambdaFirewallDecorator;
import messages.*;
import java.util.function.Predicate;

import observer.*;
import reflection.DynamicProxy;
import reflection.HelloService;
import reflection.InsultService;
import reflection.Service;

import java.util.List;
import java.util.Map;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;


public class app {
	public static void main(String[] args) {

		ActorContext actorContext = ActorContext.getInstance();

		System.out.println(Message.class.getName());

		//actor system demonstration
		System.out.println(" ------------------- HEllO WORLD -------------------");
		ActorProxy helloActor = actorContext.spawnActor(new HelloWorldActor("hello"));
		System.out.println("Sending 4 concurrent Hello World messages...");
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		helloActor.send(new Message(null, "Hello from the HelloWorldActor!"));
		System.out.println("Done.\n");

		System.out.println("Waiting for the messages to arrive...");
		try {
			sleep(500);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("Done.\n");

		//actor proxy demonstration
		System.out.println(" ------------------- INSULTS -------------------");
		ActorProxy insult = actorContext.spawnActor(new InsultActor("insultActor"));

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
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		System.out.println("Done.");


		//decorators demonstration
		System.out.println(" ------------------- DECORATORS -------------------");
		System.out.println("Creating a Firewall decorator for the helloWorldActor...");
		ActorProxy firewallDecorator = actorContext.spawnActor(new FirewallDecorator(new HelloWorldActor("firewallHello")));
		System.out.println("Done.\n");

		System.out.println("Sending message from different senders...");
		firewallDecorator.send(new Message(null, "Hello from null!"));
		firewallDecorator.send(new Message(insult, "Hello from the insultActorProxy!"));
		firewallDecorator.send(new Message(insult.getActor(), "Hello from the insultActor!"));

		System.out.println("Waiting for the messages to arrive...");
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.\n");

		System.out.println("Creating a LambdaFirewallDecorator for the insultActor...");
		ActorProxy lambdaFirewallDecorator = actorContext.spawnActor(new LambdaFirewallDecorator(insult));
		System.out.println("Done.\n");

		Predicate<Message> lambda = message -> GetAllInsultsMessage.class.isInstance(message);
		lambdaFirewallDecorator.send(new AddClosureMessage(firewallDecorator.getActor(), lambda));
		lambdaFirewallDecorator.send(new AddInsultMessage(firewallDecorator.getActor(),"new insult"));
		lambdaFirewallDecorator.send(new GetAllInsultsMessage(firewallDecorator.getActor()));

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
		ActorProxy encryptionDecorator = ActorContext.spawnActor(new EncryptionDecorator(new FirewallDecorator(new HelloWorldActor("encryptionDecorator"))));
		System.out.println("Sending HelloWorld message...");
		encryptionDecorator.send(new Message(firewallDecorator.getActor(), "Hello World from the EncryptionDecorator!"));

		System.out.println("Waiting for the message to arrive...");
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
		System.out.println("Done.\n");

		encryptionDecorator.send(new QuitMessage());
		firewallDecorator.send(new QuitMessage());
		lambdaFirewallDecorator.send(new QuitMessage());


		//dynamicProxy demonstration
		System.out.println(" ------------------- DYNAMIC PROXY -------------------");
		System.out.println("Initializing the insultService...");
		insult = ActorContext.spawnActor(new InsultActor("insultActor"));
		InsultService insulter = (InsultService) DynamicProxy.intercept(new InsultService(), insult);
		System.out.println("Adding 'stupid'...");
		insulter.addInsult("stupid");
		System.out.println("Adding 'idiot'...");
		insulter.addInsult("idiot");
		System.out.println("Adding 'jerk'...");
		insulter.addInsult("jerk");
		System.out.println("Done.");

		System.out.println("Getting two random insults...");
		System.out.println(insulter.getInsult());
		System.out.println(insulter.getInsult());
		System.out.println("Done.\n");

		System.out.println("Initializing the helloWorldService...");
		helloActor = ActorContext.spawnActor(new HelloWorldActor("helloActor"));
		HelloService helloService = (HelloService) DynamicProxy.intercept(new HelloService(), helloActor);
		helloService.print("Hello from the helloService!");
		helloService.print("Hello from the helloService!");
		helloService.print("Hello from the helloService!");
		System.out.println("Waiting for the messages to arrive...");
		try {
			sleep(500);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("Done.\n");


		//reflection demonstration
		//System.out.println(" ------------------- REFLECTION -------------------");
		//ActorProxy reflectiveActor = ActorContext.spawnActor(new ReflectiveActor(new InsultService(insult), "reflectiveActor"));
		//reflectiveActor.send(new AddInsultMessage(null, "idiot"));
		//reflectiveActor.send(new AddInsultMessage(null, "stupid"));
		//reflectiveActor.send(new AddInsultMessage(null, "jerk"));
		//reflectiveActor.send(new GetInsultMessage());

		insult.send(new QuitMessage());
		helloActor.send(new QuitMessage());

		// actor observer pattern
		System.out.println(" ------------------- OBSERVER -------------------");
		ActorProxy actor = ActorContext.spawnActor(new HelloWorldActor("helloActor"));
		ActorProxy actor2 = ActorContext.spawnActor(new HelloWorldActor("goodbyeActor"));

		MonitorService monitor = new MonitorService();
		monitor.monitorActor(actor);
		monitor.monitorActor(actor2);

		System.out.println("Adding messages...");
		for (int i = 0; i < 10; i++) {
			actor.send(new Message(null, "Hello from the HelloWorldActor!"));
			actor2.send(new Message(null, "godbye from the HelloWorldActor!"));
		}
		System.out.println("Done.\n");
		actor.send(new QuitMessage());
		actor2.send(new QuitMessage());

		try {
			sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		printMessages(monitor, 0);
		printMessages(monitor, 1);
		printEvents(monitor);
		printTraffic(monitor);
		printNumberofMessages(monitor,actor,actor2);

		System.out.println(" ------------------- COST CALCULATION -------------------");
		int rounds = 1, nMessages = 100, sizeRing = 100;
		long ini=System.currentTimeMillis();
		Ring ring = new Ring(rounds,nMessages);
		ring.createRing(sizeRing);
		for(int i=0; i<nMessages; i++){
			ring.sendMessageToRing(new Message(null, "hello"));
		}
		while (!ring.allFinalized()){
			try {
				sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("ms to send "+nMessages+" messages "+rounds+" times to "+sizeRing+" actors in a ring "+(System.currentTimeMillis()-ini) + " ms");
		ring.deleteRing();
		ini=System.currentTimeMillis();
		PingPong pingPong= new PingPong(rounds);
		pingPong.createPingPong();
		pingPong.sendMessagePingPong(nMessages);
		while (!pingPong.allFinalized()){
			try {
				sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("ms to send "+nMessages+" messages "+rounds+" times between actors: "+(System.currentTimeMillis()-ini) + " ms");
		pingPong.quitPingPong();
		exit(1);
	}

	/**
	 * Print the recived or sent messages from monitorized actors
	 *
	 * @param monitor monitorizer
	 * @param version 0 -> sended messages, 1 -> recived messages, 2 -> events preformed
	 */
	private static void printMessages(MonitorService monitor, int version) {
		Map<Actor, List<Message>> map;
		if (version == 0) {
			map = monitor.getSentMessages();
			System.out.println("----------- Sent Messages: -----------");
		} else{
			map = monitor.getRecivedMessages();
			System.out.println("----------- Recived Messages: -----------");
		}
		for (var entry : map.entrySet()) {
			System.out.println("Actor: " + getActorName(entry.getKey()));
			System.out.println("Messages: ");
			for (var mes : entry.getValue()) {
				System.out.println("" + mes.getText());

			}
		}
	}
	private static String getActorName(actors.Actor actor){
		Map<String, Actor> map = ActorContext.getRegistry();
		for( var entry : map.entrySet()){
			if(entry.getValue().equals(actor)){
				return entry.getKey();
			}
		}
		return null;
	}

	private static void printEvents(MonitorService monitor) {
		Map<String, List<String>> map;
		map = monitor.getEvents();
		System.out.println("----------- Events: -----------");
		for (var entry : map.entrySet()) {
			System.out.println("Key: " + entry.getKey());
			System.out.println("Messages: ");
			for (var mes : entry.getValue()) {
				System.out.println("" + mes);

			}
		}
	}
	private static void printNumberofMessages(MonitorService monitor,ActorProxy actor, ActorProxy actor2) {
		Map<String, List<Actor>> map;
		map = monitor.getNumberofMessages(actor.getActor(), actor2.getActor());
		System.out.println("----------- Events: -----------");
		for (var entry : map.entrySet()) {
			System.out.println("event : " + entry.getKey());
			System.out.print("Actors:");
			for (var mes : entry.getValue()) {
				System.out.print(" " + getActorName(mes));

			}
			System.out.println("");
		}
	}


	private static void printTraffic(MonitorService monitor) {
		Map<String, List<Actor>> map;
		map = monitor.getTraffic();
		System.out.println("----------- Events: -----------");
		for (var entry : map.entrySet()) {
			System.out.print("--- " + entry.getKey()+ " Traffic ---\n");
			System.out.print("Actors:");
			for (var mes : entry.getValue()) {
				System.out.print(" " + getActorName(mes));
			}
			System.out.print("\n");
		}
	}

}
