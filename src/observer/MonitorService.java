package observer;

import actors.Actor;
import actors.ActorContext;
import messages.Message;

import java.util.*;

public class MonitorService {

    Map<Integer, List<actors.Actor>> messages = new HashMap<>();

    Map<Actor, List<ActorListener>> listeners = new HashMap<>();
    /**
     * Starts monitorazing the actor given by parameter
     * @param actor the actor to monitorize
     */
    public void monitorActor(Actor actor) {
        actor.monitor.subscribe(new MessagesListener());
        actor.monitor.subscribe(new TrafficListener());
        actor.monitor.subscribe(new EventsListener());
        listeners.put(actor, actor.monitor.getObservers());
    }

    /**
     * Monitors all Actors aviables
     */
    public void monitorAllActors(){
        Map<String, Actor> map = ActorContext.getRegistry();
        for(var entry : map.entrySet()){
            entry.getValue().monitor.subscribe(new MessagesListener());
            entry.getValue().monitor.subscribe(new TrafficListener());
            entry.getValue().monitor.subscribe(new EventsListener());
            listeners.put(entry.getValue(), entry.getValue().monitor.getObservers());
        }
    }

    /**
     * obtain a Map where the key is the traffic (LOW, MEDIUM, HIG)
     * and the value is the list of actor names
     * @return Map with the traffic
     */
    public Map getTraffic(){
        List<Actor> lowList= new ArrayList<>();
        List<Actor> midList= new ArrayList<>();
        List<Actor> highList= new ArrayList<>();
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
        Map<String, List<Actor>> traffic = new HashMap<>();
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
    public Map getNumberofMessages(Actor... actor){
        Map<String, List<Actor>> map = new HashMap<>();
        List<String> events = new ArrayList<>();

        for( var elem : actor){

            Map<String, List<String>> aux = listeners.get(elem).get(2).get(); // get event list from actor

            if(aux.containsKey("CREATED")){
                events=aux.get("CREATED");
            }
            if(aux.containsKey("STOPPED")){
                events=aux.get("STOPPED");
            }
            if(aux.containsKey("ERROR")){
                events=aux.get("ERROR");
            }
            if (!events.isEmpty()){
                putElems(events, map, elem, "CREATION");
                putElems(events, map, elem, "FINALIZATION");
                putElems(events, map, elem, "INCORRECT FINALIZATION");
                putElems(events, map, elem, "MESSAGE ADDED");
                putElems(events, map, elem, "MESSAGE SENDED");
            }

        }
        return map;
    }

    private void putElems(List<String> events, Map<String, List<Actor>> map, Actor entry, String key) {
        if(events.contains(key)){
            if(map.containsKey(key)) map.get(key).add(entry);
            else {
                List<Actor> aux2 = new ArrayList<>();
                aux2.add(entry);
                map.put(key, aux2);
            }

        }
    }
    /**
     * gets the messages sended by all actors
     * @return a Map where the key is the Actor, and the value is the messages sent by that actor
     */
    public Map getSentMessages(){
        Map<Actor, List<Message>> messages = new HashMap<>();
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
        Map<Actor, List<Message>> messages = new HashMap<>();
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
