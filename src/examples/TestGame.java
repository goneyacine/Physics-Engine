package examples;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.physicsEngine.Game;
import com.physicsEngine.GameObject;
import com.physicsEngine.Scene;
import com.physicsEngine.components.rendering.Cam;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.vectors.Vector2;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;
public class TestGame {
    public static void main(String[] args){
        List<GameObject> sceneObjects = new ArrayList<GameObject>();
        Scene scene = new Scene(sceneObjects, "my scene");
        List<Scene> scenes = new ArrayList<Scene>();
        scenes.add(scene);
        Game.setUp(scenes);
        GameObject camObject =  new GameObject(null, "cam");
		Cam cam = new Cam(camObject, new Vector2(16, 9), new Vector2(1920, 1080));
        BufferedImage img = null;
		try {
			img = ImageIO.read(new File("C:\\Users\\dtech\\Pictures\\wow.png"));
		} catch (IOException e) {
		}

        GameObject gameObject = new GameObject(null,"wow I'm gameobject");
        SpriteRenderer spriteRenderer = new SpriteRenderer(gameObject,img);
        TestComp comp = new TestComp(gameObject);
        gameObject.components.add(comp);

        sceneObjects.add(gameObject);
        Game.game.runGame();
    }
    
}
