package observer;

import actors.Actor;
import messages.Message;

import java.util.*;

public class MessagesListener implements ActorListener{
    private List<Message> sended = new ArrayList<>();
    private List<Message> recived = new ArrayList<>();


    public MessagesListener(){}
    @Override
    public void update(int status, Message message){
        if(status==2){
            sended.add(message);
        }
        if(status==3){
            recived.add(message);
        }
    }

    /**
     *
     * @return a map with a list of sended (Key = 0) and recived (key = 1) messages
     */
    @Override
    public Map get() {
        Map<Integer, List> messages = new HashMap<>();
        messages.put(0,sended);
        messages.put(1,recived);
        return messages;
    }
}
