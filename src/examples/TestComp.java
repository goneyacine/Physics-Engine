package examples;

import com.physicsEngine.GameObject;
import com.physicsEngine.components.Component;

public class TestComp extends Component{
    
public TestComp(GameObject gameObject){
 this.gameObject = gameObject;
}
public void start(){
    System.out.println("wow test compoenent is working, this is the start method");
}
public void update(){
}
    
}
