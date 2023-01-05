package framework;

import Validation.Ring;
import actors.*;
import messages.*;
import observer.MonitorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static java.lang.System.exit;

public class Interfaz extends JFrame {
    int nActors=0;
    JPanel panel = new JPanel(new FlowLayout(0)), panel2 = new JPanel(new FlowLayout(2));

    JLabel nameActor, estadist;
    ThreadCarga aux;
    Object objeto = new Object();
    Ring ring;
    MonitorService monitor = new MonitorService();
    boolean pideParar;
    private Button buttonCreateA, buttonSendMessage, buttonStop;
    static final long serialVersionUID = 0;
    public Interfaz() {
        super("Actors");
        this.setSize(new Dimension(560, 460));
        buttonSendMessage = new Button("Send a message");
        buttonSendMessage.addActionListener(sendMessage);
        buttonCreateA = new Button("Create actor");
        buttonCreateA.addActionListener(createActor);
        buttonStop = new Button("Stop");
        buttonStop.addActionListener(stop);
        panel2.add(buttonCreateA);
        panel2.add(buttonSendMessage);
        panel2.add(buttonStop);
        this.add(panel2, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    Action createActor = new AbstractAction(){
        static final long serialVersionUID = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            int i;
            JLabel estadist;
            JProgressBar barra;
            if(nActors==0){
                nActors++;
                JPanel panelAct = new JPanel();
                barra = new JProgressBar();
                nameActor = new JLabel("Actor "+nActors);
                estadist = new JLabel("nº messages processated: ");
                panelAct.setLayout(new GridLayout(0,3));
                panelAct.add(barra);
                panelAct.add(nameActor);
                panelAct.add(estadist);
                panel.add(panelAct);
                setVisible(true);

                JProgressBar barra2;
                JLabel estadist2;
                nActors++;
                JPanel panelAct2 = new JPanel();
                barra2 = new JProgressBar();
                nameActor = new JLabel("Actor "+nActors);
                estadist2 = new JLabel("nº messages processated: ");
                panelAct2.setLayout(new GridLayout(0,3));
                panelAct2.add(barra2);
                panelAct2.add(nameActor);
                panelAct2.add(estadist2);
                panel.add(panelAct2);
                addActors(barra,estadist,barra2,estadist2);

            }else{
                nActors++;
                JPanel panelAct = new JPanel();
                barra = new JProgressBar();
                //iniciaCuenta();
                nameActor = new JLabel("Actor "+nActors);
                estadist = new JLabel("nº messages processated: ");
                panelAct.setLayout(new GridLayout(0,3));
                panelAct.add(barra);
                panelAct.add(nameActor);
                panelAct.add(estadist);
                panel.add(panelAct);
                addActor(barra,estadist);
            }


            setVisible(true);

        }
    };

    Action sendMessage = new AbstractAction(){
        static final long serialVersionUID = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            ring.sendMessageToRing(new Message("hola"));
        }
    };

    Action stop = new AbstractAction(){
        static final long serialVersionUID = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            exit(1);
        }
    };

    public void addActors(JProgressBar bar1, JLabel label1, JProgressBar bar2, JLabel label2 ){
            RingActor ini = new RingActor();
            RingActor end = new RingActor();
            ring=new Ring(ini,end);
            new ThreadCarga(ini,bar1,label1).start();
            new ThreadCarga(end,bar2,label2).start();
    }

    public void addActor(JProgressBar bar, JLabel label){
        RingActor actor = new RingActor();
        ring.addActor(actor);
        new ThreadCarga(actor,bar,label).start();
    }
    // iniciate bar


    // End bar
    public void detieneCuenta() {
        synchronized( objeto ) {
            pideParar = true;
            objeto.notify();
        }
    }

    // thread to control the bar (Actor thread)
    class ThreadCarga extends Thread {
        RingActor actor;
        JProgressBar bar;
        JLabel label;
        int nMess=0;
        public ThreadCarga(RingActor act, JProgressBar barra, JLabel estad){
            this.actor=act;
            monitor.monitorActor(actor);
            this.bar=barra;
            label=estad;
        }
        public void run() {
            int mesSended = 0;
            int max = 100;

            bar.setValue( mesSended );
            bar.setMinimum( mesSended );
            bar.setMaximum( max );
            List<Message> a = new ArrayList<>();
            while(true){
                a=(List) monitor.getSentMessages().get(actor);
                mesSended=a.size();
                bar.setValue( mesSended );
                a=(List) monitor.getRecivedMessages().get(actor);
                max=a.size();
                bar.setMaximum(max);
                label.setText("nº messages processated: "+mesSended+"/"+max);

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized( objeto ) {
                    if( pideParar )
                        break;
                    try {
                        objeto.wait( 100 );
                    } catch( InterruptedException e ) {
                        // Se ignoran las excepciones
                    }
                }
            }
        }
    }
}




