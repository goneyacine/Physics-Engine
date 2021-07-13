package examples;

import com.physicsEngine.Game;
import com.physicsEngine.components.*;

public class TestComp extends Component{
    public TestComp(){
        name = "test comp";
    }
    public void update(){
    float sizeChanging = .001f;

    Game.game.camera.setSize(Game.game.camera.getSize() + sizeChanging);
    
    }
}
