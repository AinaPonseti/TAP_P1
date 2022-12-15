package observer;

import messages.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class TrafficListener implements ActorListener{
    private int traffic;
        @Override
    public void update (int status, Message message) {
        if(status == 2 ){
            traffic++;
        }
    }

    @Override
    public Map get() {
            Map<Integer, Integer> map=new HashMap<>();
            map.put(0,traffic);
            return map;
    }

}
