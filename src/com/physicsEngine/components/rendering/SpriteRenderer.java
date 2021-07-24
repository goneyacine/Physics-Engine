package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.*;
import com.physicsEngine.vectors.*;

import static org.lwjgl.glfw.GLFW.*;

import Inputs.InputManager;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.physicsEngine.rendering.*;

public class SpriteRenderer extends Component {
  
  /** the parent gameObject that this component is attached to it */
  public GameObject gameObject;
  /** the transform component of the parent gameObject */
  public Transform transform;
  /** the id of the used texutre */
  private int textureID = -1;

  private BufferedImage sprite;
  /** the color applyed to the rendered image */
  public float[] color = {0,0,0,1};
  /** number of pixels in one world unit */
  private int pixelsPerUnit = 100;
  /** the dimensions of that sprite renderer in world space (or the dimensions of the space token by that sprite in world space) */
  private Vector2 worldSpaceSize;

  public SpriteRenderer(GameObject gameObject, BufferedImage sprite) {
    name = "Sprite Renderer";
    this.gameObject = gameObject;
    this.transform = gameObject.transform;
    Game.game.spriteRenderers.add(this);
    gameObject.addComponent(this);
    gameObject.hasSpriteRenderer = true;
    setTexture(sprite);
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
    if(sprite != null)
    worldSpaceSize = new Vector2(sprite.getWidth() / pixelsPerUnit, sprite.getHeight()  / pixelsPerUnit);
    else 
    worldSpaceSize = new Vector2(0,0);
  }

  public void setTexture(BufferedImage image){
    this.sprite = image;

    if(textureID != -1)
    TexturesManager.texturesManager().getAlTextures().get(TexturesManager.texturesManager().getTexture(this.textureID)).removeSpriteRenderer();

    textureID = -1;
    //try to find a texture that has the same image with this if there isn't we create new one
    for(Texture tex : TexturesManager.texturesManager().getAlTextures()){
       if(tex.getImage().getHeight() != image.getHeight() || tex.getImage().getWidth() != image.getWidth())
       continue;
       else 
           if(tex.getImage().equals(image))
           textureID = tex.getID();
    }
    if(textureID == -1)
    textureID = TexturesManager.texturesManager().addTexture(image);
    TexturesManager.texturesManager().getAlTextures().get(TexturesManager.texturesManager().getTexture(textureID)).addSpriteRenderer();

    if(image == null)
    Game.game.spriteRenderers.remove(this);

    comupteWorldSpaceSize();
    TexturesManager.texturesManager().updateTexturesList();

  }
  public BufferedImage getTexture(){
    return sprite;
  }


  /**
   * 
   * @return the an array of world space positions of each vertex (these positions are effected by the object rotation)
   */
  public float[] getVertices(){
    float[] vertices = new float[32];
    float[] finalWorldSize = {(float)Math.sqrt(getWorldSpaceSize().x * getWorldSpaceSize().x + getWorldSpaceSize().x * getWorldSpaceSize().x ) * transform.scale.x 
    ,(float)Math.sqrt(getWorldSpaceSize().y * getWorldSpaceSize().y + getWorldSpaceSize().y * getWorldSpaceSize().y ) * transform.scale.y};


    float distance = Vector2.distance(Vector2.subtract(transform.position,Game.game.camera.transform.position), Vector2.zero());
    //this is the angle made by the line between the camera center & this object
     float objectAngleAroundCam  = (float)Math.atan2((transform.position.y - Game.game.camera.transform.position.y) / distance, 
     (transform.position.x - Game.game.camera.transform.position.x) / distance);
    // here we rotate the position of this object in the oposite of the camera rotation
   float[] finalPos = {(float)Math.cos(Math.toRadians(Math.toDegrees(objectAngleAroundCam) - Game.game.camera.transform.zAngle) ) * Vector2.distance(transform.position, Game.game.camera.transform.position), 
    (float)Math.sin(Math.toRadians(Math.toDegrees(objectAngleAroundCam) - Game.game.camera.transform.zAngle)) * Vector2.distance(transform.position, Game.game.camera.transform.position)};
    
    //setting first vertex data
    vertices[0] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 45 - Game.game.camera.transform.zAngle)) + (finalPos[0] - Game.game.camera.transform.position.x));
    vertices[1] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 45 - Game.game.camera.transform.zAngle)) + (finalPos[1] - Game.game.camera.transform.position.y));
    vertices[2] = color[0];
    vertices[3] = color[1];
    vertices[4] = color[2];
    vertices[5] = color[3];
    vertices[6] = 1;
    vertices[7] = 0;

    //setting the second vertex data
    vertices[8] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 135- Game.game.camera.transform.zAngle)) + (finalPos[0] - Game.game.camera.transform.position.x));
    vertices[9] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 135- Game.game.camera.transform.zAngle)) + (finalPos[1] - Game.game.camera.transform.position.y));
    vertices[10] = color[0];
    vertices[11] = color[1];
    vertices[12] = color[2];
    vertices[13] = color[3];
    vertices[14] = 0;
    vertices[15] = 0;
    
    //setting the third vertex data
    vertices[16] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 225 - Game.game.camera.transform.zAngle)) + (finalPos[0] - Game.game.camera.transform.position.x));
    vertices[17] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 225 - Game.game.camera.transform.zAngle )) +(finalPos[1] - Game.game.camera.transform.position.y));
    vertices[18] = color[0];
    vertices[19] = color[1];
    vertices[20] = color[2];
    vertices[21] = color[3];
    vertices[22] = 0;
    vertices[23] = 1;
    //setting the fourth vetex data
    vertices[24] = (float) (finalWorldSize[0] * Math.cos(Math.toRadians(transform.zAngle + 315 - Game.game.camera.transform.zAngle)) + (finalPos[0] - Game.game.camera.transform.position.x));
    vertices[25] = (float) (finalWorldSize[1] * Math.sin(Math.toRadians(transform.zAngle + 315 - Game.game.camera.transform.zAngle)) + (finalPos[1] - Game.game.camera.transform.position.y));
    vertices[26] = color[0];
    vertices[27] = color[1];
    vertices[28] = color[2];
    vertices[29] = color[3];
    vertices[30] = 1;
    vertices[31] = 1;    
    return vertices;
  }
  public int[] getIndices(){
    int[] indices = {
      0,1,2,
      2,3,0
    };

    return indices;
  }
  
  public int getTextureID(){
    return textureID;
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