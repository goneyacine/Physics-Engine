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
  private Vector2 defaultSize;
  public BufferedImage defaultSprite;
  private Color oldColor;

  public SpriteRenderer(GameObject gameObject, BufferedImage sprite) {

    this.gameObject = gameObject;
    this.transform = gameObject.transform;
    this.defaultSprite = sprite;
    defaultSize = new Vector2(sprite.getWidth(), sprite.getHeight());
    Game.game.spriteRenderers.add(this);
    gameObject.components.add(this);
    gameObject.hasSpriteRenderer = true;
    gameObject.transform.spriteRenderer = this;
    name = "Sprite Renderer";
    renderRotatedSprite();

    oldColor = color;
  }

  /*this method takes the resource sprite & render it by apply rotation...*/
  public void renderRotatedSprite() {
    AffineTransform tx = new AffineTransform();
    tx.rotate(Math.toRadians(gameObject.transform.zAngle), defaultSize.x / 2, defaultSize.y / 2);
    AffineTransformOp op = new AffineTransformOp(tx,
        AffineTransformOp.TYPE_BILINEAR);
    sprite = op.filter(defaultSprite, null);
  }
  
  public BufferedImage getSprite(){
    return sprite;
  }
  public Vector2 getDefaultSize(){
    return defaultSize;
  }

  public void update(){
    if(oldColor != color){
      Game.game.shouldRenderNewFrame = true;
      oldColor = color;
    }
  }
}