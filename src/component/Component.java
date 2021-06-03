
package components;

public class Component{
	private boolean isEnabled = true;
	private String name;

	public boolean isEnabled(){return isEnabled;}
	public void disable(){this.isEnabled = false;}
	public void enable(){this.isEnabled = true;}
} 