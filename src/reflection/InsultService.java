package reflection;

import actors.Actor;
import actors.ActorProxy;
import messages.AddInsultMessage;
import messages.GetAllInsultsMessage;
import messages.GetInsultMessage;
import messages.Message;

/**
 * InsultService Class
 * Implements methods to communicate with the actor
 */
public class InsultService implements Service{

    private ActorProxy insultActor;

    /**
     * Default constructor for the InsultService
     */
    public InsultService() {
        this.insultActor = null;
    }

    /**
     * Constructor for the InsultService
     * @param actor actor
     */
    public InsultService(ActorProxy actor) {
        this.insultActor = actor;
    }

    /**
     * Setter for the insultActor
     * @param insultActor actor
     */
    public void setInsultActor(ActorProxy insultActor){
        this.insultActor = insultActor;
    }

    /**
     * Method that adds an insult to the insultActor list
     * @param insult insult to add
     */
    public void addInsult(String insult){
        insultActor.send(new AddInsultMessage(null, insult));
    }

    /**
     * Method that gets all the insults from the actors list
     * @return String with all the insults
     */
    public String getAllInsults(){
        insultActor.send(new GetAllInsultsMessage(null));
        try {
            Message insults = insultActor.receive();
            return insults.getText();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to get a random insult form the actor
     * @return string with one insult
     */
    public String getInsult(){
        insultActor.send(new GetInsultMessage());
        try {
            Message insults = insultActor.receive();
            return insults.getText();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setActor(Actor actor) {
        if (actor instanceof ActorProxy){
            setInsultActor((ActorProxy) actor);
        }
        this.setInsultActor(new ActorProxy(actor));
    }
}
