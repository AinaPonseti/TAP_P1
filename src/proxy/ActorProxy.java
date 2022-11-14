package proxy;

import actor.*;
import Dades.*;
import missatge.*;

public class ActorProxy implements Actor {
	private Actor actor;

	public ActorProxy(Actor act){
		this.actor=act;
	}
	@Override
	public void send(Message missatge) {
		if(actor == null){
            actor = new HelloWorldActor();
        }
        actor.send(missatge);
	}

	@Override
	public void proces() {

	}

	@Override
	public void setName(String name) {

	}

	@Override
	public void setId(long id) {

	}
}
