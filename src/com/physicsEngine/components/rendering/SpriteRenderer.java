package com.physicsEngine.components.rendering;

import com.physicsEngine.components.*;
import com.physicsEngine.*;
import com.physicsEngine.vectors.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Color;

public class SpriteRenderer extends Component {

  public GameObject gameObject;
  public Transform transform;
  private BufferedImage sprite;
  public Color color = Color.white;
  private Vector2 originalSize;
  private BufferedImage originalSprite;
  private Color oldColor;
  private BufferedImage notRotatedButScaledSprite;

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

    oldColor = color;
  }

  /*this method takes the resource sprite & render it by apply rotation...*/
  public void renderRotatedSprite() {
    AffineTransform tx = new AffineTransform();
    tx.rotate(Math.toRadians(gameObject.transform.zAngle), notRotatedButScaledSprite.getWidth() / 2, notRotatedButScaledSprite.getHeight() / 2);
    AffineTransformOp op = new AffineTransformOp(tx,
        AffineTransformOp.TYPE_BILINEAR);
    sprite = op.filter(notRotatedButScaledSprite, null);
  }
  //this method is used to get rendered sprite that's the original image after applying scale,rotation,color...
  public BufferedImage getRenderedSprite() {
    return sprite;
  }
  public Vector2 getOriginalSize() {
    return originalSize;
  }
  public void setOriginalSprite(BufferedImage img) {
    this.originalSprite = img;
    originalSize = new Vector2(sprite.getWidth(), sprite.getHeight());
    //when changing the original sprite we must rerender the sprite
    renderScaledSprite();
  }
  public BufferedImage getOriginalSprite() {
    return originalSprite;
  }

  public void update() {
    if (oldColor != color) {
      Game.game.shouldRenderNewFrame = true;
      oldColor = color;
    }
  }

  //this method is used when changing the scale of the parent object
  public void renderScaledSprite() {
    if (gameObject.transform.scale.x == 1 && gameObject.transform.scale.y == 1) {
      this.notRotatedButScaledSprite = originalSprite;
       renderRotatedSprite();
      return;
    }
    int newSpriteXSize = (int)(originalSize.x * gameObject.transform.scale.x);
    int newSpriteYSize = (int)(originalSize.y * gameObject.transform.scale.y);
    BufferedImage renderedImage = new BufferedImage(newSpriteXSize,newSpriteYSize,BufferedImage.TYPE_INT_RGB);
    int x_ratio = (int)((originalSprite.getWidth() << 16) / newSpriteXSize) + 1;
    int y_ratio = (int)((originalSprite.getHeight() << 16) / newSpriteYSize) + 1;
    double px, py; 

    for (int i = 0;i < newSpriteXSize;i++) {
        for (int j = 0;j < newSpriteYSize;j++) {
            px = Math.floor(j*x_ratio) ;
            py = Math.floor(i*y_ratio) ;
            renderedImage.setRGB(i,j,originalSprite.getRGB((int)px,(int)py));
        }
    }
    this.notRotatedButScaledSprite = renderedImage;
    System.out.println("Scalling is working I think");
    renderRotatedSprite();
  }



}