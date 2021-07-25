
package com.physicsEngine.components.physics.colliders;

import com.physicsEngine.shapes.*;
import com.physicsEngine.components.*;
import com.physicsEngine.physics.PhysicsManager;

public class Collider extends Component {

	public Shape shape;
	public String name;
	//everytime we enable a collider we should add it to the colliders list 
    public void onEnable(){
		PhysicsManager.physicsManager().addCollider(this);
	}
	//everytime we disable a collider we should remove it from the colliders list
	public void onDisbale() {
		PhysicsManager.physicsManager().removeCollider(this);
	}

}