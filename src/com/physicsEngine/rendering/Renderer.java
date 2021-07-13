package com.physicsEngine.rendering;

import java.nio.*;


import com.physicsEngine.Game;
import com.physicsEngine.components.rendering.SpriteRenderer;

import org.joml.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import shaders.ShaderCompiler;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Renderer {

   // The window handle
  private long window;
  private int bufferID;
  private int indexBufferID;
  private int veiwMatrixUniformPath;

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

         glEnableVertexAttribArray(0);
         glVertexAttribPointer(0,2,GL11.GL_FLOAT,false,Float.BYTES * 2,0);

	    indexBufferID =  glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indexBufferID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,NULL,GL_DYNAMIC_DRAW);
		defaultShaderID = ShaderCompiler.compileShader(defaultShaderPaths, defaultShaderTypes);
		glUseProgram(defaultShaderID);
		veiwMatrixUniformPath =  glGetUniformLocation(defaultShaderID,"veiwMatrix");
	}
  public void render(List<SpriteRenderer> spriteRenderers){
    onCloseWindow();
	if(glfwWindowShouldClose(window))
	return;
	//checks there is camera object, if there no camera then we don't need to render 
	if(Game.game.camera == null)
	return;

    float[] vertices = new float[spriteRenderers.size() * 4 * 2];
	int[] indices = new int[spriteRenderers.size() * 6];
       //putting all the vertices data into one array
	   
	for(int i = 0;i < spriteRenderers.size();i++){
		float[] spriteRendererVertices = spriteRenderers.get(i).getVertices();
		int[] spriteRendererIndices = spriteRenderers.get(i).getIndices();

		
        for(int j = 0;j < 8;j++){
			if(j < 6){
				indices[i * 6 + j] = spriteRendererIndices[j] + i * 6;
			}
			vertices[i * 8 + j] = spriteRendererVertices[j];
		}
	}

    //computing the camera view matrix
	try (MemoryStack stack = MemoryStack.stackPush()) {

	//computing the matrix & storing it in a float buffer
	float camXsize =  Game.game.camera.acpectRatio[0] * Game.game.camera.getSize();
	float camYsize =  Game.game.camera.acpectRatio[1] * Game.game.camera.getSize(); 
	FloatBuffer fb = new Matrix4f().
	ortho2D(-camXsize /2,camXsize / 2,-camYsize /2,camYsize / 2).get(stack.mallocFloat(16));
    

	

	//setting the view matrix uniform in the default shader
    glUniformMatrix4fv(veiwMatrixUniformPath,false,fb);

	}
    glBindBuffer(GL_ARRAY_BUFFER,bufferID);
    glBufferData(GL_ARRAY_BUFFER,vertices,GL_DYNAMIC_DRAW);

    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0,2,GL11.GL_FLOAT,false,Float.BYTES * 2,0);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indexBufferID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER,indices,GL_DYNAMIC_DRAW);
   

	  glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
      glDrawElements(GL11.GL_TRIANGLES,indices.length,GL11.GL_UNSIGNED_INT,0);
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

  }
