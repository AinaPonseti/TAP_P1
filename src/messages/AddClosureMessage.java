package messages;


import actors.Actor;

import java.util.function.Predicate;

public class AddClosureMessage extends Message{

    Predicate<Message> predicate;

    /**
     * Constructor for the AddClosureMessage
     * @param predicate closure to be added
     */
    public AddClosureMessage(Actor sender, Predicate<Message> predicate) {
        super(sender);
        this.predicate = predicate;
    }

    /**
     * Getter for the predicate
     * @return the predicate
     */
    public Predicate<Message> getPredicate() {
        return predicate;
    }
}
