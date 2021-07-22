package com.physicsEngine.rendering;

import java.util.List;
import java.awt.image.BufferedImage;
import java.nio.*;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL45.*;

/**
 * every game instance should contain one instance of this class & you can access this instance by the static 
 * method 'texturesManager()'
 */
public class TexturesManager {

    private List<Texture> textures = new ArrayList<Texture>();
    private static TexturesManager texutresManager;
    private int lastAddedTextureID = -1;

    public TexturesManager(){
      texutresManager = this;
    }

    /**
     * 
     * @return a list of all the textures 
     */
    public List<Texture> getAlTextures(){
     return textures;
    }
    /** removes all the textures in the textures list */
    public void removeAllTextures(){
       textures = new ArrayList<Texture>();
    }
    /**
     * 
     * @param id
     * @return texture index in the textures list
     */
    public int getTexture(int id){
    
    int index = -1;
    for(int i = 0; i < textures.size();i++)
       if(textures.get(i).getID() == id){
          index = i;
          break;
       }

    if(index == -1)
    System.err.println("We Can't A Texture With ID = " + id + " In The Texture Manager");

     return index;
    }
    
    public static TexturesManager texturesManager(){
       return texutresManager;
    }
    /**
     * removes a texture with a give id from the textures list
     * @param id the id of the target texture
     * @return 1 if the texture is removed & -1 if we can't find that texture or remove it
     */
    public int removeTexture(int id){
     int result = -1;

     for(int i = 0; i < textures.size();i++)
         if(textures.get(i).getID() == id){
            textures.remove(i);
            result = 1;
            break;
         }

     return result;
    }
    /**
     * add a texture object to textures list
     * @param image the texture sprite or image
     * @return the id of the added texture 
     */
    public int addTexture(BufferedImage image){
     lastAddedTextureID++;
     Texture texture = new Texture(lastAddedTextureID, image);
     int openglTextureID = glGenTextures();
     texture.openglID = openglTextureID;

     glBindTexture(GL_TEXTURE_2D,openglTextureID);
      //Setup wrap mode
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

      //Setup texture scaling filtering
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

     
     int[] pixels = new int[image.getWidth() * image.getHeight()];
     image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
     ByteBuffer iBuffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

     for(int y = 0; y < image.getHeight(); y++){
         for(int x = 0; x < image.getWidth(); x++){
             int pixel = pixels[y * image.getWidth() + x];
             iBuffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
             iBuffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
             iBuffer.put((byte) (pixel & 0xFF));               // Blue component
             iBuffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
         }
     }

     iBuffer.flip(); 

     glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(),image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, iBuffer);


     glBindTexture(GL_TEXTURE_2D,0);
     textures.add(texture);
     return lastAddedTextureID;
    }
    /**
     * this method finds the texture objects that aren't used and remove them from the textures list 
     */
    public void updateTexturesList(){
       for(int i = 0; i < textures.size();i++){
           if(textures.get(i).getSpriteRenderersNum() == 0){
           glDeleteTextures(textures.get(i).openglID);
           textures.remove(i);
           }
       }
    }
}
