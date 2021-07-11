
package com.physicsEngine.components;

import com.physicsEngine.GameObject;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.vectors.*;

public class Transform extends Component {

	public Vector2 position;
	public float zAngle;
	public Vector2 scale;
    

	public Transform(Vector2 position, float zAngle, Vector2 scale,GameObject gameObject) {
		this.position = position;
		this.zAngle = zAngle;
		this.scale = scale;
		this.gameObject = gameObject;
		//setting the name of the component
		name = "Transform";
	}
}