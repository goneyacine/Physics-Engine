
package com.physicsEngine.components.physics.colliders;

import com.physicsEngine.shapes.*;
import com.physicsEngine.GameObject;
import com.physicsEngine.components.*;
import com.physicsEngine.physics.PhysicsManager;
import com.physicsEngine.physics.PhysicsManager.BVHNode;

public class Collider extends Component {

	public Shape shape;
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
	 * @return the button left point of the AABB box (min) of this collider & the
	 *         top right (max)
	 */
	public float[][] getMinMax() {
		return minMax;
	}

	public void onCollisionEnter(Collider other) {
    gameObject().spriteRenderer().color[0] = 1;
	}

	/**
	 * this method is called the object of this collider moves so it restruct the
	 * BVHTree to feet in with the new data
	 */
	public void onMove() {
		// if the collider object is still inside it's original node then we don't need
		// to anything except computing the min max
		if (gameObject().transform.position.x >= bvhNode.getMinMax()[0][0]
				&& gameObject().transform.position.x <= bvhNode.getMinMax()[1][0]
				&& gameObject().transform.position.y >= bvhNode.getMinMax()[0][1]
				&& gameObject().transform.position.y <= bvhNode.getMinMax()[1][1]) {
			bvhNode.computeMinMax();
			return;
		} else {
        onMoveCheck(bvhNode.getParent());
		}
	}

	/**
	 * this method is used the onMove method to find out what is the best node to
	 * put this collider in
	 */
	private void onMoveCheck(BVHNode node) {
		if (gameObject().transform.position.x >= node.getMinMax()[0][0]
				&& gameObject().transform.position.x <= node.getMinMax()[1][0]
				&& gameObject().transform.position.y >= node.getMinMax()[0][1]
				&& gameObject().transform.position.y <= node.getMinMax()[1][1]) {
			node.splitCollidersList();
			return;
		} else {
			if (node.getParent() != null)
				onMoveCheck(node.getParent());
			else
				node.splitCollidersList();
		}
	}
}