package examples;

import com.physicsEngine.*;
import com.physicsEngine.components.*;

import org.lwjgl.system.windows.INPUT;

import Inputs.InputManager;
import static org.lwjgl.glfw.GLFW.*;
public class TestComp extends Component{
 

    public TestComp(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public void update(){

		if(InputManager.inputManager().getKey(GLFW_KEY_SPACE))
        Game.game.camera.setSize(Game.game.camera.getSize() - 1.5f);
        else if(InputManager.inputManager().getKey(GLFW_KEY_I))
        Game.game.camera.setSize(Game.game.camera.getSize() + 1.5f);
    
        if(InputManager.inputManager().getKey(GLFW_KEY_UP))
        Game.game.camera.transform.position.y += .5f;
        else if(InputManager.inputManager().getKey(GLFW_KEY_DOWN))
        Game.game.camera.transform.position.y -= .5f;
        
        if(InputManager.inputManager().getKey(GLFW_KEY_RIGHT))
        Game.game.camera.transform.position.x += .5f;
        else if(InputManager.inputManager().getKey(GLFW_KEY_LEFT))
        Game.game.camera.transform.position.x -= .5f;

        if(InputManager.inputManager().getMouseDown(InputManager.MOUSE_BUTTON_LEFT))
        System.out.println(InputManager.inputManager().mousePosition().x + " .... " + InputManager.inputManager().mousePosition().y);
    }
}
