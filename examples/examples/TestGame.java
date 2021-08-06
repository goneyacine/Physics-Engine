package examples;


import com.physicsEngine.Game;
import com.physicsEngine.GameObject;
import com.physicsEngine.Scene;

import com.physicsEngine.components.physics.colliders.CircleCollider;
import com.physicsEngine.components.rendering.Cam;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.physics.PhysicsManager;
import com.physicsEngine.vectors.Vector2;

import java.util.List;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class TestGame {
    static BufferedImage img2 = null;

    public static void main(String[] args){
        List<GameObject> sceneObjects = new ArrayList<GameObject>();
        Scene scene = new Scene(sceneObjects, "my scene");
        List<Scene> scenes = new ArrayList<Scene>();
        scenes.add(scene);
        Game.setUp(scenes);
        GameObject camObject =  new GameObject(null, "cam");
        float[] acpectRatio = {16,9};
         new Cam(camObject,20, acpectRatio);
       BufferedImage img1 = null;

		try {
			img2 = ImageIO.read(new File("C:\\Users\\dtech\\Pictures\\Hello World.png"));
			img1 = ImageIO.read(new File("C:\\Users\\dtech\\Castle Boomer\\Assets\\Sprites\\the new style of the game\\mobile game buttons.png"));


		} catch (IOException e) {
            System.out.println(e);
        }
        
        GameObject gameObject = new GameObject(null,"BB");
        new SpriteRenderer(gameObject,img2);
        sceneObjects.add(gameObject);
        gameObject.addComponent(new TestComp(gameObject));
        GameObject gameObject1 = new GameObject(null,"hello");
        new SpriteRenderer(gameObject1,img1);
        sceneObjects.add(gameObject1);
        gameObject1.transform.position.x = 333;
        gameObject1.transform.position.y = 22;
        gameObject.addComponent(new CircleCollider(1.5f / 2, Vector2.zero()));
        PhysicsManager.physicsManager().physicsSetUp();
        Game.game.runGame();
    }
    public static BufferedImage testImage(){return img2;}
    
}

