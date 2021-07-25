
package com.physicsEngine.physics;

import java.util.*;
import com.physicsEngine.components.physics.colliders.*;

public class PhysicsManager {
	/** the time between physics frames */
	private static float deltaTime = 1f / 60f;

	/** this control the speed of physics movements */
	private static float timeScale = 1f;

	private static PhysicsManager physicsManager;

	/** contains all the enabled collider objects in the scene */
	private List<Collider> colliders = new ArrayList<Collider>();

	public PhysicsManager() {
		physicsManager = this;
	}

	public static void setDeletaTime(float dt) {
		deltaTime = dt;
	}

	public static float getDeltaTime() {
		return deltaTime;
	}

	public static void setTimeScale(float tSpeed) {
		timeScale = tSpeed;
	}

	public static float getTimeScale() {
		return timeScale;
	}

	public static PhysicsManager physicsManager() {
		return physicsManager;
	}

	/**
	 * removes a collider from the colliders list
	 * 
	 * @param collider the collider that should be removed
	 * @implNote use this when a collider object is disabled or removed from the active scene
	 */
	public void removeCollider(Collider collider) {
		colliders.remove(collider);
	}

	/**
	 * adds a collider to the colliders list
	 * 
	 * @param collider the collider that should be added
	 * @apiNote the collider will not be added if it already exists on the colliders list
	 * @implNote use this when a collider object is enabled or added to the scene
	 */
	public void addCollider(Collider collider) {
		if (!colliders.contains(collider))
			colliders.add(collider);
	}
}