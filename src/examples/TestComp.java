package examples;

import com.physicsEngine.GameObject;
import com.physicsEngine.components.Component;
import com.physicsEngine.vectors.Vector2;

public class TestComp extends Component{
    
public TestComp(GameObject gameObject){
 this.gameObject = gameObject;
}
public void update(){
    if(gameObject.transform.scale.x > .1f)
    gameObject.transform.scale.x -= .005f ;
    else 
    gameObject.transform.scale.x = 1f;

    if(gameObject.transform.scale.y > .1f)
    gameObject.transform.scale.y -= .005f ;
    else 
    gameObject.transform.scale.y = 1f;
    
}
    
}
