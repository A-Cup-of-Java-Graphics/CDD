#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 textureCoords;

out vec2 texCoords;
out vec3 colour;

uniform vec3 color;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

void main(void) {

    gl_Position = projection * view * model * vec4(vec3(position, 0), 1);
    texCoords = textureCoords;
    colour = color;

}