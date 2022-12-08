package messages;

import actors.Actor;

public class Message {
    private Actor from;
    private String text;

    /**
     * Constructor for the Message class.
     * @param from actor that sends the message
     * @param text message
     */
    public Message(Actor from, String text) {
        this.from = from;
        this.text = text;
    }

    /**
     * Getter for the actor that sends the message
     * @return the actor that sent the message
     */
    public Actor getFrom() {
        return from;
    }


    /**
     * Getter for the message
     * @return the message (text) sent
     */
    public String getText() {
        return text;
    }
}

