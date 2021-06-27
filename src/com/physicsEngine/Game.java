package com.physicsEngine;

import com.physicsEngine.components.*;
import com.physicsEngine.components.rendering.*;
import com.physicsEngine.customExceptions.NoSceneAttachedToGameException;
import com.physicsEngine.rendering.*;
import java.util.List;
import java.util.*;

public class Game implements Runnable {
	//there should be only one game instance
	public static Game game;
	//the list of all gameobejcts that are in the scene (this should be included on the scene class but i'll work on that later)
	public List<GameObject> gameObjects = new ArrayList<GameObject>();

	public List<SpriteRenderer> spriteRenderers = new ArrayList<SpriteRenderer>();

	public Cam camera;

	private Displayer displayer = new Displayer();

	private Thread thread;

	private List<Scene> scenes = new ArrayList<Scene>();
	private Scene runningScene;

	public Game(List<Scene> scenes)
	{
     //checking if the scenes list isn't empty, if it is, then throw NoSceneAttachedToGameException
     if(scenes == null || scenes.size() == 0){
		try {
			throw new NoSceneAttachedToGameException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 this.scenes = scenes;
	 runningScene = scenes.get(0);
	}
	public static void setUp(List<Scene> scenes) {
		game = new Game(scenes);
	}
	private synchronized void start() {
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		display();
		try {
			Thread.sleep((long)(1 / 60 * 1000));
		} catch (Exception e) {System.out.println(e);}
		run();
	}

	//I have to change this later
	public void display() {
		displayer.display(camera);
	}
	public void runGame() {
		start();
		run();
	}

	//scenes list getter & setter
	public List<Scene> getScenes(){
		return scenes;
	}
	public void setScenes(List<Scene> scenes){
		this.scenes = scenes;
	}
	//use this to add new scene to the game
	public void addScene(Scene scene){
		scenes.add(scene);
	}
	
	//runnig scene getter & setter
	public Scene getRunningScene(){
		return runningScene;
	}
	public void setRunningScene(Scene runningScene){
		this.runningScene = runningScene;
	}
	//this method is used to set the running scene by it's index on the scenes list
	public void setRunningSceneByIndex(int index){
    runningScene = scenes.get(index);
	}
	//this method is used to set the running scene by it's name on the scenes list 
	public void setRunningSceneByName(String name){
		for(Scene scene : scenes){
			if(scene.name.equals(name))
			runningScene = scene;
			else 
			continue;
		}
	}
}