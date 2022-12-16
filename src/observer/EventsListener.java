package observer;

import messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsListener implements ActorListener{

    private List<String> events = new ArrayList<>();
    private String actualStatus;
    @Override
    public void update(int status, Message message) {
        if(status==1){
            events.add("CREATION");
            actualStatus="CREATED";
        }
        if(status==0){
            events.add("FINALIZATION");
            actualStatus="STOPPED";
        }
        if(status==-1){
            events.add("INCORRECT FINALIZATION");
            actualStatus="ERROR";
        }
        if(status==2){
            events.add("MESSAGE ADDED");
        }
        if(status==3){
            events.add("MESSAGE SENDED");
        }
    }

    public Map get(){
        Map aux= new HashMap<>();
        aux.put(actualStatus,events);
        return aux;
    }
}
