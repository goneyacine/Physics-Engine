package com.physicsEngine.components.physics.colliders;

import com.physicsEngine.vectors.*;
import com.physicsEngine.shapes.*;

public class BoxCollider extends Collider {

	public Vector2 size;
	public Vector2 center;

	public BoxCollider(Vector2 size, Vector2 center) {
		this.center = center;
		this.size = size;

		shape = new Box(size.x, size.y);
		setName("Box Collider");
	}

	public void computeMinMax() {
		// computing the min point position
		minMax[0][0] = gameObject().transform.position.x - size.x / 2 + center.x;
		minMax[0][1] = gameObject().transform.position.y - size.y / 2 + center.y;
		// computing the max point position
		minMax[1][0] = gameObject().transform.position.x + size.x / 2 + center.x;
		minMax[1][1] = gameObject().transform.position.y + size.y / 2 + center.y;
	}
}