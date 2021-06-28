package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.vectors.*;
import com.physicsEngine.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Cam extends Component {

  //the size is a vector that contains the x & y size of the camera view in units
  public float size;
  //the resolution of the screen or the final dispalyed image
  public Vector2 resolution;
  //the parent gameObject transform component
  public Transform transform;

  public Cam(GameObject gameObject, float size, Vector2 resolution) {
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
      for (int w = 0; w < spriteRenderer.getRenderedSprite().getWidth(); w++) {
        for (int h = 0; h < spriteRenderer.getRenderedSprite().getHeight(); h++)
          if (w < resolution.x && h < resolution.y) {
            try {
              Vector2 screenCenterPosition = new Vector2(resolution.x / 2,resolution.y / 2);
              float pixelsPerUnit = (size * size) / (resolution.x * resolution.y);
              Vector2 objectWorldPositionRelativeToCam = Vector2.subtract(spriteRenderer.gameObject.transform.position, transform.position);
              Vector2 pixelPosition = Vector2.sum(screenCenterPosition,Vector2.multiply(objectWorldPositionRelativeToCam, new Vector2(pixelsPerUnit,pixelsPerUnit)));
              frame.setRGB(w + (int)pixelPosition.x - (int)spriteRenderer.getOriginalSize().x, h +(int)pixelPosition.y - (int)spriteRenderer.getOriginalSize().y, spriteRenderer.getRenderedSprite().getRGB(w, h));
            } catch (IndexOutOfBoundsException e) {continue;}
          }
      }
    }
    return frame;
  }

  /* this method translates the position of a point from a screen position
  (in pixels) to world position (in units) */
  public Vector2 screenToWorldPoint(Vector2 screenPoint) {
    float pixelsPerUnit = (size * size) / (resolution.x * resolution.y);
    return Vector2.sum(transform.position , new Vector2(screenPoint.x / pixelsPerUnit , screenPoint.y / pixelsPerUnit));
  }

  /* this method translates the position of a point from a world position
  (in units) to screen point (in pixels)  */
  public Vector2 worldToScreenPoint(Vector2 worldPoint) {
    float pixelsPerUnit = (size * size) / (resolution.x * resolution.y);
    return new Vector2((worldPoint.x - transform.position.x) * pixelsPerUnit, (worldPoint.y - transform.position.y) * pixelsPerUnit);
  }


}