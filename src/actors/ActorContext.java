package actors;

import java.util.*;

/**
 * Class ActorContext (Singleton)
 */
public class ActorContext{

	//the attribute instance contains the only instance of the class
	private static ActorContext instance;

	//registry of all the actors that have been spawned
	private static Map<String, Actor> actorRegistry;

	/**
	 * The constructor is private, so it cannot be called
	 * to ensure that only one instance of the ActorContext exists.
	 */
	private ActorContext(){
		actorRegistry = new HashMap<>();
	}

	/**
	 * Method to get the only instance of the class
	 * @return the ActorContext
	 */
	public static ActorContext getInstance(){
		if (instance == null){
			instance = new ActorContext();
		}
		return instance;
	}

	/**
	 * Method to spawn actors
	 * @param name name of the actor
	 * @param actor actor to be spawned
	 * @return the actor
	 */
	public Actor spawnActor(String name, Actor actor) {
		actorRegistry.put(name, actor);
		new Thread(actor::process).start();
		return actor;
	}

	/**
	 * Method to get the actor with a specific name
	 * @param name name of the actor
	 * @return the actor with that name
	 */
	public static Actor lookup(String name){
		return actorRegistry.get(name);
	}

	/**
	 * Getter for the list of names of the actors
	 * @return a Set with the names of the actors
	 */
	public static Set<String> getNames(){
		return actorRegistry.keySet();
	}
}
