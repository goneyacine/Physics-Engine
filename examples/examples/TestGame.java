package examples;


import com.physicsEngine.Game;
import com.physicsEngine.GameObject;
import com.physicsEngine.Scene;
import com.physicsEngine.components.rendering.Cam;
import com.physicsEngine.components.rendering.SpriteRenderer;
import java.util.List;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class TestGame {
    public static void main(String[] args){
        List<GameObject> sceneObjects = new ArrayList<GameObject>();
        Scene scene = new Scene(sceneObjects, "my scene");
        List<Scene> scenes = new ArrayList<Scene>();
        scenes.add(scene);
        Game.setUp(scenes);
        GameObject camObject =  new GameObject(null, "cam");
        float[] acpectRatio = {16,9};
         new Cam(camObject,20, acpectRatio);
       BufferedImage img2 = null;

		try {
			img2 = ImageIO.read(new File("C:\\Users\\dtech\\Castle Boomer\\Assets\\Sprites\\the new style of the game\\the new green background layers\\jungle forgound.png"));

		} catch (IOException e) {
            System.out.println(e);
        }
        GameObject gameObject = new GameObject(null,"hello");
        new SpriteRenderer(gameObject,img2);
        sceneObjects.add(gameObject);
  
        Game.game.runGame();
    }
    
}
