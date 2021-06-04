
package com.physicsEngine.components;

import com.physicsEngine.vectors.*;

public class Transform extends Component{
 
 public Vector2 position;
 public float zAngle;
 public Vector2 scale;
 
 public Transform(Vector2 position,float zAngle,Vector2 scale){
 	this.position = position;
 	this.zAngle = zAngle;
 	this.scale = scale;
    //setting the name of the component
 	name = "Transform";
 }
}