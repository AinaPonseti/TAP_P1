package observer;

import messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorService {

    Map<Integer, List<ActorObserver>> messages = new HashMap<>();

    Map<ActorObserver, List<ActorListener>> listeners = new HashMap<>();
    /**
     * Starts monitorazing the actor given by parameter
     * @param actor the actor to monitorize
     */
    public void monitorActor(ActorObserver actor) {
        actor.monitor.subscribe(new MessagesListener());
        actor.monitor.subscribe(new TrafficListener());
        actor.monitor.subscribe(new EventsListener());
        listeners.put(actor, actor.monitor.getObservers());
    }

    /**
     * Monitors all Actors aviables
     */
    public void monitorAllActors(ActorObserver... actor){
        //TODO
    }

    /**
     * obtain a Map where the key is the traffic (LOW, MEDIUM, HIG)
     * and the value is the list of actor names
     * @return Map with the traffic
     */
    public Map getTraffic(){
        List<ActorObserver> lowList= new ArrayList<>();
        List<ActorObserver> midList= new ArrayList<>();
        List<ActorObserver> highList= new ArrayList<>();
        int trafic=0;
        for( var entry : listeners.entrySet()){
            trafic=(Integer)entry.getValue().get(1).get().get(0);
            if(trafic < 5){
                lowList.add(entry.getKey());
            }
            else if(trafic >= 5 && trafic < 15 ){
                midList.add(entry.getKey());
            }
            else if(trafic >= 15){
                highList.add(entry.getKey());
            }
        }
        Map<String, List<ActorObserver>> traffic = new HashMap<>();
        traffic.put("LOW",lowList);
        traffic.put("MID",midList);
        traffic.put("HIGH",highList);
        return traffic;
    }

    /**
     * log all messages from one or more Actors, and log all events
     * @param actor
     * @return a Map where the key is the events and the value is a list of actors
     */
    public Map getNumberofMessages(ActorObserver... actor){
        //TODO
        return null;
    }

    /**
     * gets the messages sended by all actors
     * @return a Map where the key is the Actor, and the value is the messages sent by that actor
     */
    public Map getSentMessages(){
        Map<ActorObserver, List<Message>> messages = new HashMap<>();
        for( var entry : listeners.entrySet()){
            messages.put(entry.getKey(), (List<Message>) entry.getValue().get(0).get().get(0));
        }
        return messages;
    }

    /**
     * gets the messages recived by all actors
     * @return g a Map where the key is the Actor, and the value is the list of Messages sent by that Actor
     */
    public Map getRecivedMessages(){
        Map<ActorObserver, List<Message>> messages = new HashMap<>();
        for( var entry : listeners.entrySet()){
            messages.put(entry.getKey(), (List<Message>) entry.getValue().get(0).get().get(1));
        }
        return messages;
    }

    /**
     *
     * @return a Map where the key is the enum (CREATED, STOPPED, ERROR),
     * and the value contains a list of strings enumerating the events.
     */
    public Map getEvents(){
        Map<String, List<String>> events = new HashMap<>();
        for( var entry : listeners.entrySet()){
            Map aux = entry.getValue().get(2).get();
            events.putAll(aux);
        }
        return events;
    }
}
