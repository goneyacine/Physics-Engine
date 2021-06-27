package com.physicsEngine;

import java.util.List;
import java.util.*;
public class Scene {
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    public String name;

    public Scene(List<GameObject> gameObjects,String name){
     this.gameObjects = gameObjects;
     this.name = name;
    }
    //this method is used to dynamicly add gameobjects to the scene
    public void addGameObejct(GameObject gObject){
    gameObjects.add(gObject);
    }
    //the gameobjects list getter & setter
    public List<GameObject> getGameObjects(){
    return gameObjects;
    }
    public void setGameObjects(List<GameObject> gameObjects){
    this.gameObjects = gameObjects;
    }
}
