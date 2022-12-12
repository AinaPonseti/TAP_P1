package messages;

import actors.Actor;

public class AddInsultMessage extends Message{

    /**
     * Constructor for the Message class.
     *
     * @param from actor that sends the message
     * @param text message
     */
    public AddInsultMessage(Actor from, String text) {
        super(from, text);
    }
}
