package actors;

public abstract class RingActor extends Actor {

    //constants
    private static int numActorsInRing = 0;

    //attributes
    private Actor successor;
    private int id;


    /**
     * Constructor for the Actor class, initializes the messageQueue
     * @param name name of the actor
     */
    public RingActor(String name) {
        super(name);
        id = ++numActorsInRing;
    }
}
