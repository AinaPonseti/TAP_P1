package reflection;

import actors.Actor;
import messages.AddInsultMessage;
import messages.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiveActor extends Actor {

    private InsultService insultService;

    /**
     * Constructor for the ReflectiveActor class
     * @param insultService insultService
     */
    public ReflectiveActor(InsultService insultService, String name) {
        super(name);
        this.insultService = insultService;
    }

    @Override
    public void onMessageReceived(Message message) {

        String methodName = message.getClass().getSimpleName();
        methodName = methodName.substring(0, methodName.length() - 7);
        methodName = Character.toLowerCase(methodName.charAt(0))+methodName.substring(1);

        try {
            Class[] params = new Class[1];
            params[0] = String.class;
            Method method = insultService.getClass().getMethod(methodName, params);
            method.invoke(null, message.getText());

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }
}
