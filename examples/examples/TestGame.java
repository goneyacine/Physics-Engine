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
         new Cam(camObject,45, acpectRatio);
       BufferedImage img = null;
		try {
			img = ImageIO.read(new File("C:\\Users\\dtech\\Pictures\\wow.png"));
		} catch (IOException e) {
		}
       for(int i = 1; i < 15;i++){
           for(int j = 1;j < 15;j++){
       GameObject gameObject = new GameObject(null,"hello");
       new SpriteRenderer(gameObject,img);
       gameObject.addComponent(new TestComp(gameObject));
       sceneObjects.add(gameObject);
       gameObject.transform.position.x = i * 25 - 100;
       gameObject.transform.position.y = j * 25 - 100;
       }
    }
    Game.game.camera.transform.zAngle = 180;
        Game.game.runGame();
    }
    
}
