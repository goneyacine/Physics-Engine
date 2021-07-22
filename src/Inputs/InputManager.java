package Inputs;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import java.util.*;

import com.physicsEngine.vectors.Vector2;


public class InputManager {

//the keys that are still pressed
private List<Integer> pressedKeys = new ArrayList<Integer>();
//the keys that are pressed down (once) 
private List<Integer> pressedDownKeys = new ArrayList<Integer>(); 
//the mouse buttons that are still pressed
private List<Integer> pressedMouseButtons = new ArrayList<Integer>();
//the mouse buttons that pressed down(once)
private List<Integer> pressedDownMouseButtons = new ArrayList<Integer>();

private Vector2 mousePosition = new Vector2(0,0);

private static InputManager inputManager;

public InputManager(long window){
    GLFWKeyCallback keyCallback = null;
    glfwSetKeyCallback(window, keyCallback  =  new GLFWKeyCallback() {
        @Override
        public void invoke (long window, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS){
        //adding this key to the pressedDownKeys list
        pressedDownKeys.add(key);
        //checking if the pressedKeys list contains this key and remove it
        int index = pressedKeys.indexOf(key);
        if(index != -1)
        pressedKeys.remove(index);
        }else if(action == GLFW_REPEAT){
         //adding this key to the pressedKeys list
         pressedKeys.add(key);
         //checking if the pressedDownKeys list contains this key and remove it
         int index = pressedDownKeys.indexOf(key);
         if(index != -1)
         pressedDownKeys.remove(index);
        }else if(action == GLFW_RELEASE){
        //checking if the pressedKeys list contains this key and remove it
        int index = pressedDownKeys.indexOf(key);
        if(index != -1)
        pressedDownKeys.remove(index);
        else{
        index = pressedKeys.indexOf(key);
        if(index != -1)
        pressedKeys.remove(index);
        }


        }
        }
    });
    GLFWMouseButtonCallback mouseCallback = null;
    glfwSetMouseButtonCallback(window, mouseCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if(action == GLFW_PRESS){
                //adding this key to the pressedDonwMouseButtons list
                pressedDownMouseButtons.add(button);
                //checking if the pressedMouseButtons list contains this key and remove it
                int index = pressedMouseButtons.indexOf(button);
                if(index != -1)
                pressedMouseButtons.remove(index);
                }else if(action == GLFW_REPEAT){
                 //adding this key to the pressedMouseButtons list
                 pressedMouseButtons.add(button);
                 //checking if the pressedDownMouseButtons list contains this key and remove it
                 int index = pressedDownMouseButtons.indexOf(button);
                 if(index != -1)
                 pressedDownMouseButtons.remove(index);
                }else if(action == GLFW_RELEASE){
                //checking if the pressedMouseButtons list contains this key and remove it
                int index = pressedDownMouseButtons.indexOf(button);
                if(index != -1)
                pressedDownMouseButtons.remove(index);
                else{
                index = pressedMouseButtons.indexOf(button);
                if(index != -1)
                pressedMouseButtons.remove(index);
                }
        }
    }
});
    GLFWCursorPosCallback posCallback  = null;
    glfwSetCursorPosCallback(window, posCallback = new GLFWCursorPosCallback() {
	@Override
	public void invoke(long window, double xpos, double ypos) {
    mousePosition.x = (float)xpos;
    mousePosition.y = (float)ypos;
	}
});
    inputManager = this;
}
/**
 * the InputManager class is responsible for handling inputs, it has info about key stats,
 * mouse buttons stats, cursor position ...
 * @return the input manager instance used by this game
 */
public static InputManager inputManager(){
    return inputManager;
}
/**
 * 
 * @param key the targer key
 * @return This will either return ture or false. true if the key is pressed down, false if it’s not.
 */
 public boolean getKeyDown(int key){
 return pressedDownKeys.contains(key);
 }
/**
 * 
 * @param key the target key
 * @return true if a key is repeatdly pressed & false if it's not
 */
 public boolean getKey(int key){
  return pressedKeys.contains(key);
 } 
 /**
  * 
  * @return the cursor position in the screen
  */
 public Vector2 mousePosition(){
     return mousePosition;
 }
}
