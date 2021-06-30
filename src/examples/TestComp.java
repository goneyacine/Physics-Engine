package examples;

import com.physicsEngine.GameObject;
import com.physicsEngine.components.Component;
import com.physicsEngine.vectors.Vector2;

public class TestComp extends Component{
    
public TestComp(GameObject gameObject){
 this.gameObject = gameObject;
 gameObject.transform.position.x = 5;
 gameObject.transform.position.y = 8;
}
public void update(){

    
}
    
}
