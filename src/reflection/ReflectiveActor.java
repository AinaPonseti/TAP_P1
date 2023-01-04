package reflection;

import actors.Actor;
import messages.Message;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiveActor extends Actor{

    private Object target;

    /**
     * Constructor for the ReflectiveActor class
     * @param target target
     */
    public ReflectiveActor(Object target, String name) {
        super(name);
        this.target = target;
    }

    @Override
    public void onMessageReceived(Message message) {

        String methodName = message.getClass().getSimpleName();
        methodName = methodName.substring(0, methodName.length() - 7);
        methodName = Character.toLowerCase(methodName.charAt(0))+methodName.substring(1);

        try {
            Class[] params = new Class[1];
            params[0] = String.class;
            Method method = target.getClass().getMethod(methodName, params);
            method.invoke(target, message.getText());

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }
}
