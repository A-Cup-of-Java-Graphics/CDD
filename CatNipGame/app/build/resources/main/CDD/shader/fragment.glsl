#version 330 core

in vec2 texCoords;

out vec4 FragColor;

uniform vec3 color;
uniform sampler2D texture0;

void main(void) {

    vec4 sampleColor = texture(texture0, texCoords);

    if(sampleColor.a < 0.5){
        discard;
    }

    FragColor = sampleColor * vec4(color, 1);

}