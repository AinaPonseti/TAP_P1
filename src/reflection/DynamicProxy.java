package reflection;

import actors.ActorProxy;


public class DynamicProxy {

    public static Service intercept(Service service, ActorProxy actor){
        service.setActor(actor);
        return service;
    }
}
