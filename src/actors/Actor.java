package actors;
import messages.Message;
import messages.QuitMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Actor {

    //queue that contains all the messages sent to the actor
    private BlockingQueue<Message> messageQueue;
    private String name;

    /**
     * Constructor for the Actor class, initializes the messageQueue
     */
    public Actor(String name) {
        messageQueue = new LinkedBlockingQueue<>();
        this.name = name;
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
            try {
                Message message = messageQueue.take();
                if (message instanceof QuitMessage) {
                    running = false;
                } else {
                    onMessageReceived(message);
                }
            }
            catch (InterruptedException e){
                running = false;
            }
        }
    }

    /**
     * Method onMessageReceived: handles the message
     * This method will be implemented by each subclass of actor
     * @param message message received
     */
    public abstract void onMessageReceived(Message message);

    /**
     * Getter for the actor name
     * @return string (name of the actor)
     */
    public String getName(){
        return name;
    }
}

