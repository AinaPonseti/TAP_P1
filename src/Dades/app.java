package Dades;
import actor.*;
import proxy.*;
import missatge.*;

public class app {
	public static void main(String[] args) {
		Actor hello=new ActorProxy(ActorContext.spawnActor("name", new RingActor()));
		Actor h2=new ActorProxy(ActorContext.spawnActor("h2", new HelloWorldActor()));
		for(int i=0;i<10;i++){
			hello.send(new Message(null,"Hello World"));
			h2.send(new Message(null,"Goodbye World"));
		}

		/*Actor insult = ActorContext.spawnActor("name",new InsultActor());
		insult.send(new GetInsultMessage());
		Message result = insult.receive();
		System.out.println(result.getText());
		//dynamic Proxy
		Actor insult = ActorContext.spawnActor("name",new InsultActor());
		InsultService insulter = DynamicProxy.intercept(insult);
		insulter.addInsult("stupid");
		System.out.println(insulter.getInsult());
		
		
		//decorator
		ActorProxy hello = ActorContext.spawnActor("name",new FireWallDecorator(new RingActor()));
*/
		
	}
}
