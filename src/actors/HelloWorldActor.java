package actors;

import messages.Message;

public class HelloWorldActor extends Actor {

    /**
     * Constructor for the Actor class, initializes the messageQueue
     *
     * @param name
     */
    public HelloWorldActor(String name) {
        super(name);
    }


    @Override
    public void onMessageReceived(Message message) {
        System.out.println(message.getText());
    }
}
