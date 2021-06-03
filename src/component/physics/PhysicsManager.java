
package components.physics;

public class PhysicsManager {

	private static float deltaTime = 1f/60f;
	private static float timeSpeed = 1f;

	public static void setDeletaTime(float dt){deltaTime = dt;}
	public static float getDeletaTime(){return deltaTime;}

    public static void setTimeSpeed(float tSpeed){timeSpeed = tSpeed;}
    public static float getTimeSpeed(){return timeSpeed;}
}