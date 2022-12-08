package actors;

import messages.Message;

public class HelloWorldActor extends Actor {

    @Override
    public void onMessageReceived(Message message) {
        System.out.println(message.getText());
    }
}
