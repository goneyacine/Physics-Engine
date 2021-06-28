package examples;

import com.physicsEngine.GameObject;
import com.physicsEngine.components.Component;
import com.physicsEngine.vectors.Vector2;

public class TestComp extends Component{
    
public TestComp(GameObject gameObject){
 this.gameObject = gameObject;
}
public void update(){
    gameObject.transform.scale.x = .5f;
}
    
}
