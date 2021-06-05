package com.physicsEngine.components.physics.colliders;

import com.physicsEngine.vectors.*;
import com.physicsEngine.shapes.*;

public class BoxCollider extends Collider{
 
 public Vector2 size;
 public Vector2 center;

 public BoxCollider(Vector2 size,Vector2 center){
 	this.center = center;
 	this.size = size;

 	shape = new Box(size.x,size.y);
 	name = "box collider";
 }
}