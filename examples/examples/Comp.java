package examples;

import com.physicsEngine.*;
import com.physicsEngine.components.*;
import com.physicsEngine.vectors.Vector2;


public class Comp extends Component{

 private Vector2 target = null;
 private Vector2 offset = null;
 public Comp(){
     setName("jjfjf");
 }
 public void update(){
    gameObject().spriteRenderer().color[0] = 0;
    if(target == null){
    target = new Vector2((float)Math.random() * 30 - 15,(float)Math.random() * 20 - 10);
    offset = new Vector2((target.x - gameObject().transform.position.x) / 100,(target.y - gameObject().transform.position.y) / 100);
    }
    else {
    if(gameObject().transform.position.x != target.x)
    gameObject().transform.position.x += offset.x;

    if(gameObject().transform.position.y != target.y)
    gameObject().transform.position.y += offset.y;

     if(gameObject().transform.position.x == target.x && gameObject().transform.position.y == target.y)
     target = null;
    }
 }
    
}
