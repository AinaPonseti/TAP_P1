import actors.ActorContext;
import framework.Vista;

public class InterfaceApp {
    public static void main(String[] args) {
        ActorContext actorContext = ActorContext.getInstance();
        Vista window = new Vista();
        window.setVisible(true);
    }
}
