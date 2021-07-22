package examples;

import com.physicsEngine.Game;
import com.physicsEngine.GameObject;
import com.physicsEngine.components.*;
import com.physicsEngine.components.rendering.SpriteRenderer;


public class TestComp extends Component{
    public TestComp(GameObject gameObject){
        this.gameObject = gameObject;
        name = "test comp";
    
        
        SpriteRenderer sp = (SpriteRenderer)gameObject.getComponent("Sprite Renderer");
       float[] color = {(float)Math.random(),(float)Math.random(),(float)Math.random(),1};
       sp.color = color;
    }
    public void update() {

    }

}
