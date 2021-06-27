package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.vectors.*;
import com.physicsEngine.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Cam extends Component {

  //the size is a vector that contains the x & y size of the camera view in units
  public Vector2 size;
  //the resolution of the screen or the final dispalyed image
  public Vector2 resolution;
  //the parent gameObject
  public GameObject gameObject;
  //the parent gameObject transform component
  // a static version of the parent object transform component
  public Transform transform;
  public Cam(GameObject gameObject, Vector2 size, Vector2 resolution) {
    this.gameObject =  gameObject;
    this.transform = gameObject.transform;
    this.size = size;
    this.resolution = resolution;
    Game.game.camera = this;
  }
  //this redender & return an image whenever we want to render a new frame
  public BufferedImage render() {

    List<SpriteRenderer> spriteRenderers = Game.game.spriteRenderers;

    BufferedImage frame = new BufferedImage((int)resolution.x, (int)resolution.y, BufferedImage.TYPE_INT_RGB);

    for (SpriteRenderer spriteRenderer : spriteRenderers) {
      for (int w = 0; w < spriteRenderer.sprite.getWidth(); w++) {
        for (int h = 0; h < spriteRenderer.sprite.getHeight(); h++)
          if (w < resolution.x && h < resolution.y) {
            try {
              Vector2 offset = this.worldToScreenPoint(spriteRenderer.gameObject.transform.position);
              frame.setRGB(w + (int)offset.x / 2, h + (int)offset.y / 2, spriteRenderer.sprite.getRGB(w, h));
            } catch (IndexOutOfBoundsException e) {continue;}
          }
      }
    }
    return frame;
  }

  /* this method translates the position of a point from a screen position
  (in pixels) to world position (in units) */
  public Vector2 screenToWorldPoint(Vector2 screenPoint) {
    float pixelsPerUnit = (resolution.x * resolution.y) / (size.x * size.y);
    return Vector2.sum(transform.position , new Vector2(screenPoint.x / pixelsPerUnit , screenPoint.y / pixelsPerUnit));
  }

  /* this method translates the position of a point from a world position
  (in units) to screen point (in pixels)  */
  public Vector2 worldToScreenPoint(Vector2 worldPoint) {
    float pixelsPerUnit = (resolution.x * resolution.y) / (size.x * size.y);
    return new Vector2((worldPoint.x - transform.position.x) * pixelsPerUnit, (worldPoint.y - transform.position.y) * pixelsPerUnit);
  }


}