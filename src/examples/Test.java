package examples;

import com.physicsEngine.*;
import com.physicsEngine.components.*;
import com.physicsEngine.components.rendering.*;
import com.physicsEngine.vectors.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;

public class Test {

	public static void main(String[] args) {
		Game.setUp();
		GameObject obj = new GameObject(new Transform(new Vector2(0, 0), 180, new Vector2(1, 1)), "obj");
		GameObject camObject =  new GameObject(null, "cam");
		Cam cam = new Cam(camObject, new Vector2(10, 90), new Vector2(1920, 1080));
		//obj.transform.zAngle = 30;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("E:\\pictures\\Saved Pictures\\moon.PNG"));
		} catch (IOException e) {
		}
		SpriteRenderer sr = new SpriteRenderer(obj, img);

		Game.game.runGame();
	}

}