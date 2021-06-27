
package com.physicsEngine;

import com.physicsEngine.components.*;
import com.physicsEngine.vectors.*;
import java.util.*;


public class GameObject {
    public Transform transform;
    public String name;
    public List<Component> components = new ArrayList<Component>();
    public boolean hasSpriteRenderer = false;
    public GameObject(Transform transform, String name) {
        //setting up the tranform component so we can position the gameobject
        if (transform == null)
            this.transform = new Transform(new Vector2(0, 0), 0, new Vector2(1, 1),this);
        else
            this.transform = transform;
        //setting the name of the gameobject
        this.name = name;
        components.add(this.transform);
        Game.game.gameObjects.add(this);
    }
    public Component getComponent(String name){
    Component component = null;
    for(Component comp : components){
        if(comp.name.equals(name)){
        component = comp;
        break;
        }
    }
    if(component == null)
    System.out.println("Sorry, But We Can Find Componenet With Name : " + name + " In The GameObject Named :" + this.name);
    return component;
    }
}