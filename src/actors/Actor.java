package actors;
import messages.Message;
import messages.QuitMessage;
import observer.Subject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Actor {

    //queue that contains all the messages sent to the actor
    private BlockingQueue<Message> messageQueue;

    public Subject monitor;
    /**
     * Constructor for the Actor class, initializes the messageQueue
     */
    public Actor() {
        messageQueue = new LinkedBlockingQueue<>();
        this.monitor = new Subject();
        monitor.notify(1, null);
    }

    /**
     * Method send: adds a message to the queue of the actor
     * @param message message to be added
     */
    public void send(Message message){
        monitor.notify(3, message);
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
                    monitor.notify(0, null);
                } else {
                    monitor.notify(2, message);
                    onMessageReceived(message);
                }
            }
            catch (InterruptedException e){
                running = false;
                monitor.notify(-1, null);
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

