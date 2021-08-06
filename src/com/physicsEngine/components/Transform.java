
package com.physicsEngine.components;

import com.physicsEngine.GameObject;
import com.physicsEngine.vectors.*;

public class Transform extends Component {

	public Vector2 position;
	public float zAngle;
	public Vector2 scale;
    
	private Vector2 oldPosition;

	public Transform(Vector2 position, float zAngle, Vector2 scale,GameObject gameObject) {
		this.position = position;
		this.zAngle = zAngle;
		this.scale = scale;
		setGameObject(gameObject);
		//setting the name of the component
		setName("Transform");
		oldPosition = position;
	}
	public void update(){
		if(gameObject().hasCollider){
		   if(oldPosition.x != position.x && oldPosition.y != position.y){
			oldPosition = position;
			gameObject().collider.onMove();
		   }
		}
	}
}