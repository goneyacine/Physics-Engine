package com.physicsEngine;

import java.util.ArrayList;
import java.util.List;
import com.physicsEngine.components.rendering.Cam;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.customExceptions.NoSceneAttachedToGameException;
import com.physicsEngine.physics.PhysicsManager;
import com.physicsEngine.rendering.*;
import Inputs.InputManager;

public class Game implements Runnable {
	// there should be only one game instance
	public static Game game;
	// the list of all gameobejcts that are in the scene (this should be included on
	// the scene class but i'll work on that later)
	public List<GameObject> gameObjects = new ArrayList<GameObject>();
	private int fps;
	private int updates;
	private long lastTime;
	private long timer;
	private final double ns = 1000000000 / 60;
	private double delta = 0;
	public List<SpriteRenderer> spriteRenderers = new ArrayList<SpriteRenderer>();
	private int ups;
	private int frames;
	public Cam camera;
	private Renderer renderer = new Renderer();

	private List<Scene> scenes = new ArrayList<Scene>();
	private Scene runningScene;
	/*
	 * this used to check if we should render new frame or not because nothing has
	 * been changed, this should help us improving the preformence
	 */
	public boolean shouldRenderNewFrame = true;

	public Game(List<Scene> scenes) {
		// checking if the scenes list isn't empty, if it is, then throw
		// NoSceneAttachedToGameException
		if (scenes == null || scenes.size() == 0) {
			try {
				throw new NoSceneAttachedToGameException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.scenes = scenes;
		runningScene = scenes.get(0);
		TexturesManager texturesManager = new TexturesManager();
		PhysicsManager physicsManager = new PhysicsManager();
		new InputManager(renderer.getWindow());
	}

	public static void setUp(List<Scene> scenes) {
		game = new Game(scenes);
	}

	public void run() {
		while (true) {
			long now = System.nanoTime();
		delta = (now - lastTime) / ns;
		lastTime = System.nanoTime();
			if (delta >= 1) {
				runningScene.update();
				updates++;
				delta--;
				PhysicsManager.physicsManager().physicsUpdate();
			}
			display();
			fps++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				ups = updates;
				frames = fps;
				updates = 0;
				fps = 0;
			}
		}
	}

	// I have to change this later
	public void display() {
		renderer.render(spriteRenderers);
	}

	public void runGame() {
		lastTime = System.nanoTime();
		timer = System.currentTimeMillis();
		run();
	}

	// scenes list getter & setter
	public List<Scene> getScenes() {
		return scenes;
	}

	public void setScenes(List<Scene> scenes) {
		this.scenes = scenes;
	}

	// use this to add new scene to the game
	public void addScene(Scene scene) {
		scenes.add(scene);
	}

	// runnig scene getter & setter
	public Scene getRunningScene() {
		return runningScene;
	}

	public void setRunningScene(Scene runningScene) {

		for (GameObject gameObject : this.runningScene.getGameObjects())
			gameObject.onDestroy();

		this.runningScene = runningScene;
		spriteRenderers = new ArrayList<SpriteRenderer>();
		// reseting the spriteRenderers list and calling onEnable methods of each object
		for (GameObject gameObject : runningScene.getGameObjects()) {
			gameObject.onEnable();
			if (gameObject.hasSpriteRenderer)
				spriteRenderers.add((SpriteRenderer) gameObject.getComponent("Sprite Renderer"));
		}
		runningScene.start();
		PhysicsManager.physicsManager().physicsSetUp();
	}

	// this method is used to set the running scene by it's index on the scenes list
	public void setRunningSceneByIndex(int index) {
		runningScene = scenes.get(index);
	}

	// this method is used to set the running scene by it's name on the scenes list
	public void setRunningSceneByName(String name) {
		for (Scene scene : scenes) {
			if (scene.name.equals(name))
				runningScene = scene;
			else
				continue;
		}
	}

	/**
	 * 
	 * @return rendering frame rate
	 */
	public int getFrames() {
		return frames;
	}

	/**
	 * 
	 * @return update function rate
	 */
	public int getUpdates() {
		return ups;
	}

	/**
	 * adds a spriteRenderer to the spriteRenderers list so it'll be rendered
	 * 
	 * @param spriteRenderer spriteRenderer object that will be added
	 * @implNote if the spriteRenderer object is already in the list it'll not be
	 *           added
	 */
	public void addSpriteRenderer(SpriteRenderer spriteRenderer) {
		if (!spriteRenderers.contains(spriteRenderer))
			spriteRenderers.add(spriteRenderer);
	}

	/**
	 * removes a spriteRenderer from the spriteRenderers list so it'll not be
	 * rendered
	 * 
	 * @param spriteRenderer spriteRenderer object that will be added
	 */
	public void removeSpriteRenderer(SpriteRenderer spriteRenderer) {
		spriteRenderers.remove(spriteRenderer);
	}
}