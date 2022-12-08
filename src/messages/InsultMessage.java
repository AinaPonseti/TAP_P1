package messages;

import actors.Actor;

public class InsultMessage extends Message{

    /**
     * Constructor for the Message class.
     *
     * @param from actor that sends the message
     * @param text message
     */
    public InsultMessage(Actor from, String text) {
        super(from, text);
    }
}
