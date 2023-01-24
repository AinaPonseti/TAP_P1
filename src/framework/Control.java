package framework;

import Validation.Ring;
import actors.Actor;
import actors.RingActor;
import messages.Message;
import observer.MonitorService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Control {
    MonitorService monitor = new MonitorService();

    Ring ring;
    public void enviarMissatge() {
        ring.sendMessageToRing(new Message("hola"));
    }

    public List<Actor> addActors(){
        RingActor ini = new RingActor();
        RingActor end = new RingActor();
        ring=new Ring(ini,end);
        List aux =new ArrayList<>();
        aux.add(ini);
        aux.add(end);
        return aux;
    }

    public RingActor addActor() {
        RingActor actor = new RingActor();
        ring.addActor(actor);
        return actor;
    }
    public void monitor(Actor act) {
        monitor.monitorActor(act);
    }

    /**
     * Method to get the number of messages of an actor
     * @param actor actor that sends or recive the messages
     * @param version   0 if sended messages
     *                  other if recived messages
     * @return number of messages
     */
    public int getnMessages(Actor actor,int version){
        List a;
        if(version==0){
            a=(List) monitor.getSentMessages().get(actor);
        }else a=(List) monitor.getRecivedMessages().get(actor);
        return a.size();
    }
}
