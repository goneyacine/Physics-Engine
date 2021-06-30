package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.vectors.*;
import com.physicsEngine.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

public class Cam extends Component {

  /** the size is a vector that contains the x & y size of the camera view in units */
  private float size;
  /** the resolution of the screen or the final dispalyed image */
  public Vector2 resolution;
  /** the parent gameObject transform component */
  public Transform transform;
 
  /** the dimenesions of the camera view area in world space units */
  private float[] camSize;

  /** the raduis of the circle that surounds the view area rectangle */
  private float raduis;
  /** a list of the sprite renderers that should render */
  private List<SpriteRenderer> shouldRender = new ArrayList<SpriteRenderer>();
  
  private List<int[][]> renderedSprites = new ArrayList<int[][]>();

 /**
  * 
  * @param gameObject parent gameObject
  * @param size the view size of the camera
  * @param resolution the screen resolution
  */
  public Cam(GameObject gameObject, float size, Vector2 resolution) {
    this.gameObject =  gameObject;
    this.transform = gameObject.transform;
    this.resolution = resolution;
    setViewSize(size);

    Game.game.camera = this;

  }

  /**
   * 
   * @param screenPoint the screen position of the point that you want to translate it's postion to world space 
   * @return this method translates the position of a point from a screen position
   (in pixels) to world position (in units)
   */
  public Vector2 screenToWorldPoint(Vector2 screenPoint) {
    float[] camSize =  getCamSize();
    float pixelsPerUnit = (resolution.x * resolution.y) / (camSize[0] * camSize[1]);
    return Vector2.sum(transform.position , new Vector2(screenPoint.x / pixelsPerUnit , screenPoint.y / pixelsPerUnit));
  }

  /**
   * 
   * @param worldPoint the world position of the point that you want to translate it's position
   * @return this method translates the position of a point from a world position
   (in units) to screen point (in pixels)
   */
  public Vector2 worldToScreenPoint(Vector2 worldPoint) {
    float[] camSize =  getCamSize();
    float pixelsPerUnit = (resolution.x * resolution.y) / (camSize[0] * camSize[1]);
    return new Vector2((worldPoint.x - transform.position.x) * pixelsPerUnit, (worldPoint.y - transform.position.y) * pixelsPerUnit);
  }
  /**
   * 
   * @return camera veiw dimensions (maybe you say size) in world space 
   */
  public float[] getCamSize(){
  return camSize;
  }
  public void computeCamSize(){
  float[] camSize = {(float)(size * reduceFraction((int)resolution.x,(int)resolution.y)[0]),(float)(size * reduceFraction((int)resolution.x,(int)resolution.y)[1])};
  this.camSize = camSize;
  computeRaduis();
  }
  /** 
   * these two methods are used to reduce the resolustion for example from [1920,1080] to [16,9] 
    * we need this when we want to get the camera size
    */
  private int[] reduceFraction(int x, int y)
  {
      int d;
      d = __gcd(x, y);
   
      x = x / d;
      y = y / d;
     int[] result = {x,y};
     return result;
  }
  private int __gcd(int a, int b)
  {
      if (b == 0)
          return a;
      return __gcd(b, a % b);
       
  }
  /**
   * this method checks if an object is in the screen to check if we should render it 
   * @param spriteRenderer 
   */
  public void checkIfShouldRender(SpriteRenderer spriteRenderer){
  if(Vector2.distance(transform.position,spriteRenderer.transform.position) <= spriteRenderer.getRaduis() + raduis)
  shouldRender.add(spriteRenderer);
  else
  shouldRender.remove(spriteRenderer);
  }

  public float getViewSize(){
    return size;
  }
  public void setViewSize(float size){
    this.size = size;
    computeCamSize();
  }
  /** computes the raduis of the circle that surounds the camera view area */
  public void computeRaduis(){
  this.raduis = (float) (Math.sqrt(camSize[0] * camSize[0] + camSize[1] * camSize[1]) / 2);
  }
  /**
   * 
   * @return the raduis of the circle that surounds the camera view area
   */
  public float getRaduis(){
    return raduis;
  }
  public void renderSprite(SpriteRenderer spriteRenderer){
  int[][] sprite = spriteRenderer.getRenderedSprite();
  int xSize = sprite.length;
  int ySize = sprite[0].length;
  int newSpriteXSize = (int)(xSize * (spriteRenderer.getWorldSpaceSize().x / camSize[0]));
  int newSpriteYSize = (int)(ySize *  (spriteRenderer.getWorldSpaceSize().y / camSize[1]));
  int[][] renderedImage = new int[newSpriteXSize][newSpriteYSize];
  int x_ratio = (int)((xSize << 16) / newSpriteXSize) + 1;
  int y_ratio = (int)((ySize << 16) / newSpriteYSize) + 1;
  int x2, y2; 

  for (int i = 0;i < newSpriteYSize;i++) {
      for (int j = 0;j < newSpriteXSize;j++) {
          x2 = ((j*x_ratio)>>16) ;
          y2 = ((i*y_ratio)>>16) ;
          renderedImage[j][i]  = sprite[x2][y2];
      }
  }
  renderedSprites.add(sprite);
  }
   /**
   * 
   * @return this redender & return an image whenever we want to render a new frame
   */
  public BufferedImage render() {
    renderedSprites = new ArrayList<int[][]>();
    for(SpriteRenderer spr : shouldRender)
    renderSprite(spr);
    BufferedImage frame = new BufferedImage((int)resolution.x, (int)resolution.y, BufferedImage.TYPE_INT_RGB);
    for(int x = 0;x < renderedSprites.size();x++){
      int[][] img = renderedSprites.get(x);
      int xOffest = img.length /2;
      int yOffest = img[0].length / 2;
      Vector2 objectCenterScreenPostion = worldToScreenPoint(shouldRender.get(x).transform.position);
       for(int i = 0; i < img.length;i++){
           for(int j = 0; j < img[0].length;j++){
              try{
              frame.setRGB((int)objectCenterScreenPostion.x - xOffest + i,(int)objectCenterScreenPostion.y - yOffest + j,img[i][j]);
              }catch(IndexOutOfBoundsException e){continue;}
           }
       }
    }
    return frame;
  }
   


}