
package com.physicsEngine.components.physics.colliders;

import com.physicsEngine.shapes.*;
import com.physicsEngine.components.*;
import com.physicsEngine.physics.PhysicsManager;
import com.physicsEngine.physics.PhysicsManager.BVHNode;

public class Collider extends Component {

	public Shape shape;
	public String name;
	public BVHNode bvhNode;
	/**
	 * min is the button left point of the AABB box of this collider & max is the
	 * top right point
	 * 
	 * @implNote the first point is min & the second is max
	 */
	protected float[][] minMax = new float[2][2];

	// everytime we enable a collider we should add it to the colliders list
	public void onEnable() {
		PhysicsManager.physicsManager().addCollider(this);
	}

	// everytime we disable a collider we should remove it from the colliders list
	public void onDisbale() {
		PhysicsManager.physicsManager().removeCollider(this);
	}

	/**
	 * computes the button left point of the AABB box (min) of this collider & the
	 * top right (max)
	 */
	public void computeMinMax() {
	}
     /**
	  * 
	  * @return the button left point of the AABB box (min) of this collider & the top right (max)
	  */
	public float[][] getMinMax() {
		return minMax;
	}

}