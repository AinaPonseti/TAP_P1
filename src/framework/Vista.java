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

public class Vista extends JFrame {
    JPanel panel = new JPanel(new FlowLayout(0)), panel2 = new JPanel(new FlowLayout(2));
    Control control=new Control();
    JLabel nameActor;
    int nActors=0;
    Object objeto = new Object();
    boolean pideParar;
    private Button buttonCreateA, buttonSendMessage, buttonStop;
    static final long serialVersionUID = 0;
    public Vista() {
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
            String idActor;
            if(nActors==0){
                JPanel panelAct = new JPanel();
                barra = new JProgressBar();
                nameActor = new JLabel("Actor "+1);
                estadist = new JLabel("nº messages processed: ");
                panelAct.setLayout(new GridLayout(0,3));
                panelAct.add(barra);
                panelAct.add(nameActor);
                panelAct.add(estadist);
                panel.add(panelAct);
                setVisible(true);

                JProgressBar barra2;
                JLabel estadist2;

                JPanel panelAct2 = new JPanel();
                barra2 = new JProgressBar();
                nameActor = new JLabel("Actor "+2);
                estadist2 = new JLabel("nº messages processed: ");
                panelAct2.setLayout(new GridLayout(0,3));
                panelAct2.add(barra2);
                panelAct2.add(nameActor);
                panelAct2.add(estadist2);
                panel.add(panelAct2);
                List<String> aux = control.addActors();

                new ThreadCarga(aux.get(0),barra,estadist).start();
                nActors=2;
                new ThreadCarga(aux.get(1),barra2,estadist2).start();


            }else{
                nActors++;
                JPanel panelAct = new JPanel();
                barra = new JProgressBar();
                nameActor = new JLabel("Actor "+nActors);
                estadist = new JLabel("nº messages processed: ");
                panelAct.setLayout(new GridLayout(0,3));
                panelAct.add(barra);
                panelAct.add(nameActor);
                panelAct.add(estadist);
                panel.add(panelAct);
                idActor=control.addActor(nActors);
                new ThreadCarga(idActor, barra, estadist).start();
            }


            setVisible(true);

        }
    };

    Action sendMessage = new AbstractAction(){
        static final long serialVersionUID = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            control.enviarMissatge();
        }
    };

    Action stop = new AbstractAction(){
        static final long serialVersionUID = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            exit(1);
        }
    };

    // End bar
    public void detieneCuenta() {
        synchronized( objeto ) {
            pideParar = true;
            objeto.notify();
        }
    }

    // thread to control the bar (Actor thread)
    class ThreadCarga extends Thread {
        String idActor;
        JProgressBar bar;
        JLabel label;
        int nMess=0;
        public ThreadCarga(String idAct, JProgressBar barra, JLabel estad){
            control.monitor(idAct);
            this.idActor=idAct;
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
                mesSended=control.getnMessages(idActor,0);
                max=control.getnMessages(idActor,1);
                bar.setValue(mesSended);
                bar.setMaximum(max);
                label.setText("nº messages processed: "+mesSended+"/"+max);
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




