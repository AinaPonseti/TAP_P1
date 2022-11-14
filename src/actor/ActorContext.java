package actor;


public class ActorContext{
	private static long id=0;
	private static final ActorContext INSTANCE = new ActorContext();
	private ActorContext(){}

	public static ActorContext getInstance(){
		return INSTANCE;
	}
	public static Actor spawnActor(String name, Act actor) {
		actor.setName(name);
		actor.setId(id);
		id++;
		actor.start();
		return actor;

	}
}
