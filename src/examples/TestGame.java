package examples;


import com.physicsEngine.Game;
import com.physicsEngine.GameObject;
import com.physicsEngine.Scene;

import java.util.List;
import java.util.*;
public class TestGame {
    public static void main(String[] args){
        List<GameObject> sceneObjects = new ArrayList<GameObject>();
        Scene scene = new Scene(sceneObjects, "my scene");
        List<Scene> scenes = new ArrayList<Scene>();
        scenes.add(scene);
        Game.setUp(scenes);
       // GameObject camObject =  new GameObject(null, "cam");
        //float
		//Cam cam = new Cam(camObject,15, new Vector2(1920, 1080));
       // BufferedImage img = null;
	//	try {
	//		img = ImageIO.read(new File("C:\\Users\\dtech\\Pictures\\wow.png"));
	//	} catch (IOException e) {
	//	}

      //  GameObject gameObject = new GameObject(null,"wow I'm gameobject");
      //  SpriteRenderer spriteRenderer = new SpriteRenderer(gameObject,img);
      //  TestComp comp = new TestComp(gameObject);
       // gameObject.components.add(comp);

        //sceneObjects.add(gameObject);
        Game.game.runGame();
    }
    
}
