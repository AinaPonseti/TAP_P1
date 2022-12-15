import actors.*;
import messages.*;
import observer.*;

import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

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
			sleep(500);
		} catch (InterruptedException e) {
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
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("Done. \n");
		insult.send(new QuitMessage());


		// actor observer Pattern
		System.out.println(" ------------------- OBSERVER -------------------");
		Actor actor = new HelloWorldActor();
		Actor actor2 = new HelloWorldActor();
		MonitorService monitor = new MonitorService();
		//monitor.monitorActor(actor);
		//monitor.monitorActor(actor2);


		ActorContext.spawnActor("hello", actor);
		ActorContext.spawnActor("godbye", actor2);
		monitor.monitorAllActors();

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


	}

	/**
	 * Print the recived or sended messages from monitorized actors
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
	private static void printNumberofMessages(MonitorService monitor,Actor actor, Actor actor2) {
		Map<String, List<Actor>> map;
		map = monitor.getNumberofMessages(actor, actor2);
		System.out.println("----------- Events: -----------");
		for (var entry : map.entrySet()) {
			System.out.println("event : " + entry.getKey());
			System.out.println("Actors: ");
			for (var mes : entry.getValue()) {
				System.out.println("" + getActorName(mes));

			}
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
