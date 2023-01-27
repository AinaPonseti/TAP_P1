package framework;

import Validation.Ring;
import actors.Actor;
import actors.ActorContext;
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

    public List<String> addActors(){
        RingActor ini = new RingActor(1);
        RingActor end = new RingActor(2);
        ring=new Ring(ini,end);
        List aux =new ArrayList<>();
        aux.add("Ring1");
        aux.add("Ring2");
        return aux;
    }

    public String addActor(int i) {
        RingActor actor = new RingActor(i);
        ring.addActor(actor);
        return "Ring"+i;
    }
    public void monitor(String act) {
        monitor.monitorActor(ActorContext.lookup(act));
    }

    /**
     * Method to get the number of messages of an actor
     * @param idActor id of the actor that sends or recive the messages
     * @param version   0 if sended messages
     *                  other if recived messages
     * @return number of messages
     */
    public int getnMessages(String idActor,int version){
        List a=new ArrayList();
        Actor actor = ActorContext.lookup(idActor).getActor();
        if(actor!=null){
            if(version==0){
                a=(List) monitor.getSentMessages().get(actor);
            }else a=(List) monitor.getRecivedMessages().get(actor);
        }
        return a.size();
    }
}
