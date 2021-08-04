package examples;

import com.physicsEngine.*;
import com.physicsEngine.components.*;
import com.physicsEngine.physics.PhysicsManager;

import Inputs.InputManager;
import static org.lwjgl.glfw.GLFW.*;

public class TestComp extends Component {

    public TestComp(GameObject gameObject) {
        this.gameObject = gameObject;
        name = "ffff";
    }

    public void update() {

        if (InputManager.inputManager().getKey(GLFW_KEY_SPACE))
            Game.game.camera.setSize(Game.game.camera.getSize() - 1.5f);
        else if (InputManager.inputManager().getKey(GLFW_KEY_I))
            Game.game.camera.setSize(Game.game.camera.getSize() + 1.5f);

        if (InputManager.inputManager().getKey(GLFW_KEY_UP))
            Game.game.camera.transform.position.y += .5f;
        else if (InputManager.inputManager().getKey(GLFW_KEY_DOWN))
            Game.game.camera.transform.position.y -= .5f;

        if(InputManager.inputManager().getKeyDown(InputManager.SPACE))
         System.out.println("FPS = " + Game.game.getFrames() + " ... UPS = " + Game.game.getUpdates());

        if (InputManager.inputManager().getKey(GLFW_KEY_RIGHT))
            Game.game.camera.transform.position.x += .5f;
        else if (InputManager.inputManager().getKey(GLFW_KEY_LEFT))
            Game.game.camera.transform.position.x -= .5f;

        if (InputManager.inputManager().getMouseDown(InputManager.MOUSE_BUTTON_LEFT)) {
            for (GameObject object : Game.game.getRunningScene().getGameObjects()) {
                object.transform.position.x += Math.random() * 8 - 4;
                object.transform.position.y += Math.random() * 8 - 4;
                object.spriteRenderer().color[0] = 0;
            }
           
        }

    }

}
