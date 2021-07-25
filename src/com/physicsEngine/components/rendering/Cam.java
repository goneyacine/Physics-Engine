package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.*;

public class Cam extends Component {

  /** the size is a vector that contains the x & y size of the camera view in units */
  private float size;
  /** the parent gameObject transform component */
  public Transform transform;
  
  /** the acpect ratio of the camera veiw */
  public float[] acpectRatio;
  
  private float viewSpace;

  /**
   *
   * @param gameObject parent gameObject
   * @param size the view size of the camera
   * @param acpectRatio the camera view acpectRatio
   */
  public Cam(GameObject gameObject, float size, float[] acpectRatio) {
    this.gameObject =  gameObject;
    this.transform = gameObject.transform;
    this.acpectRatio = acpectRatio;
    this.size = size;
    Game.game.camera = this;
    computeViewSpace();
    
  }
  /**
   * sets the acpect ratio of camera view space
   * @param acpectRatio camera acpect ratio
   */
  public void setAcpectRatio(float[] acpectRatio){
    this.acpectRatio = acpectRatio;
    computeViewSpace();
  }
  /**
   * 
   * @return camera acpect ratio
   */
  public float[] getAcpectRatio(){
    return  acpectRatio;
  }

  /**
   * @return camera size
   */
  public float getSize() {
    return size;
  }
  /**
   * sets the size of the camera
   * @param size the new camera size
   */
  public void setSize(float size) {
    this.size = size;
    computeViewSpace();
  }

  private void computeViewSpace(){
  viewSpace = (float)Math.sqrt(Math.pow(Game.game.camera.getSize() * Game.game.camera.getAcpectRatio()[0],2) + Game.game.camera.getSize() * Math.pow(Game.game.camera.getAcpectRatio()[1],2));
  }
  /**
   * 
   * @return the raduis of the circle surounding the view rectangle 
   */
  public float getViewSpace(){
    return viewSpace;
  }
}