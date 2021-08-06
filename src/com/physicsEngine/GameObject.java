
package com.physicsEngine;

import com.physicsEngine.components.*;
import com.physicsEngine.components.physics.colliders.Collider;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.physics.PhysicsManager;
import com.physicsEngine.physics.PhysicsManager.BVHNode;
import com.physicsEngine.vectors.*;
import java.util.*;

public class GameObject {
    public Transform transform;
    public String name;
    private List<Component> components = new ArrayList<Component>();
    public boolean hasSpriteRenderer = false;
    private SpriteRenderer spriteRenderer;
    public boolean hasCollider = false;
    public Collider collider;

    public GameObject(Transform transform, String name) {
        // setting up the tranform component so we can position the gameobject
        if (transform == null)
            this.transform = new Transform(new Vector2(0, 0), 0, new Vector2(1, 1), this);
        else
            this.transform = transform;
        // setting the name of the gameobject
        this.name = name;
        components.add(this.transform);
        Game.game.gameObjects.add(this);
    }

    public Component getComponent(String name) {
        Component component = null;
        for (Component comp : components) {
            if (comp.getName().equals(name)) {
                component = comp;
                break;
            }
        }
        if (component == null)
            System.out.println(
                    "Sorry, But We Can Find Componenet With Name : " + name + " In The GameObject Named :" + this.name);
        return component;
    }

    /**
     * adds a component to a gameObject, if the gameObject already contains a
     * component with the same type the component will not be added
     * 
     * @param component
     */
    public void addComponent(Component component) {
        // checking the the component already exits, if it's then don't add that
        // component
        for (Component comp : components)
            if (comp.getName().equals(component.getName())) {
                System.err.println("Can't Added A Component To " + name
                        + " GameObject Because It Already Contains A Component With The Same Type");
                return;
            }
        component.setGameObject(this);

        if (component.getName().equals("Sprite Renderer")) {
            spriteRenderer = (SpriteRenderer) component;
            hasSpriteRenderer = true;
        } else if (component.getName().equals("Circle Collider") || component.getName().equals("Box Collider")) {
            collider = (Collider) component;
            collider.computeMinMax();
            hasCollider = true;
            if (Game.game.getRunningScene().getGameObjects().contains(this))
                addToBVHTree();
        }
        components.add(component);
        component.onEnable();
    }

    /**
     * 
     * @return the spriteRenderer componenet of this object & null if there no
     *         spriteRenderer
     */
    public SpriteRenderer spriteRenderer() {
        return spriteRenderer;
    }

    /**
     * removes component from gameObject
     * 
     * @param comp component will be removed
     */
    public void removeComponent(Component comp) {
        if (comp.getName().equals("Sprite Renderer"))
            Game.game.spriteRenderers.remove(comp);

        components.remove(comp);

        if (comp.getName().equals("Box Collider") || comp.getName().equals("Circle Collider")) {
            ((Collider) comp).bvhNode.removeCollider((Collider) comp);
            ((Collider) comp).bvhNode.computeMinMax();
            ((Collider) comp).bvhNode = null;
        }

        if (getComponent("Box Collider") == null && getComponent("Circle Collider") == null) {
            hasCollider = false;
            collider = null;
        }

    }

    /**
     * 
     * @return all the components attached to the gameobject as List
     */
    public List<Component> getAllComponents() {
        return components;
    }

    public void onEnable() {
        for (Component comp : components)
            comp.onEnable();
    }

    public void onDisbale() {
        for (Component comp : components)
            comp.onDisbale();
    }

    public void onDestroy() {
        for (Component comp : components) {
            comp.onDestroy();
            comp.onDisbale();
        }
    }

    /**
     * this method is called when this gameObject is spawed for the first time or a
     * new collider is added to it we it's enabled to add it's collider to the BVH
     * tree
     */
    public void addToBVHTree() {
        if (collider == null) {
            return;
        }

        checkInterception(PhysicsManager.physicsManager().bvhTree.getRoot());
    }

    private void checkInterception(BVHNode node) {
        if (transform.position.x >= node.getMinMax()[0][0] && transform.position.x <= node.getMinMax()[1][0]
                && transform.position.y >= node.getMinMax()[0][1] && transform.position.y <= node.getMinMax()[1][1]) {
            BVHNode foundedNode = null;
            for (BVHNode n : node.getchilds()) {
                if (transform.position.x >= n.getMinMax()[0][0] && transform.position.x <= n.getMinMax()[1][0]
                        && transform.position.y >= n.getMinMax()[0][1] && transform.position.y <= n.getMinMax()[1][1]) {
                    foundedNode = n;
                    break;
                }
            }
            if (foundedNode != null)
                checkInterception(foundedNode);
            else {
                node.addCollider(collider);
                node.splitCollidersList();
                return;
            }
        } else {
            if (node.equals(PhysicsManager.physicsManager().bvhTree.getRoot())){
                node.addCollider(collider);
                node.splitCollidersList();
            }
        }
    }
}