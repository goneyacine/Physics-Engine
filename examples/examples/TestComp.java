package examples;

import com.physicsEngine.*;
import com.physicsEngine.components.*;
import com.physicsEngine.physics.PhysicsManager;
import com.physicsEngine.components.physics.colliders.CircleCollider;
import com.physicsEngine.components.rendering.Cam;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.vectors.Vector2;
import Inputs.InputManager;
import static org.lwjgl.glfw.GLFW.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class TestComp extends Component {

    private BufferedImage img2;

    public TestComp(GameObject gameObject) {
        setGameObject(gameObject);
        setName("DD");
        try{
		img2 = ImageIO.read(new File("C:\\Users\\dtech\\Pictures\\Hello World.png"));
        }catch(Exception e){}
    }

    public void update() {

        if (InputManager.inputManager().getKey(GLFW_KEY_SPACE))
            Game.game.camera.setSize(Game.game.camera.getSize() - 1.5f);
        else if (InputManager.inputManager().getKey(GLFW_KEY_I))
            Game.game.camera.setSize(Game.game.camera.getSize() + 1.5f);

        if (InputManager.inputManager().getKey(GLFW_KEY_UP))
            gameObject().transform.position.y += .5f;
        else if (InputManager.inputManager().getKey(GLFW_KEY_DOWN))
            gameObject().transform.position.y -= .5f;

        if(InputManager.inputManager().getKeyDown(InputManager.SPACE))
         System.out.println("FPS = " + Game.game.getFrames() + " ... UPS = " + Game.game.getUpdates());

        if (InputManager.inputManager().getKey(GLFW_KEY_RIGHT))
            gameObject().transform.position.x += .5f;
        else if (InputManager.inputManager().getKey(GLFW_KEY_LEFT))
            gameObject().transform.position.x -= .5f;
           
        if(InputManager.inputManager().getKeyDown(InputManager.A)){
            GameObject gameObject = new GameObject(null,"BB");
            new SpriteRenderer(gameObject,img2);
            gameObject.transform.position.x = (float)Math.random() * 300 - 150;
            gameObject.transform.position.y = (float)Math.random() * 100 - 50;
            gameObject.transform.scale.x = 3;
            gameObject.transform.scale.y = 3;
            gameObject.addComponent(new CircleCollider(4.5f, Vector2.zero()));
            Game.game.getRunningScene().addGameObejct(gameObject);
        }

    }

}
