package actors;

import messages.*;
import java.util.LinkedList;
import java.util.Queue;

public class ActorProxy {

	//attributes
	private Actor actor;
	private Queue<Message> messageQueue;

	/**
	 * Constructor for the ActorProxy
	 * @param actor actor
	 */
	public ActorProxy(Actor actor){
		this.actor=actor;
		messageQueue = new LinkedList<>();
	}


	/**
	 * Send method.  Adds a message to the actor queue.
	 * @param missatge message to the actor.
	 */
	public void send(Message missatge) {
        actor.send(missatge);
	}

	/**
	 * Receive method.  Gets the first message from the proxy's queue.
	 * @return the message received by the proxy
	 */
	public Message receive(){
		return messageQueue.poll();
	}

	/**
	 * Method to add messages (responses) to the proxy's queue
	 * @param message message to be sent to the proxy
	 */
	public void onMessageReceived(Message message){
		messageQueue.add(message);
	}
}
