package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.*;
import com.physicsEngine.vectors.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Color;

public class SpriteRenderer extends Component {
  /** the parent gameObject that this component is attached to it */
  public GameObject gameObject;
  /** the transform component of the parent gameObject */
  public Transform transform;
  /** the final rendered sprite(image) after apllying : scale,rotation & color */
  private int[][] sprite;
  /** the color applyed to the rendered image */
  private Color color = Color.white;
  /** the dimensions of the reference image(originalSprite)*/
  private Vector2 originalSize;
  /** the refrence sprite (image) */
  private BufferedImage originalSprite;
  /** the original sprite after scaling it & without apllying any rotation*/
  private int[][] notRotatedButScaledSprite;
  /** number of pixels in one world unit */
  private int pixelsPerUnit = 100;
  /** the dimensions of that sprite renderer in world space (or the dimensions of the space token by that sprite in world space) */
  private Vector2 worldSpaceSize;
  /** the raduis of the circle that surounds the sprite in world space*/
  private float raduis;
  
  public SpriteRenderer(GameObject gameObject, BufferedImage sprite) {

    this.gameObject = gameObject;
    this.transform = gameObject.transform;
    this.originalSprite = sprite;

    originalSize = new Vector2(sprite.getWidth(), sprite.getHeight());
    Game.game.spriteRenderers.add(this);
    gameObject.components.add(this);
    gameObject.hasSpriteRenderer = true;
    gameObject.transform.spriteRenderer = this;
    name = "Sprite Renderer";
    renderScaledSprite();
    comupteWorldSpaceSize();
    Game.game.camera.checkIfShouldRender(this);;
  }

  /**
   * this method takes the resource sprite & render it by apply rotation...
   */
  public void renderRotatedSprite() {
    AffineTransform tx = new AffineTransform();
    tx.rotate(Math.toRadians(gameObject.transform.zAngle), notRotatedButScaledSprite.length / 2, notRotatedButScaledSprite[0].length / 2);
    AffineTransformOp op = new AffineTransformOp(tx,
        AffineTransformOp.TYPE_BILINEAR);
    BufferedImage img = new BufferedImage(notRotatedButScaledSprite.length, notRotatedButScaledSprite[0].length,BufferedImage.TYPE_INT_ARGB);
    for(int i = 0;i < notRotatedButScaledSprite.length;i++){
      for(int j = 0;j < notRotatedButScaledSprite[0].length;j++)
      img.setRGB(i,j,notRotatedButScaledSprite[i][j]);
    }
    img = op.filter(img, null);
    for(int i = 0;i < notRotatedButScaledSprite.length;i++){
      for(int j = 0;j < notRotatedButScaledSprite[0].length;j++)
      sprite[i][j] = img.getRGB(i, j);
    }
  }
  /**
   * 
   * @return rendered image after applying scale,rotation & color.
   */
  public int[][] getRenderedSprite() {
    return sprite;
  }
  public Vector2 getOriginalSize() {
    return originalSize;
  }
  /**
   * set the reference image of the sprite renderer component
   * @param img reference image
   */
  public void setOriginalSprite(BufferedImage img) {
    this.originalSprite = img;
    originalSize = new Vector2(img.getWidth(), img.getHeight());
    //when changing the original sprite we must rerender the sprite
    renderScaledSprite();
  }
  /**
   * @return the reference image of the sprite renderer component
   */
  public BufferedImage getOriginalSprite() {
    return originalSprite;
  }
  /**
   * sets the color of the sprite renderer 
   * @param color 
   */
  public void setColor(Color color){
    this.color = color;
  }
  /**
   * @return the color of the sprite renderer
   */
  public Color getColor(){
    return color;
  }
  /**
   * this method is used when changing the scale of the parent object
   */
  public void renderScaledSprite() {
    int newSpriteXSize = (int)(originalSize.x * gameObject.transform.scale.x);
    int newSpriteYSize = (int)(originalSize.y * gameObject.transform.scale.y);
    int[][] renderedImage = new int[newSpriteXSize][newSpriteYSize];
    int x_ratio = (int)((originalSprite.getWidth() << 16) / newSpriteXSize) + 1;
    int y_ratio = (int)((originalSprite.getHeight() << 16) / newSpriteYSize) + 1;
    int x2, y2; 

    for (int i = 0;i < newSpriteYSize;i++) {
        for (int j = 0;j < newSpriteXSize;j++) {
            x2 = ((j*x_ratio)>>16) ;
            y2 = ((i*y_ratio)>>16) ;
            renderedImage[j][i]  = originalSprite.getRGB(x2,y2);
        }
    }
    this.notRotatedButScaledSprite = renderedImage;
    if(transform.zAngle != 0)
    renderRotatedSprite();
    else
    sprite = renderedImage;

  }
  
  public int getPixelsPerUnit(){
    return pixelsPerUnit;
  }
  public void setPixelsPerUnit(int pixelsPerUnit){
    this.pixelsPerUnit = pixelsPerUnit;
    comupteWorldSpaceSize();
  }
  /**
   * @return the dimension of the space token by the rendered sprite
   */
  public Vector2 getWorldSpaceSize(){
    return worldSpaceSize;
  }
  /** computes the dimension of the space token by the rendered sprite */
  public void comupteWorldSpaceSize(){
  worldSpaceSize = new Vector2(sprite.length / pixelsPerUnit,sprite[0].length);
  }

  /**
   * @return the raduis of the circle that surounds the sprite in the world space
   */
  public float getRaduis(){
    return raduis;
  }
  /** computes the raduis of the circle that surounds the sprite in the world space */
  public void computeRaduis(){
    raduis = (float)(Math.sqrt(Math.pow(worldSpaceSize.x,2) + Math.pow(worldSpaceSize.y,2))) / 2;
  }




}