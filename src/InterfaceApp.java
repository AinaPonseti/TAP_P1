import actors.ActorContext;
import framework.Interface;

public class InterfaceApp {
    public static void main(String[] args) {
        ActorContext actorContext = ActorContext.getInstance();
        Interface window = new Interface();
        window.setVisible(true);
    }
}
