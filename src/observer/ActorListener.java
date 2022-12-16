package observer;

import actors.Actor;
import messages.Message;

import java.util.Map;

public interface ActorListener {
    void update(int status, Message message);
    Map get();
}
