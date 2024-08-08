package CDD.render;

import org.joml.Vector3f;

import CDD.shader.BaseShader;

public class SpriteShader extends BaseShader {

    public void setColor(Vector3f color){
        fragment.vector("color").load(color);
    }
    
}
