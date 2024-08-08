#version 330 core

in vec2 texCoords;
in vec3 colour;

out vec4 FragColor;

uniform sampler2D texture0;

void main(void) {

    vec4 sampleColor = texture(texture0, texCoords);
    vec4 simpleColor = vec4(colour, 1);

    if(sampleColor.a < 0.5){
        discard;
    }

    FragColor = sampleColor * simpleColor;

}