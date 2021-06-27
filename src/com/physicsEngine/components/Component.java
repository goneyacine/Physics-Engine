
package com.physicsEngine.components;

import com.physicsEngine.GameObject;

public class Component {
	private boolean isEnabled = true;
	public String name;
    public GameObject gameObject;
	
	public boolean isEnabled() {return isEnabled;}
	public void disable() {this.isEnabled = false;}
	public void enable() {this.isEnabled = true;}
    public void start() {}
	public void update() {}
	//the fixedUpdate method is used to do physics
	public void fixedUpdate(){}
}