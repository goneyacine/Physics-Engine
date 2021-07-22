#version 330 core

layout(location = 0) out vec4 color;

uniform sampler2D u_texture;

in vec2 v_texCoord;
in vec4 v_Color;
void main()
{
 vec4 textureColor =  texture(u_texture,v_texCoord);
 color = textureColor + v_Color;
}