package shaders;

import java.io.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderCompiler{
    public static int compileShader(String[] shadersFilePaths,int[] shaderTypes){
    try{
    int program = glCreateProgram();
    //compile all the mini shaders like fragment shader, vertex shader and adding then to the main shader program
    for(int i = 0; i < shadersFilePaths.length;i++){
    File file = new File(shadersFilePaths[i]);
    BufferedReader is = null;
	try {
		is = new BufferedReader(new FileReader(file));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	StringBuilder source = new StringBuilder();
	String line;

	try {
		while((line = is.readLine()) != null) {
			source.append(line).append("\n");
		}
	} catch (IOException e) {
		e.printStackTrace();
	}

  int id = glCreateShader(shaderTypes[i]);
  glShaderSource(id,source);
  glCompileShader(id);
  glAttachShader(program,id);

	if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == 0) {

		GL20.glDeleteShader(id);
		throw new Error("Could not compile shader with path  " + shadersFilePaths[i]);
	}

    }
    glLinkProgram(program);
	glValidateProgram(program);
    return program;
 }catch(Exception e){
     e.printStackTrace();
     return -1;
 }
}


}