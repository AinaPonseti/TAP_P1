package messages;

import actors.Actor;

public class GetAllInsultsMessage extends Message{
    /**
     * Constructor for the Message class.
     *
     * @param from actor that sends the message
     */
    public GetAllInsultsMessage(Actor from) {
        super(from, null);
    }
}
