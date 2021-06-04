
package com.physicsEngine;

import com.physicsEngine.components.*;
import com.physicsEngine.vectors.*;
import java.util.*;


public class GameObject{
	public Transform transform;
	public String name;
    public List<Component> components = new ArrayList<Component>();

    public GameObject(Transform transform,String name){
    	//setting up the tranform component so we can position the gameobject
    	if(transform == null)
    	transform = new Transform(new Vector2(0,0),0,new Vector2(1,1));
        else 
        this.transform = transform;
        //setting the name of the gameobject
        this.name = name;
    }
}