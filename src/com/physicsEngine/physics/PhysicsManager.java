
package com.physicsEngine.physics;

import java.util.*;
import com.physicsEngine.components.physics.colliders.*;

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

	public PhysicsManager() {
		physicsManager = this;
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

	public void devideBVHNode(BVHNode node) {

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

		/**
		 * @implNote if the childs list is null, then it'll not be set as a childs list
		 *           of this node, the same thing with the colliders list
		 * @param colliders
		 * @param childs
		 */
		public BVHNode(List<Collider> colliders, List<BVHNode> childs) {
			if (colliders != null)
				this.colliders = colliders;
			if (childs != null)
				this.childs = childs;
		}

		public List<Collider> getColliders() {
			return colliders;
		}

		public void addCollider(Collider collider) {
			if (!colliders.contains(collider))
				colliders.add(collider);
		}

		public void removeCollider(Collider collider) {
			colliders.remove(collider);
		}

		public void removeAllColliders() {
			colliders = new ArrayList<Collider>();
		}

		public List<BVHNode> getchilds() {
			return childs;
		}
        
		public void scrapeColliders(BVHNode parent){
			for(BVHNode child : parent.getchilds()){
                if(child.colliders.size() == 0)
				scrapeColliders(parent);
				else 
				parent.colliders.addAll(child.getColliders());

			}
		parent.childs = new ArrayList<BVHNode>();
		}

		public void splitCollidersList(){
            if(colliders.size() == 0 ){
			  if(childs.size() == 0)
			  return;
			  else
			  scrapeColliders(this);
			}

			sortCollidersList();
			if(colliders.size() > 2)
			{
			 childs = new ArrayList<BVHNode>();
			BVHNode child1 = new BVHNode(null,null);
			BVHNode child2 = new BVHNode(null,null);
			for(int i = 0;i < colliders.size();i++){
				if(i + 1 < colliders.size() / 2)
				child1.addCollider(colliders.get(i));
				else
				child2.addCollider(colliders.get(i));
			}
			childs.add(child1);
			childs.add(child2);
			}
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
			float pivot = (list.get(high).getMinMax()[0][longestAxis] + list.get(high).getMinMax()[1][longestAxis]) / 2;


			int i = (low - 1);

			for (int j = low; j <= high - 1; j++) {

				// If current element is smaller
				// than the pivot
				if ((list.get(j).getMinMax()[0][longestAxis] + list.get(j).getMinMax()[1][longestAxis]) / 2 < pivot) {

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
			if (!childs.contains(child))
				childs.add(child);
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
	}
}