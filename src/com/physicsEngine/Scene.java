package com.physicsEngine;

import java.util.List;

import com.physicsEngine.components.Component;
import com.physicsEngine.components.rendering.SpriteRenderer;

import java.util.*;

public class Scene {
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    public String name;
    //this list contains all the gameObjects that should be added to gameObjects list
    public List<GameObject> gameObjectsShouldAdd = new ArrayList<GameObject>();
    //this list contains all the gameObjects that should be removed to gameObjects list
    public List<GameObject> gameObjectsShouldRemove= new ArrayList<GameObject>();

    public Scene(List<GameObject> gameObjects, String name) {
        this.gameObjects = gameObjects;
        this.name = name;
    }

    /**
     * this method is used to dynamicly add gameobjects to the scene
     * 
     * @param gObject GameObject that will be added
     */
    public void addGameObejct(GameObject gObject) {
        gameObjectsShouldAdd.add(gObject); 
        gObject.onEnable();
        if (gObject.hasSpriteRenderer)
            Game.game.addSpriteRenderer((SpriteRenderer) gObject.getComponent("Sprite Renderer"));

            gObject.addToBVHTree();
    }

    /**
     * 
     * @return the gameobjects list getter & setter
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * this method is used to run all the start methods of all gameobjects
     * compoenents attached to the scene
     */
    public void start() {
        for (GameObject gameObject : gameObjects) {
            for (Component comp : gameObject.getAllComponents()) {
                comp.start();
            }
        }
        System.out.println("all start methods are finished");
    }

    /**
     * this method is used to run all the update methods of all gameobjects
     * compoenents attached to the scene
     */
    public void update() {
        try {
            for (GameObject gameObject : gameObjects) {
                for (Component comp : gameObject.getAllComponents()) {
                    comp.update();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }

    /**
     * destroy gameObject from the target scene & call the onDestoy function of that
     * object if target scene is the running scene
     * 
     * @param gameObject
     */
    public void destroyGameObject(GameObject gameObject) {
        if (gameObjectsShouldRemove.add(gameObject) && Game.game.getRunningScene().equals(this))
            gameObject.onDestroy();

        if (gameObject.spriteRenderer() != null)
            Game.game.removeSpriteRenderer(gameObject.spriteRenderer());
    }
}
