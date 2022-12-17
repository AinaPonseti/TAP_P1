package reflection;

import actors.ActorProxy;
import messages.AddInsultMessage;
import messages.GetAllInsultsMessage;
import messages.GetInsultMessage;
import messages.Message;

/**
 * InsultService Class
 * Implements methods to communicate with the actor
 */
public class InsultService implements InsultServiceInt{

    private ActorProxy insultActor;

    /**
     * Constructor for the InsultService
     * @param insultActor actor
     */
    public InsultService(ActorProxy insultActor) {
        this.insultActor = insultActor;
    }

    /**
     * Default constructor for the InsultService
     */
    public InsultService() {
        this.insultActor = null;
    }

    /**
     * Setter for the insultActor
     * @param insultActor actor
     */
    public void setInsultActor(ActorProxy insultActor){
        this.insultActor = insultActor;
    }

    public void addInsult(String insult){
        insultActor.send(new AddInsultMessage(null, insult));
    }

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
}
