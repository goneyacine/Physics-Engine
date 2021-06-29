
package com.physicsEngine.components;

import com.physicsEngine.Game;
import com.physicsEngine.GameObject;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.vectors.*;

public class Transform extends Component {

	public Vector2 position;
	public float zAngle;
	public Vector2 scale;
    
    private Vector2 oldPosition;
    private float oldAngle;
	private Vector2 oldScale;
    //the sprite renderer attached to the parent gameobject
	public SpriteRenderer spriteRenderer;
	public Transform(Vector2 position, float zAngle, Vector2 scale,GameObject gameObject) {
		this.position = position;
		this.zAngle = zAngle;
		this.scale = scale;
		this.gameObject = gameObject;
		//setting the name of the component
		name = "Transform";

		oldPosition = position;
		oldAngle = zAngle;
		oldScale = scale;
	}
	public void update(){
		
	    spriteRenderer.renderScaledSprite();
		if(position.x != oldPosition.x || position.y != oldPosition.y || zAngle != oldAngle || scale.x != oldScale.x || scale.y != oldScale.y){
		if(gameObject.hasSpriteRenderer){
			//if the the rotation of the sprite has been changed then we should rerender the sprite
			if(zAngle != oldAngle && scale.x == oldScale.x && scale.y == oldScale.y)
			spriteRenderer.renderRotatedSprite();
            else if(scale.x != oldScale.x || scale.y != oldScale.y)
            spriteRenderer.renderScaledSprite();
		    Game.game.shouldRenderNewFrame = true;
	} 

		oldPosition = position;
		oldAngle = zAngle;
		oldScale = scale;
	
		}
	}
}