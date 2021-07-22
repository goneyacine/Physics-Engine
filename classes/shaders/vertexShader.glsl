#version 330 core

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 a_Color;
layout(location = 2) in vec2 texCoord;

uniform mat4 projectionMatrix;
out vec2 v_texCoord;
out vec4 v_Color;

void main()
{
 v_Color = a_Color;
 v_texCoord = texCoord;
 gl_Position = position * projectionMatrix;
}