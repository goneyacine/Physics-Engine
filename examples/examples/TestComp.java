package examples;

import com.physicsEngine.Game;
import com.physicsEngine.GameObject;
import com.physicsEngine.components.*;

import Inputs.InputManager;
import static org.lwjgl.glfw.GLFW.*;

public class TestComp extends Component{
    public TestComp(GameObject gameObject){
        this.gameObject = gameObject;
        name = "test comp";
    
    }

    public void update(){
    if(InputManager.inputManager().getKey(GLFW_KEY_SPACE))
    Game.game.camera.setSize(Game.game.camera.getSize() - 1.5f);
    }

}
