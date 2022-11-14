package actor;
import missatge.*;

public interface Actor {
    public void send(Message missatge);

    public void proces();

    void setName(String name);
    void setId(long id);
}
