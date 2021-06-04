
package com.physicsEngine.components;

public class Component{
	private boolean isEnabled = true;
	public String name;

	public boolean isEnabled(){return isEnabled;}
	public void disable(){this.isEnabled = false;}
	public void enable(){this.isEnabled = true;}
} 