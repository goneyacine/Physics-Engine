package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.*;
import com.physicsEngine.vectors.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class SpriteRenderer extends Component {
  
  /** the parent gameObject that this component is attached to it */
  public GameObject gameObject;
  /** the transform component of the parent gameObject */
  public Transform transform;
  /** the final rendered sprite(image) after apllying : scale,rotation & color */
  private BufferedImage texture;
  /** the color applyed to the rendered image */
  private Color color = Color.white;
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


  /**
   * sets the color of the sprite renderer
   * @param color
   */
  public void setColor(Color color) {
    this.color = color;
  }
  /**
   * @return the color of the sprite renderer
   */
  public Color getColor() {
    return color;
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
   * @return the an array of world space positions of each vertex
   */
  public float[] getVertices(){
    float[] vertices = new float[8];
    vertices[0] = (float) (getWorldSpaceSize().x * Math.cos(Math.toRadians(transform.zAngle + 45)) + transform.position.x);
    vertices[1] = (float) (getWorldSpaceSize().y * Math.sin(Math.toRadians(transform.zAngle + 45)) + transform.position.y);
    
   
    vertices[2] = (float) (getWorldSpaceSize().x *  Math.cos(Math.toRadians(transform.zAngle + 135)) + transform.position.x);
    vertices[3] = (float) (getWorldSpaceSize().y * Math.sin(Math.toRadians(transform.zAngle + 135)) + transform.position.y);

    
    vertices[4] = (float) (getWorldSpaceSize().x * Math.cos(Math.toRadians(transform.zAngle + 225)) + transform.position.x);
    vertices[5] = (float) (getWorldSpaceSize().y *  Math.sin(Math.toRadians(transform.zAngle + 225)) + transform.position.y);

    
    vertices[6] = (float) (getWorldSpaceSize().x * Math.cos(Math.toRadians(transform.zAngle + 315)) + transform.position.x);
    vertices[7] = (float) (getWorldSpaceSize().y * Math.sin(Math.toRadians(transform.zAngle + 315)) + transform.position.y);
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