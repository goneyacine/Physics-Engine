package examples;

import com.physicsEngine.GameObject;
import com.physicsEngine.components.*;

public class TestComp extends Component{
    private float angle = 0;
    public TestComp(GameObject gameObject){
        this.gameObject = gameObject;
        name = "test comp";
    }
    public void update(){
   // gameObject.transform.zAngle += 2f;
    
    }
}
