package com.physicsEngine;

import java.util.List;

import com.physicsEngine.components.Component;

import java.util.*;
public class Scene {
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    public String name;
    
    public Scene(List<GameObject> gameObjects,String name){
     this.gameObjects = gameObjects;
     this.name = name;
    }
    /**
     * this method is used to dynamicly add gameobjects to the scene
     * @param gObject GameObject that will be added 
     */
    public void addGameObejct(GameObject gObject){
    gameObjects.add(gObject);
    }
    /**
     * 
     * @return the gameobjects list getter & setter
     */
    public List<GameObject> getGameObjects(){
    return gameObjects;
    }
    public void setGameObjects(List<GameObject> gameObjects){
    this.gameObjects = gameObjects;
    }

    /**
     * this method is used to run all the start methods of all gameobjects compoenents attached to the scene
     */
    public void start(){
      for(GameObject gameObject : gameObjects){
          for(Component comp : gameObject.components){
              comp.start();
          }
      }
      System.out.println("all start methods are finished");
    }
    /**
     * this method is used to run all the update methods of all gameobjects compoenents attached to the scene
     */
    public void update(){
        for(GameObject gameObject : gameObjects){
            for(Component comp : gameObject.components){
                comp.update();
            }
        }
    }
}
