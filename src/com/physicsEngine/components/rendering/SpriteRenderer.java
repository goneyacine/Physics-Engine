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
  public BufferedImage sprite;
  public Color color = Color.white;
  private Vector2 defaultSize;

  public SpriteRenderer(GameObject gameObject,BufferedImage sprite){
  	this.gameObject = gameObject;
  	this.transform = gameObject.transform;
  	this.sprite = sprite;
  	defaultSize = new Vector2(sprite.getWidth(),sprite.getHeight());
  	Game.game.spriteRenderers.add(this);
  	gameObject.components.add(this);
  	renderRotatedSprite();
  }

  /*this method takes the resource sprite & render it by apply rotation...*/
  public void renderRotatedSprite(){
  AffineTransform tx = new AffineTransform();
    tx.rotate(0.5, defaultSize.x / 2, defaultSize.y / 2);
    AffineTransformOp op = new AffineTransformOp(tx,
        AffineTransformOp.TYPE_BILINEAR);
    sprite = op.filter(sprite, null);
  }
  
}