package com.physicsEngine;

import com.physicsEngine.components.*;
import com.physicsEngine.components.rendering.*;
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

    public static void setUp(){
    game = new Game();
    }
    private synchronized void start() {
		thread = new Thread(this,"Display");
		thread.start();
	}

	private synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void run(){  
    display();
     try{
    Thread.sleep((long)(1 / 60 * 1000));
    }catch(Exception e){System.out.println(e);}
    run();
	}
    
    //I have to change this later
    public void display(){
    displayer.display(camera);
    }
    public void runGame(){
        start();
        run();
    }
}