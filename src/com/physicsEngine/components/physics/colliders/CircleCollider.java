
package com.physicsEngine.components.physics.colliders;

import com.physicsEngine.vectors.*;
import com.physicsEngine.shapes.*;

public class CircleCollider extends Collider {

	public float radius;
	public Vector2 center;

	public CircleCollider(float radius, Vector2 center) {
		this.radius = radius;
		this.center = center;
		shape = new Circle(radius);
		name = "circle collider";
	}

	public void computeMinMax() {
		// computing the min point position
		minMax[0][0] = gameObject.transform.position.x - radius / 2 + center.x;
		minMax[0][1] = gameObject.transform.position.y - radius / 2 + center.y;
		// computing the max point position
		minMax[1][0] = gameObject.transform.position.x + radius / 2 + center.x;
		minMax[1][1] = gameObject.transform.position.y + radius / 2 + center.y;
	}

}