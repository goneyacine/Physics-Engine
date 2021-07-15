package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.*;
import com.physicsEngine.vectors.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer extends Component {
  
  /** the parent gameObject that this component is attached to it */
  public GameObject gameObject;
  /** the transform component of the parent gameObject */
  public Transform transform;
  /** the final rendered sprite(image) after apllying : scale,rotation & color */
  private BufferedImage texture;
  /** the color applyed to the rendered image */
  public float[] color = {0.8f,0,0,1};
  /** number of pixels in one world unit */
  private int pixelsPerUnit = 100;
  /** the dimensions of that sprite renderer in world space (or the dimensions of the space token by that sprite in world space) */
  private Vector2 worldSpaceSize;

  public SpriteRenderer(GameObject gameObject, BufferedImage sprite) {
    name = "Sprite Renderer";
    this.gameObject = gameObject;
    this.transform = gameObject.transform;
    if(sprite != null)
    Game.game.spriteRenderers.add(this);
    gameObject.addComponent(this);
    gameObject.hasSpriteRenderer = true;
    
    this.texture = sprite;
    comupteWorldSpaceSize();
  }

  public int getPixelsPerUnit() {
    return pixelsPerUnit;
  }
  public void setPixelsPerUnit(int pixelsPerUnit) {
    this.pixelsPerUnit = pixelsPerUnit;
    comupteWorldSpaceSize();
  }
  /**
   * @return the dimension of the space token by the rendered sprite
   */
  public Vector2 getWorldSpaceSize() {
    return worldSpaceSize;
  }
  /** computes the dimension of the space token by the rendered sprite */
  public void comupteWorldSpaceSize() {
    if(texture != null)
    worldSpaceSize = new Vector2(texture.getWidth() / pixelsPerUnit, texture.getHeight()  / pixelsPerUnit);
    else 
    worldSpaceSize = new Vector2(0,0);
  }

  public void setTexture(BufferedImage texture){
    this.texture = texture;
    if(texture == null)
    Game.game.spriteRenderers.remove(this);
    
    comupteWorldSpaceSize();
  }
  public BufferedImage getTexture(){
    return texture;
  }


  /**
   * 
   * @return the an array of world space positions of each vertex (these positions are effected by the object rotation)
   */
  public float[] getVertices(){
    float[] vertices = new float[24];

    float[] finalWorldSize = {(float)Math.sqrt(getWorldSpaceSize().x * getWorldSpaceSize().x + getWorldSpaceSize().x * getWorldSpaceSize().x ) * transform.scale.x 
    ,(float)Math.sqrt(getWorldSpaceSize().y * getWorldSpaceSize().y + getWorldSpaceSize().y * getWorldSpaceSize().y ) * transform.scale.y};
    


   


    //this is the angle made by the line between the camera center & this object
     float objectAngleAroundCam  = (float)Math.atan2(transform.position.y / Vector2.distance(transform.position, Game.game.camera.transform.position), 
     transform.position.x / Vector2.distance(transform.position, Game.game.camera.transform.position));
    // here we rotate the position of this object in the oposite of the camera rotation
   float[] finalPos = {(float)Math.cos(Math.toRadians(Math.toDegrees(objectAngleAroundCam) - Game.game.camera.gameObject.transform.zAngle)) * Vector2.distance(transform.position, Game.game.camera.transform.position), 
    (float)Math.sin(Math.toRadians(Math.toDegrees(objectAngleAroundCam) - Game.game.camera.gameObject.transform.zAngle)) * Vector2.distance(transform.position, Game.game.camera.transform.position)};

    //setting first vertex data
    vertices[0] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 45 - Game.game.camera.transform.zAngle)) + finalPos[0]);
    vertices[1] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 45 - Game.game.camera.transform.zAngle)) + finalPos[1]);
    vertices[2] = color[0];
    vertices[3] = color[1];
    vertices[4] = color[2];
    vertices[5] = color[3];

    //setting the second vertex data
    vertices[6] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 135- Game.game.camera.transform.zAngle)) + finalPos[0]);
    vertices[7] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 135- Game.game.camera.transform.zAngle)) + finalPos[1]);
    vertices[8] = color[0];
    vertices[9] = color[1];
    vertices[10] = color[2];
    vertices[11] = color[3];
    
    //setting the third vertex data
    vertices[12] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 225 - Game.game.camera.transform.zAngle)) + finalPos[0]);
    vertices[13] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 225 - Game.game.camera.transform.zAngle )) + finalPos[1]);
    vertices[14] = color[0];
    vertices[15] = color[1];
    vertices[16] = color[2];
    vertices[17] = color[3];
    
    //setting the fourth vetex data
    vertices[18] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 315 - Game.game.camera.transform.zAngle)) + finalPos[0]);
    vertices[19] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 315 - Game.game.camera.transform.zAngle)) + finalPos[1]);
    vertices[20] = color[0];
    vertices[21] = color[1];
    vertices[22] = color[2];
    vertices[23] = color[3];

    return vertices;
  }
  public int[] getIndices(){
    int[] indices = {
      0,1,2,
      2,3,0
    };

    return indices;
  }
    /*
  public void renderScaledSprite() {
    int newSpriteXSize = (int)(originalSize.x * gameObject.transform.scale.x);
    int newSpriteYSize = (int)(originalSize.y * gameObject.transform.scale.y);
    int[][] renderedImage = new int[newSpriteXSize][newSpriteYSize];
    int x_ratio = (int)((originalSprite.getWidth() << 16) / newSpriteXSize) + 1;
    int y_ratio = (int)((originalSprite.getHeight() << 16) / newSpriteYSize) + 1;
    int x2, y2;

    for (int i = 0; i < newSpriteYSize; i++) {
      for (int j = 0; j < newSpriteXSize; j++) {
        x2 = ((j * x_ratio) >> 16) ;
        y2 = ((i * y_ratio) >> 16) ;
        renderedImage[j][i]  = originalSprite.getRGB(x2, y2);
      }
    }
    this.notRotatedButScaledSprite = renderedImage;
    if (transform.zAngle != 0)
      renderRotatedSprite();
    else
      sprite = renderedImage;

  } */
}