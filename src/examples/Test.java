 package examples; 

 import com.physicsEngine.*;
 import com.physicsEngine.components.*;
 import com.physicsEngine.components.physics.*;
 import com.physicsEngine.components.physics.colliders.*;
 import com.physicsEngine.vectors.*;

public class Test{

 public static void main(String[] args){
 	GameObject gameObject = new GameObject(null,"TestObject");
 	RigidBody2D rb = new RigidBody2D(new Transform(new Vector2(0,0),0,new Vector2(1,1)),gameObject,new BoxCollider(new Vector2(4,6),new Vector2(0,0)),10);
 	rb.applyHitForce(new Vector2(4,70),new Vector2(2,-3));
 	System.out.println("Angular acceleration = " + rb.angularAcceleration);
 }

}