
package com.physicsEngine.components.physics.colliders;

import com.physicsEngine.vectors.*;
import com.physicsEngine.shapes.*;


public class CircleCollider extends Collider{

 public float radius;
 public Vector2 center;

 public CircleCollider(float radius,Vector2 center){
 	this.radius = radius;
 	this.center = center;
    shape = new Circle(radius);
 	name = "circle collider";
 }

}