package com.physicsEngine.components.physics;

import com.physicsEngine.components.*;
import com.physicsEngine.components.physics.colliders.*;
import com.physicsEngine.vectors.*;
import com.physicsEngine.*;

public class RigidBody2D extends Component{
  public Transform transform;
  public GameObject gameObject;
	public Collider collider;
	public float mass;
	private float momentOfInertia;
  private float torque;
  public float angularVelocity;
  public float angularAcceleration;
    
	public RigidBody2D(Transform transform,GameObject gameObject,Collider collider,float mass){
		this.transform = transform;
		this.gameObject =  gameObject;
		this.collider = collider;
		this.mass = mass;
 
		name = "RigidBody2D";
	}

    //this method computes the moment of inertia of different shapes 
	public void computeMomentOfInertia(){

  if(collider.shape.name.equals("box"))
  momentOfInertia = mass * (collider.shape.height * collider.shape.height + collider.shape.width * collider.shape.width) / 12;
  else if(collider.shape.name.equals("circle"))
  momentOfInertia = mass * collider.shape.height * collider.shape.width;

	}
 //this method is used to apply a force to an object in the surface of an object (for example when object hit an other)
    public void applyHitForce(Vector2 force,Vector2 hitPoint){
      computeMomentOfInertia();
     /* r is the 'arm vector' that goes from the center of mass to the point of force application (this works for 
    	rectangles & circles & any shape) */
     Vector2 r = new Vector2(Math.abs(hitPoint.x - transform.position.x),Math.abs(hitPoint.y - transform.position.y));
     //calculating the torque
     torque = r.x * force.y - r.y * force.x;

     //computing the angular velocity & acceleration
     angularAcceleration = torque / momentOfInertia;
     angularVelocity += angularAcceleration * PhysicsManager.getDeltaTime();
     
    }
    public void updateAngularVelocity(){

    }
   
}