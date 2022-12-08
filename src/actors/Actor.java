package actors;
import messages.Message;
import messages.QuitMessage;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Actor {

    //queue that contains all the messages sent to the actor
    private Queue<Message> messageQueue;

    /**
     * Constructor for the Actor class, initializes the messageQueue
     */
    public Actor() {
        messageQueue = new LinkedList<>();
    }

    /**
     * Method send: adds a message to the queue of the actor
     * @param message message to be added
     */
    public void send(Message message){
        messageQueue.add(message);
    }

    /**
     * Method process: processes all the methods from the queue
     */
    public synchronized void process() {
        boolean running = true;

        while (running) {
            Message message = messageQueue.poll();
            if (message != null) {
                if (message instanceof QuitMessage) {
                    running = false;
                } else {
                    onMessageReceived(message);
                }
            }
        }
    }

    /**
     * Method onMessageReceived: handles the message
     * This method will be implemented by each subclass of actor
     * @param message message received
     */
    public abstract void onMessageReceived(Message message);

}

