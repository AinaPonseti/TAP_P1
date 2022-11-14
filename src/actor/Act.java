package actor;
import missatge.Message;

public class Act extends Thread implements Actor{
    static long id;
    String nom;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public void send(Message missatge) {
    }

    @Override
    public void proces() {
        //TODO
    }



    public void run() {
    }
}

