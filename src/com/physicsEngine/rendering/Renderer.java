package com.physicsEngine.rendering;

import java.nio.*;


import com.physicsEngine.Game;
import com.physicsEngine.components.rendering.SpriteRenderer;
import com.physicsEngine.vectors.Vector2;

import org.joml.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.util.*;
import shaders.ShaderCompiler;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.util.List;

public class Renderer {

   // The window handle
  private long window;
  private int bufferID;
  private int indexBufferID;
  private int projectionMatrixUniformPath;
  private float[] vertices;
  private int[] indices;
  private float camXsize;
  private float camYsize;
  private float[] spriteRendererVertices;
  private int[] spriteRendererIndices;
  private int defaultShaderID;
 public Renderer(){
   initWindow();
 }

	private void initWindow() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(3, 3, "Game Engine", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
        
		String[] defaultShaderPaths = {
			"src\\shaders\\vertexShader.glsl",
			"src\\shaders\\fragmentShader.glsl"
		};
		int[] defaultShaderTypes = {
			GL_VERTEX_SHADER,
            GL_FRAGMENT_SHADER
		};
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		bufferID =  glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,bufferID);
        glBufferData(GL_ARRAY_BUFFER,NULL,GL_DYNAMIC_DRAW);
  

	    indexBufferID =  glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indexBufferID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,NULL,GL_DYNAMIC_DRAW);
		defaultShaderID = ShaderCompiler.compileShader(defaultShaderPaths, defaultShaderTypes);
		glUseProgram(defaultShaderID);
		projectionMatrixUniformPath =  glGetUniformLocation(defaultShaderID,"projectionMatrix");
		int textureUniformPath = glGetUniformLocation(defaultShaderID,"u_texture");
		glUniform1i(textureUniformPath,0);
		glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
				
        //position attrib
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0,2,GL11.GL_FLOAT,false,Float.BYTES * 8,0);
		//color attrib
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1,4,GL11.GL_FLOAT,false,Float.BYTES * 8,8);
		//texture coordinate attrib
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(2,2,GL11.GL_FLOAT,false,Float.BYTES * 8,24);
	}

  /**
   * 

   * @param spriteRenderers all the sprite renderers that are enabled in the scene
   */
  public void render(List<SpriteRenderer> spriteRenderers){
	if(glfwWindowShouldClose(window)){
		onCloseWindow();
		System.exit(0);
	;
	}
	//checks there is camera object, if there no camera then we don't need to render 
	if(Game.game.camera == null)
	return;
	/** the indicies of the sprite renderers that should be rendered */
    List<Integer> shouldRendereSpriteRenderers = new ArrayList<Integer>();
    for(int i = 0; i < spriteRenderers.size();i++)
	    if(Vector2.distance(Game.game.camera.transform.position, spriteRenderers.get(i).transform.position) < Game.game.camera.getViewSpace() + spriteRenderers.get(i).getWorldSpaceRaduis())
		shouldRendereSpriteRenderers.add(i);

    int unrenderedSpriteRenderers = shouldRendereSpriteRenderers.size();
    
	glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
        //computing the camera view matrix
	try (MemoryStack stack = MemoryStack.stackPush()) {

		//computing the matrix & storing it in a float buffer 
		FloatBuffer fb = new Matrix4f().
		ortho2D(-camXsize /2,camXsize / 2 ,-camYsize /2 ,camYsize / 2).get(stack.mallocFloat(16));
		
	
		//setting the view matrix uniform in the default shader
		glUniformMatrix4fv(projectionMatrixUniformPath,false,fb);
	
		}
	boolean keepRendering = true;
    int usedTexture = 0;
    while(keepRendering){
	glActiveTexture(GL_TEXTURE0);
	glEnable(GL_TEXTURE_2D);
	if(unrenderedSpriteRenderers ==  0)
	break;
    //we render one texture every draw call 
    int textureID = TexturesManager.texturesManager().getAlTextures().get(usedTexture).getID();
    glBindTexture(GL_TEXTURE_2D,TexturesManager.texturesManager().getAlTextures().get(textureID).openglID);
    //putting all the vertices data into one array
     vertices = new float[spriteRenderers.size() * 32];
	 indices = new int[spriteRenderers.size() * 6];
	for(int i = 0;i < shouldRendereSpriteRenderers.size();i++){
		if(spriteRenderers.get(shouldRendereSpriteRenderers.get(i)).getTextureID() != textureID)
		continue;

		 spriteRendererVertices = spriteRenderers.get(shouldRendereSpriteRenderers.get(i)).getVertices();
	     spriteRendererIndices = spriteRenderers.get(shouldRendereSpriteRenderers.get(i)).getIndices();
         
		
        for(int j = 0;j < 32;j++){
			if(j < 6){
				indices[i * 6 + j] = spriteRendererIndices[j] + i * 4;
			}
			vertices[i * 32 + j] = spriteRendererVertices[j];
		}
    unrenderedSpriteRenderers--;
	}
     camXsize =  Game.game.camera.acpectRatio[0] * Game.game.camera.getSize();
	 camYsize =  Game.game.camera.acpectRatio[1] * Game.game.camera.getSize();
     
    glBindBuffer(GL_ARRAY_BUFFER,bufferID);
    glBufferData(GL_ARRAY_BUFFER,vertices,GL_DYNAMIC_DRAW);
    
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indexBufferID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER,indices,GL_DYNAMIC_DRAW);

    glDrawElements(GL11.GL_TRIANGLES,indices.length,GL11.GL_UNSIGNED_INT,0);	
	glBindTexture(GL_TEXTURE_2D,0);
	usedTexture++;
    if(unrenderedSpriteRenderers < 1)
	keepRendering = false;
        }
	glfwSwapBuffers(window); // swap the color buffers

			
	 // Poll for window events. The key callback above will only be
	 // invoked during this call.
	glfwPollEvents();

	}
		
  public void onCloseWindow(){
    if(glfwWindowShouldClose(window)){
      // Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
    }
  }
  public long getWindow(){
	  return window;
  }

  }
