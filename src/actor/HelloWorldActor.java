package actor;

import missatge.Message;

public class HelloWorldActor extends Act {
    private static Message[] cua=new Message[30];
    private static int nMissatges;

    public void HelloWorldActor(){
        nMissatges=0;
    }
    public void send(Message missatge){
        cua[nMissatges]=new Message(missatge.getFrom(), missatge.getBody());
        nMissatges++;
    }


    private static void eliminarElem(){
        for(int i=0; i<nMissatges; i++){
            cua[i]=cua[i+1];
        }
        nMissatges--;
    }
    public void run(){
        while(nMissatges>0){

            try {
                    Thread.sleep(100);
                System.out.println(cua[0].getBody());
                eliminarElem();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
