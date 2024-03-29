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
     * Constructor for the Message class
     * @param proxy actor that sends the message
     */
    public Message(Actor proxy){
        this.from = proxy;
        this.text = null;
    }

    /**
     * Constructor for the Message class
     * @param text message text
     */
    public Message(String text){
        this.from = null;
        this.text = text;
    }


    /**
     * Default Constructor for the Message class
     */
    public Message(){
        this.from = null;
        this.text = null;
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

    /**
     * Setter for the sender
     * @param from actor that sends the message
     */
    public void setFrom(Actor from) {
        this.from = from;
    }

    public void setText(String text) {
        this.text = text;
    }
}

