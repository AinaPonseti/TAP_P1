package actor;

import missatge.Message;

public class RingActor extends Act {
    private static Message[] cua=new Message[30];
    private static int nMissatges=0;

    public void send(Message missatge){
        cua[nMissatges]=new Message(missatge.getFrom(), missatge.getBody());
        nMissatges++;
    }
    public RingActor() {

    }
    private static void eliminarElem(){
        for(int i=0; i<nMissatges; i++){
            cua[i]=cua[i+1];
        }
        nMissatges--;
    }

    @Override
    public void run() {
        while(nMissatges>0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(cua[0].getBody());
            eliminarElem();
        }
    }
}
