
package com.physicsEngine.components.physics;

public class PhysicsManager {

    //delta time is the time between physics frames  
	private static float deltaTime = 1f/60f;
	//this control the speed of physics movements
	private static float timeSpeed = 1f;

	public static void setDeletaTime(float dt){deltaTime = dt;}
	public static float getDeletaTime(){return deltaTime;}

    public static void setTimeSpeed(float tSpeed){timeSpeed = tSpeed;}
    public static float getTimeSpeed(){return timeSpeed;}
}