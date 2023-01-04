package actors;

import messages.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorProxy extends Actor{

	//attributes
	private Actor actor;
	private BlockingQueue<Message> responseQueue;

	/**
	 * Constructor for the ActorProxy
	 * @param actor actor
	 */
	public ActorProxy(Actor actor){
		super("proxy"+actor.getName());
		this.actor = actor;
		responseQueue = new LinkedBlockingQueue<>();
	}


	/**
	 * Send method.  Adds a message to the actor queue.
	 * @param message message to the actor.
	 */
	public void send(Message message) {
		if (message.getFrom() == null){
			message.setFrom(this);
		}
		actor.send(message);
	}

	/**
	 * Receive method.  Gets the first message from the proxy's queue.
	 * @return the message received by the proxy
	 */
	public void sendResponse(Message message){
		this.responseQueue.add(message);
	}

	/**
	 * Method to add messages (responses) to the proxy's queue
	 * @param message message to be sent to the proxy
	 */
	public void onMessageReceived(Message message){
		actor.onMessageReceived(message);
	}

	/**
	 * Method to get the messages sent to the proxy
	 * @return message
	 */
	public Message receive() throws InterruptedException {
		return responseQueue.take();
	}

	/**
	 * Getter for the actor
	 * @return actor
	 */
	public Actor getActor() {
		return actor;
	}
}
