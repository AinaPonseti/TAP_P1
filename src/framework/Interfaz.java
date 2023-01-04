package framework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interfaz extends JFrame {
    int nActors=0;
    JPanel panel = new JPanel(new FlowLayout(0)), panel2 = new JPanel(new FlowLayout(2));
    JProgressBar barra;
    JLabel nameActor, estadist;
    ThreadCarga aux;
    Object objeto = new Object();
    boolean pideParar;
    //listar actores
    //crear nuevos actores
    //monitorizar colas de actores con barras de progreso
    //estadisticas nº mens procesados por actor
    //RingApp
    private Button buttonCreateA, buttonSendMessage;
    static final long serialVersionUID = 0;
    public Interfaz() {
        super("Actors");
        this.setSize(new Dimension(560, 460));
        buttonSendMessage = new Button("Send Message");
        buttonSendMessage.addActionListener(sendMessage);
        buttonCreateA = new Button("Create actor");
        buttonCreateA.addActionListener(createActor);
        panel2.add(buttonCreateA);
        panel2.add(buttonSendMessage);
        this.add(panel2, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    Action createActor = new AbstractAction(){
        static final long serialVersionUID = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            int nMessaProc=0,nMessaTot=0;
            nActors++;
            JPanel panelAct = new JPanel();
            barra = new JProgressBar();
            //iniciaCuenta();
            nameActor = new JLabel("Actor "+nActors);
            estadist = new JLabel("nº messages processated: "+nMessaProc+"/"+nMessaTot);
            panelAct.setLayout(new GridLayout(0,3));
            panelAct.add(barra);
            panelAct.add(nameActor);
            panelAct.add(estadist);
            panel.add(panelAct);

            setVisible(true);

        }
    };

    Action sendMessage = new AbstractAction(){
        static final long serialVersionUID = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            //Send message
        }
    };

    // iniciate bar
    public void iniciaCuenta() {
        if( aux == null ) {
            aux = new ThreadCarga();
            pideParar = false;
            aux.start();
        }
    }

    // End bar
    public void detieneCuenta() {
        synchronized( objeto ) {
            pideParar = true;
            objeto.notify();
        }
    }

    // thread to control the bar (Actor thread)
    class ThreadCarga extends Thread {
        public void run() {
            int min = 0;
            int max = 100;

            barra.setValue( min );
            barra.setMinimum( min );
            barra.setMaximum( max );

            for (int i=min; i <= max; i++ ) {
                barra.setValue( i );

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
            aux = null;
        }
    }
}




