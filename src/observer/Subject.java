package observer;

import messages.Message;

import java.util.*;

public class Subject {
    private List<ActorListener> observers;
    public Subject() {
        observers=new ArrayList<>();
    }

    // gets the actor and the type of event and adds it to the list
    public void subscribe(ActorListener listener) {
        observers.add(listener);
    }

    public void unsubscribe(ActorListener listener) {
        observers.remove(listener);
    }

    public void notify(int status, Message message) {
        for (ActorListener listener : observers) {
            listener.update(status, message);
        }
    }

    public List getObservers(){
        return observers;
    }
}
