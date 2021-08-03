
package com.physicsEngine.physics;

import java.util.List;
import java.util.ArrayList;
import com.physicsEngine.components.physics.colliders.*;
import com.physicsEngine.vectors.Vector2;

public class PhysicsManager {
	/** the time between physics frames */
	private static float deltaTime = 1f / 60f;

	/** this control the speed of physics movements */
	private static float timeScale = 1f;
	private static PhysicsManager physicsManager;

	/** contains all the enabled collider objects in the scene */
	private List<Collider> colliders = new ArrayList<Collider>();
	/** 0 if it's the x axis & 1 if it's the y axis */
	private int longestAxis;

	public BVHTree bvhTree;

	private List<List<BVHNode>> bvhTreeLayers = new ArrayList<List<BVHNode>>();

	public PhysicsManager() {
		physicsManager = this;
		bvhTree = new BVHTree(new BVHNode(null, 0));
	}

	public void physicsSetUp() {

		bvhTree.root.childs = new ArrayList<BVHNode>();
		bvhTree.root.colliders = new ArrayList<Collider>();

		for (Collider collider : colliders)
			bvhTree.root.addCollider(collider);

		bvhTree.root.splitCollidersList();
	}

	public static void setDeletaTime(float dt) {
		deltaTime = dt;
	}

	public static float getDeltaTime() {
		return deltaTime;
	}

	public static void setTimeScale(float tSpeed) {
		timeScale = tSpeed;
	}

	public static float getTimeScale() {
		return timeScale;
	}

	public static PhysicsManager physicsManager() {
		return physicsManager;
	}

	public void physicsUpdate(){
	collisionUpdate();
	}
	private void collisionUpdate(){
	for(int i = bvhTreeLayers.size() - 1; i >= 0;i--){
		for(BVHNode node : bvhTreeLayers.get(i)){

		for(int j = 0;j < node.getColliders().size();j++){
			checkInterceptionColliderColliders(node.getColliders().get(j),node.getColliders());
		}

		for(BVHNode n : node.getchilds())
		checkInterceptionNodeColliders(n, node.getColliders());

		for(int j = 0;j < node.getchilds().size();j++){
			for(BVHNode n : node.getchilds()){
				if(n.equals(node.getchilds().get(j)))
				continue;

				checkInterceptionNodeNode(n, node.getchilds().get(j));
			}
		}
		}
	}
	}

	/**
	 * check if there is an interception between a collider & a list of colliders
	 * 
	 * @param a
	 * @param colliders
	 */
	private void checkInterceptionColliderColliders(Collider a, List<Collider> colliders) {
		for (Collider collider : colliders) {

             if(a.equals(collider))
			 continue;

			if (a.getMinMax()[0][0] <= collider.getMinMax()[1][0] && a.getMinMax()[0][0] >= collider.getMinMax()[0][0]
					&& a.getMinMax()[0][1] <= collider.getMinMax()[1][1]
					&& a.getMinMax()[0][1] >= collider.getMinMax()[0][1])
				checkCollision(a, collider);
			else if (a.getMinMax()[1][0] <= collider.getMinMax()[1][0]
					&& a.getMinMax()[1][0] >= collider.getMinMax()[0][0]
					&& a.getMinMax()[1][1] <= collider.getMinMax()[1][1]
					&& a.getMinMax()[1][1] >= collider.getMinMax()[0][1])
				checkCollision(a, collider);
			else if (a.getMinMax()[1][0] <= collider.getMinMax()[1][0]
					&& a.getMinMax()[1][0] >= collider.getMinMax()[0][0]
					&& a.getMinMax()[0][1] <= collider.getMinMax()[1][1]
					&& a.getMinMax()[0][1] >= collider.getMinMax()[0][1])
				checkCollision(a, collider);
			else if (a.getMinMax()[0][0] <= collider.getMinMax()[1][0]
					&& a.getMinMax()[0][0] >= collider.getMinMax()[0][0]
					&& a.getMinMax()[1][1] <= collider.getMinMax()[1][1]
					&& a.getMinMax()[1][1] >= collider.getMinMax()[0][1])
				checkCollision(a, collider);
		}
	}

	private void checkInterceptionNodeCollider(BVHNode node, Collider collider) {
		if ((node.getMinMax()[0][0] <= collider.getMinMax()[1][0]
				&& node.getMinMax()[0][0] >= collider.getMinMax()[0][0]
				&& node.getMinMax()[0][1] <= collider.getMinMax()[1][1]
				&& node.getMinMax()[0][1] >= collider.getMinMax()[0][1])
				|| (node.getMinMax()[1][0] <= collider.getMinMax()[1][0]
						&& node.getMinMax()[1][0] >= collider.getMinMax()[0][0]
						&& node.getMinMax()[1][1] <= collider.getMinMax()[1][1]
						&& node.getMinMax()[1][1] >= collider.getMinMax()[0][1])
				|| (node.getMinMax()[1][0] <= collider.getMinMax()[1][0]
						&& node.getMinMax()[1][0] >= collider.getMinMax()[0][0]
						&& node.getMinMax()[0][1] <= collider.getMinMax()[1][1]
						&& node.getMinMax()[0][1] >= collider.getMinMax()[0][1])
				|| (node.getMinMax()[0][0] <= collider.getMinMax()[1][0]
						&& node.getMinMax()[0][0] >= collider.getMinMax()[0][0]
						&& node.getMinMax()[1][1] <= collider.getMinMax()[1][1]
						&& node.getMinMax()[1][1] >= collider.getMinMax()[0][1])) {

			for (Collider co : node.getColliders()) {

				checkCollision(collider, co);

			}

			for (BVHNode n : node.getchilds()) {
				checkInterceptionNodeCollider(n, collider);
			}
		}

	}

	private void checkInterceptionNodeColliders(BVHNode node, List<Collider> colliders) {
		for (Collider collider : colliders) {
			if ((node.getMinMax()[0][0] <= collider.getMinMax()[1][0]
					&& node.getMinMax()[0][0] >= collider.getMinMax()[0][0]
					&& node.getMinMax()[0][1] <= collider.getMinMax()[1][1]
					&& node.getMinMax()[0][1] >= collider.getMinMax()[0][1])
					|| (node.getMinMax()[1][0] <= collider.getMinMax()[1][0]
							&& node.getMinMax()[1][0] >= collider.getMinMax()[0][0]
							&& node.getMinMax()[1][1] <= collider.getMinMax()[1][1]
							&& node.getMinMax()[1][1] >= collider.getMinMax()[0][1])
					|| (node.getMinMax()[1][0] <= collider.getMinMax()[1][0]
							&& node.getMinMax()[1][0] >= collider.getMinMax()[0][0]
							&& node.getMinMax()[0][1] <= collider.getMinMax()[1][1]
							&& node.getMinMax()[0][1] >= collider.getMinMax()[0][1])
					|| (node.getMinMax()[0][0] <= collider.getMinMax()[1][0]
							&& node.getMinMax()[0][0] >= collider.getMinMax()[0][0]
							&& node.getMinMax()[1][1] <= collider.getMinMax()[1][1]
							&& node.getMinMax()[1][1] >= collider.getMinMax()[0][1])) {

				for (Collider co : node.getColliders()) {
					checkCollision(collider, co);
				}

				for (BVHNode n : node.getchilds()) {
					checkInterceptionNodeCollider(n, collider);
				}
			}

		}
	}

	private void checkInterceptionNodeNode(BVHNode a, BVHNode b) {
		if ((a.getMinMax()[0][0] <= b.getMinMax()[1][0] && a.getMinMax()[0][0] >= b.getMinMax()[0][0]
				&& a.getMinMax()[0][1] <= b.getMinMax()[1][1] && a.getMinMax()[0][1] >= b.getMinMax()[0][1])
				|| (a.getMinMax()[1][0] <= b.getMinMax()[1][0] && a.getMinMax()[1][0] >= b.getMinMax()[0][0]
						&& a.getMinMax()[1][1] <= b.getMinMax()[1][1] && a.getMinMax()[1][1] >= b.getMinMax()[0][1])
				|| (a.getMinMax()[1][0] <= b.getMinMax()[1][0] && a.getMinMax()[1][0] >= b.getMinMax()[0][0]
						&& a.getMinMax()[0][1] <= b.getMinMax()[1][1] && a.getMinMax()[0][1] >= b.getMinMax()[0][1])
				|| (a.getMinMax()[0][0] <= b.getMinMax()[1][0] && a.getMinMax()[0][0] >= b.getMinMax()[0][0]
						&& a.getMinMax()[1][1] <= b.getMinMax()[1][1] && a.getMinMax()[1][1] >= b.getMinMax()[0][1])) {

		for(BVHNode nodeA : a.getchilds())
		    for(BVHNode nodeB : b.getchilds())
            checkInterceptionNodeNode(nodeA,nodeB);
		for(Collider colliderA : a.getColliders())
		    for(Collider colliderB : b.getColliders())
			checkCollision(colliderA,colliderB);

		checkInterceptionNodeColliders(a,b.getColliders());
		checkInterceptionNodeColliders(b,a.getColliders());

		}
	}

	/**
	 * removes a collider from the colliders list
	 * 
	 * @param collider the collider that should be removed
	 * @implNote use this when a collider object is disabled or removed from the
	 *           active scene
	 */
	public void removeCollider(Collider collider) {
		colliders.remove(collider);
	}

	/** removes all colliders from colliders list */
	public void removeAllColliders() {
		colliders = new ArrayList<Collider>();
	}

	/**
	 * adds a collider to the colliders list
	 * 
	 * @param collider the collider that should be added
	 * @apiNote the collider will not be added if it already exists on the colliders
	 *          list
	 * @implNote use this when a collider object is enabled or added to the scene
	 */
	public void addCollider(Collider collider) {
		if (!colliders.contains(collider))
			colliders.add(collider);
	}

	private void checkCollision(Collider a, Collider b) {
	CircleCollider c = (CircleCollider)a;
	CircleCollider d = (CircleCollider)b;
	if(Vector2.distance(c.gameObject.transform.position, d.gameObject.transform.position) <= c.radius + d.radius){
    a.gameObject.spriteRenderer().color[0] = 1;
    b.gameObject.spriteRenderer().color[0] = 1;
    a.gameObject.spriteRenderer().color[1] = 0;
    b.gameObject.spriteRenderer().color[1] = 0;
	}

	}

	public class BVHTree {
		private BVHNode root;

		public BVHTree(BVHNode root) {
			this.root = root;
		}

		public BVHNode getRoot() {
			return root;
		}

		public void setRoot(BVHNode root) {
			this.root = root;
		}
	}

	public class BVHNode {
		private List<Collider> colliders = new ArrayList<Collider>();
		private List<BVHNode> childs = new ArrayList<BVHNode>();
		private int layer;
		private float[] minPoint;
		private float[] maxPoint;

		/**
		 * @implNote if the childs list is null, then it'll not be set as a childs list
		 *           of this node, the same thing with the colliders list
		 * @param childs
		 * @param layer  the layer of the node on the tree (root layer is 0)
		 */
		public BVHNode(List<BVHNode> childs, int layer) {
			if (childs != null)
				this.childs = childs;

			this.layer = layer;
		}

		public List<Collider> getColliders() {
			return colliders;
		}

		public void addCollider(Collider collider) {
			if (!colliders.contains(collider))
				colliders.add(collider);

			collider.bvhNode = this;
			computeLongestAxis();
		}

		public void removeCollider(Collider collider) {
			colliders.remove(collider);

			computeLongestAxis();
		}

		public void removeAllColliders() {
			colliders = new ArrayList<Collider>();
		}

		public List<BVHNode> getchilds() {
			return childs;
		}

		public void scrapeColliders(BVHNode parent) {
			for (BVHNode child : parent.getchilds()) {
				if (child.colliders.size() == 0)
					scrapeColliders(child);
				else
					for (Collider collider : child.getColliders())
						parent.colliders.add(collider);

			}
			parent.childs = new ArrayList<BVHNode>();
		}

		public void splitCollidersList() {
			if (colliders.size() == 0) {
				if (childs.size() == 0)
					return;
				else
					scrapeColliders(this);
			}

			if (colliders.size() >= 4) {
				computeLongestAxis();
				sortCollidersList();
				removeAllChilds();
				BVHNode child1 = new BVHNode(null, layer + 1);
				BVHNode child2 = new BVHNode(null, layer + 1);
				for (int i = 0; i < colliders.size(); i++) {
					if (i < (colliders.size() - 1) / 2)
						child1.addCollider(colliders.get(i));
					else
						child2.addCollider(colliders.get(i));
				}

				child1.splitCollidersList();
				child2.splitCollidersList();
				addChild(child1);
				addChild(child2);
				colliders = new ArrayList<Collider>();
			} else
				return;
		}

		private void sortCollidersList() {
			int low = 0;
			int high = colliders.size() - 1;
			quickSort(colliders, low, high);
		}

		private void quickSort(List<Collider> colliders, int low, int high) {
			if (low < high) {

				// pi is partitioning index, arr[p]
				// is now at right place
				int pi = partition(colliders, low, high);

				// Separately sort elements before
				// partition and after partition
				quickSort(colliders, low, pi - 1);
				quickSort(colliders, pi + 1, high);
			}
		}

		private void swap(List<Collider> list, int i, int j) {
			Collider temp = list.get(i);
			list.set(i, list.get(j));
			list.set(j, temp);
		}

		private int partition(List<Collider> list, int low, int high) {

			// pivot
			float pivot = longestAxis == 0 ? list.get(high).gameObject.transform.position.x
					: list.get(high).gameObject.transform.position.y;

			int i = (low - 1);

			for (int j = low; j <= high - 1; j++) {

				// If current element is smaller
				// than the pivot
				if ((list.get(j).gameObject.transform.position.x < pivot && longestAxis == 0)
						|| (list.get(j).gameObject.transform.position.y < pivot && longestAxis == 1)) {

					// Increment index of
					// smaller element
					i++;
					swap(list, i, j);
				}
			}
			swap(list, i + 1, high);
			return (i + 1);
		}

		/**
		 * adds a collider to the childs list, if the collider is already in the list
		 * it'll not be added
		 * 
		 * @param child
		 */
		public void addChild(BVHNode child) {
			if (!childs.contains(child)) {
				childs.add(child);
				// try & add this child to its layer on the bvhTreeList but if we can't find
				// it's layer we add a new one (new layer)
				while (bvhTreeLayers.size() - 1 < child.layer) {
					List<BVHNode> newLayer = new ArrayList<BVHNode>();
					bvhTreeLayers.add(newLayer);
				}
				bvhTreeLayers.get(child.layer).add(child);
			}
		}

		/**
		 * removes a collider from the childs list
		 * 
		 * @param child
		 */
		public void removeChild(BVHNode child) {
			childs.remove(child);
		}

		/** removes all childs from childs list */
		public void removeAllChilds() {
			childs = new ArrayList<BVHNode>();
		}

		public void computeLongestAxis() {
			computeMinMax();
			if (maxPoint[0] - minPoint[0] > maxPoint[1] - minPoint[1])
				longestAxis = 0;
			else
				longestAxis = 1;
		}

		public void computeMinMax() {
			if (colliders.size() == 0)
				return;

			minPoint = colliders.get(0).getMinMax()[0];
			maxPoint = colliders.get(0).getMinMax()[1];
			for (Collider collider : colliders) {
				if (collider.getMinMax()[0][0] < minPoint[0])
					minPoint[0] = collider.getMinMax()[0][0];

				if (collider.getMinMax()[1][0] > maxPoint[0])
					maxPoint[0] = collider.getMinMax()[1][0];

				if (collider.getMinMax()[0][1] < minPoint[1])
					minPoint[1] = collider.getMinMax()[0][1];

				if (collider.getMinMax()[1][1] > maxPoint[1])
					maxPoint[1] = collider.getMinMax()[1][1];

			}
		}

		public float[][] getMinMax() {
			float[][] minMax = { minPoint, maxPoint };
			return minMax;
		}
	}
}