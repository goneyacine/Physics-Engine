package com.physicsEngine.rendering;

import java.awt.image.*;

public class Texture {
    private int id;
    /** the of the texture in the gpu */
    public int openglID;
    private BufferedImage image;
    /**
     * the number sprite renderers that are using this texture 
     */
    private int spriteRenderersNum;

    public Texture(int id,BufferedImage image){
    this.id = id;
    this.image = image;
    }
    /**
     * 
     * @return the id of the texture used by the texutre manager
     */
    public int getID(){
        return id;
    }
    public BufferedImage getImage(){
      return image;
    }
    /**
     * 
     * @return the number of the sprite renderers that are using this texture 
     */
    public int getSpriteRenderersNum(){
        return spriteRenderersNum;
    }
    /**
     * adds 1 to the sprite renderer number value
     */
    public void addSpriteRenderer(){
     spriteRenderersNum++;
    }
    /**
     * removes 1 to the sprite renderer number value
     */
    public void removeSpriteRenderer(){
    spriteRenderersNum--;
    }
}
