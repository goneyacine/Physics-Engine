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

  /**
   *
   * @param gameObject parent gameObject
   * @param size the view size of the camera
   * @param resolution the screen resolution
   */
  public Cam(GameObject gameObject, float size, float[] acpectRatio) {
    this.gameObject =  gameObject;
    this.transform = gameObject.transform;
    this.acpectRatio = acpectRatio;
    this.size = size;

    Game.game.camera = this;

  }
  /**
   * sets the acpect ratio of camera view space
   * @param acpectRatio camera acpect ratio
   */
  public void setAcpectRatio(float[] acpectRatio){
    this.acpectRatio = acpectRatio;
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
  }
}