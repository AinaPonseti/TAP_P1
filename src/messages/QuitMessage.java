package messages;

import actors.Actor;

public class QuitMessage extends Message{

    /**
     * Constructor for the Message class.
     *
     * @param from actor that sends the message
     */
    public QuitMessage(Actor from) {
        super(from, null);
    }
}
