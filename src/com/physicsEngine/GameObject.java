
package com.physicsEngine;

import com.physicsEngine.components.*;
import com.physicsEngine.vectors.*;
import java.util.*;


public class GameObject {
    public Transform transform;
    public String name;
    private List<Component> components = new ArrayList<Component>();
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

/**
 * adds a component to a gameObject, if the gameObject already contains a component with the same type the component will not be added
 * @param component
 */
  public void addComponent(Component component){
      //checking the the component already exits, if it's then don't add that component
      for(Component comp : components)
          if(comp.name.equals(component.name)){
          System.err.println("Can't Added A Component To " + name + " GameObject Because It Already Contains A Component With The Same Type");
          return;
          }

      components.add(component);
  } 

   /**
   * removes component from gameObject 
   * @param comp component will be removed
   */
  public void removeComponent(Component comp){
     if(comp.name.equals("Sprite Renderer"))
     Game.game.spriteRenderers.remove(comp);
     
     components.remove(comp);

  }
  /**
   * 
   * @return all the components attached to the gameobject as List
   */
  public List<Component> getAllComponents(){
      return components;
  }
}