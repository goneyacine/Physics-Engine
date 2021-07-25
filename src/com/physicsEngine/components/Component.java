
package com.physicsEngine.components;

import com.physicsEngine.GameObject;

public class Component {
	private boolean isEnabled = true;
	public String name;
	public GameObject gameObject;

	public boolean isEnabled() {
		return isEnabled;
	}

	public void disable() {
		onDisbale();
		this.isEnabled = false;
	}

	public void enable() {
		onEnable();
		this.isEnabled = true;		
	}

	/**
	 * this method is called when the game starts
	 */
	public void start() {}

	/**
	 * this is called every frame with a fixed time steps (it's called 60 time per
	 * second but you can change that on the game class)
	 */
	public void update() {}

	/** this method is called when ever we remove a component from a gameObject */
	public void onRemove() {}

	/** this function is called when this component is enabled or added to a gameObject for the first time
	 * @implNote this is also called when the parent gameObject is spawned or enabled
	 */
	public void onEnable() {}
    /** this function is called when this component is disabled or removed from a gameObject for the first time
	 * @implNote this is also called when the parent gameObject is destroyed or disabled
	 */
	public void onDisbale() {}
    /** this function is called when a compoenent is removed from it's parent gameObject or when it's parent is destroyed */
	public void onDestroy() {}
}