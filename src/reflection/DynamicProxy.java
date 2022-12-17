package reflection;

import actors.Actor;
import actors.ActorProxy;

import java.lang.reflect.InvocationHandler;


public class DynamicProxy {

    public static InsultService intercept(InsultService insultService, ActorProxy actor){
        insultService.setInsultActor(actor);
        return insultService;
    }
}
