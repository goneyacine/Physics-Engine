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
		bvhTree = new BVHTree(new BVHNode(null, 0,null));
	}
    /**
	 * doing some setup stuff related to physics
	 */
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

	public void physicsUpdate() {
		collisionUpdate();
	}

	private void collisionUpdate() {
		for (int i = bvhTreeLayers.size() - 1; i >= 0; i--) {
			for (BVHNode node : bvhTreeLayers.get(i)) {

				for (int j = 0; j < node.getColliders().size(); j++) {
					checkInterceptionColliderColliders(node.getColliders().get(j), node.getColliders());
				}

				for (BVHNode n : node.getchilds())
					checkInterceptionNodeColliders(n, node.getColliders());

				for (int j = 0; j < node.getchilds().size(); j++) {
					for (BVHNode n : node.getchilds()) {
						if (n.equals(node.getchilds().get(j)))
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

			if (a.equals(collider))
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

	/**
	 * checks if a node a node intercepts with a box that surounds a collider
	 * 
	 * @param node     the BVHNode we check interception with
	 * @param collider the Collider we check interception with
	 */
	private void checkInterceptionNodeCollider(BVHNode node, Collider collider) {
		// checks if there is an interception between the node box & the box that
		// surounds the collider
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
			// if there an interception :

			// we check if any of the node colliders collides with that collider
			for (Collider co : node.getColliders()) {

				checkCollision(collider, co);

			}
			// we check if any of the node BVHNode childs intercepts with that collider
			for (BVHNode n : node.getchilds()) {
				checkInterceptionNodeCollider(n, collider);
			}
		}

	}

	/**
	 * checks if a node a node intercepts with a list of colliders
	 * 
	 * @param node      node to be checked
	 * @param colliders list of colliders to be checked
	 */
	private void checkInterceptionNodeColliders(BVHNode node, List<Collider> colliders) {
		// we check if there an interception for every collider in the colliders list
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
				// if there is an interception

				// we check if any of the node colliders collides with that collider
				for (Collider co : node.getColliders()) {
					checkCollision(collider, co);
				}
				// we check if any of the node BVHNode childs intercepts with that collider
				for (BVHNode n : node.getchilds()) {
					checkInterceptionNodeCollider(n, collider);
				}
			}

		}
	}

	/**
	 * checks if two nodes intercepts with each others
	 * 
	 * @param a first node
	 * @param b second node
	 */
	private void checkInterceptionNodeNode(BVHNode a, BVHNode b) {
		// checking for interception
		if ((a.getMinMax()[0][0] <= b.getMinMax()[1][0] && a.getMinMax()[0][0] >= b.getMinMax()[0][0]
				&& a.getMinMax()[0][1] <= b.getMinMax()[1][1] && a.getMinMax()[0][1] >= b.getMinMax()[0][1])
				|| (a.getMinMax()[1][0] <= b.getMinMax()[1][0] && a.getMinMax()[1][0] >= b.getMinMax()[0][0]
						&& a.getMinMax()[1][1] <= b.getMinMax()[1][1] && a.getMinMax()[1][1] >= b.getMinMax()[0][1])
				|| (a.getMinMax()[1][0] <= b.getMinMax()[1][0] && a.getMinMax()[1][0] >= b.getMinMax()[0][0]
						&& a.getMinMax()[0][1] <= b.getMinMax()[1][1] && a.getMinMax()[0][1] >= b.getMinMax()[0][1])
				|| (a.getMinMax()[0][0] <= b.getMinMax()[1][0] && a.getMinMax()[0][0] >= b.getMinMax()[0][0]
						&& a.getMinMax()[1][1] <= b.getMinMax()[1][1] && a.getMinMax()[1][1] >= b.getMinMax()[0][1])) {
			// if there an interception :

			// we check if there is an interception between every node from the first node
			// childs list & the second node childs list
			for (BVHNode nodeA : a.getchilds())
				for (BVHNode nodeB : b.getchilds())
					checkInterceptionNodeNode(nodeA, nodeB);
			// we check if there is an interception between every collider from the first
			// node & the second node
			for (Collider colliderA : a.getColliders())
				for (Collider colliderB : b.getColliders())
					checkCollision(colliderA, colliderB);

			// checks if there is an interception between the first node & the colliders of
			// the second node
			checkInterceptionNodeColliders(a, b.getColliders());
			// checks if there is an interception between the second node & the colliders of
			// the first node
			checkInterceptionNodeColliders(b, a.getColliders());

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

	/**
	 * checks if two colliders collides with each others
	 * 
	 * @param a first collider
	 * @param b second collider
	 */
	private void checkCollision(Collider a, Collider b) {
		if (a.name.equals("Circle Collider")) {
			if (b.name.equals("Circle Collider")) {
				if (Vector2.distance(a.gameObject.transform.position,
						b.gameObject.transform.position) <= ((CircleCollider) a).radius + ((CircleCollider) b).radius) {
					a.onCollisionEnter(b);
					b.onCollisionEnter(a);
				}
			} else if (b.name.equals("Box Collider")) {
				if (Vector2.distance(new Vector2(b.getMinMax()[0][0], b.getMinMax()[0][1]),
						b.gameObject.transform.position) <= ((CircleCollider) a).radius
						|| Vector2.distance(new Vector2(b.getMinMax()[1][0], b.getMinMax()[1][1]),
								b.gameObject.transform.position) <= ((CircleCollider) a).radius
						|| Vector2.distance(new Vector2(b.getMinMax()[1][0], b.getMinMax()[0][1]),
								b.gameObject.transform.position) <= ((CircleCollider) a).radius
						|| Vector2.distance(new Vector2(b.getMinMax()[0][0], b.getMinMax()[1][1]),
								b.gameObject.transform.position) <= ((CircleCollider) a).radius) {
					a.onCollisionEnter(b);
					b.onCollisionEnter(a);
				}
			}
		} else if (a.name.equals("Box Collider")) {
			if (b.name.equals("Circle Collider")) {
				if (Vector2.distance(new Vector2(a.getMinMax()[0][0], a.getMinMax()[0][1]),
						b.gameObject.transform.position) <= ((CircleCollider) b).radius
						|| Vector2.distance(new Vector2(a.getMinMax()[1][0], a.getMinMax()[1][1]),
								b.gameObject.transform.position) <= ((CircleCollider) b).radius
						|| Vector2.distance(new Vector2(a.getMinMax()[1][0], a.getMinMax()[0][1]),
								b.gameObject.transform.position) <= ((CircleCollider) b).radius
						|| Vector2.distance(new Vector2(a.getMinMax()[0][0], a.getMinMax()[1][1]),
								b.gameObject.transform.position) <= ((CircleCollider) b).radius) {
					a.onCollisionEnter(b);
					b.onCollisionEnter(a);

				}
			} else if (b.name.equals("Box Collider")) {
				if ((a.getMinMax()[0][0] <= b.getMinMax()[1][0] && a.getMinMax()[0][0] >= b.getMinMax()[0][0]
						&& a.getMinMax()[0][1] <= b.getMinMax()[1][1] && a.getMinMax()[0][1] >= b.getMinMax()[0][1])
						|| (a.getMinMax()[1][0] <= b.getMinMax()[1][0] && a.getMinMax()[1][0] >= b.getMinMax()[0][0]
								&& a.getMinMax()[1][1] <= b.getMinMax()[1][1]
								&& a.getMinMax()[1][1] >= b.getMinMax()[0][1])
						|| (a.getMinMax()[1][0] <= b.getMinMax()[1][0] && a.getMinMax()[1][0] >= b.getMinMax()[0][0]
								&& a.getMinMax()[0][1] <= b.getMinMax()[1][1]
								&& a.getMinMax()[0][1] >= b.getMinMax()[0][1])
						|| (a.getMinMax()[0][0] <= b.getMinMax()[1][0] && a.getMinMax()[0][0] >= b.getMinMax()[0][0]
								&& a.getMinMax()[1][1] <= b.getMinMax()[1][1]
								&& a.getMinMax()[1][1] >= b.getMinMax()[0][1])) {
					a.onCollisionEnter(b);
					b.onCollisionEnter(a);
				}
			}

		}

	}

	/**
	 * the tree that store & struct all the colliders in the runing scene to
	 * optimize collision detection (using BVH algorithm)
	 */
	public class BVHTree {
		private BVHNode root;

		public BVHTree(BVHNode root) {
			this.root = root;
		}

		/**
		 * 
		 * @return the root node of the tree
		 */
		public BVHNode getRoot() {
			return root;
		}

		/**
		 * sets the root node of the tree
		 * 
		 * @param root
		 */
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
        private BVHNode parent;
		/**
		 * @implNote if the childs list is null, then it'll not be set as a childs list
		 *           of this node, the same thing with the colliders list
		 * @param childs
		 * @param layer  the layer of the node on the tree (root layer is 0)
		 * @param parent the parent of this node
		 */
		public BVHNode(List<BVHNode> childs, int layer,BVHNode parent) {
			if (childs != null)
				this.childs = childs;

			this.layer = layer;
			this.parent = parent;
		}
        /**
		 * 
		 * @return the parent of this node
		 */
		public BVHNode getParent(){
			return parent;
		}

		/**
		 * 
		 * @return the colliders that are in this node
		 */
		public List<Collider> getColliders() {
			return colliders;
		}
        /**
		 * adds a collider to the colliders list of BVHNode object
		 * @param collider
		 */
		private void addCollider(Collider collider) {
			if (!colliders.contains(collider))
				colliders.add(collider);

			collider.bvhNode = this;
			computeLongestAxis();
		}
        /**
		 * remove a collider from the colliders list of BVHNode object
		 * @param collider
		 */
		public void removeCollider(Collider collider) {
			colliders.remove(collider);

			computeLongestAxis();
		}
        /**
		 * romoves all the colliders from the colliders list of BVHNode object
		 */
		public void removeAllColliders() {
			colliders = new ArrayList<Collider>();
		}
        /**
		 *
		 * @return the BVHNode childs of this node
		 */
		public List<BVHNode> getchilds() {
			return childs;
		}
        
		/**
		 * gets all the collider from the child nodes & make the childs list empty
		 * @param parent
		 */
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
        /** splits the node into two parts */
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
				BVHNode child1 = new BVHNode(null, layer + 1,this);
				BVHNode child2 = new BVHNode(null, layer + 1,this);
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
        /** computes the node box left button point position & right up point position  */
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
        /**
		 * 
		 * @return the node box left button point position & right up point position
		 */
		public float[][] getMinMax() {
			float[][] minMax = { minPoint, maxPoint };
			return minMax;
		}
	}
}