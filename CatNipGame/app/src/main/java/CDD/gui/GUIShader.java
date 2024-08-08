package CDD.gui;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import CDD.shader.BaseShader;
import CDD.shader.Shader;
import CDD.util.GameFile;

public class GUIShader extends BaseShader {

    private static final Shader VERTEX = new Shader(GL20.GL_VERTEX_SHADER, GameFile.readFile("CDD/shader/gui/vertex.glsl"));
    private static final Shader FRAGMENT = new Shader(GL20.GL_FRAGMENT_SHADER, GameFile.readFile("CDD/shader/gui/fragment.glsl"));

    
    public GUIShader(){
        super(VERTEX, FRAGMENT);
    }
    
    @Override
    public void setView(Matrix4f view){
        throw new UnsupportedOperationException("GUI doesn't require a view matrix");
    }

    public void setColor(Vector3f color){
        vertex.vector("color").load(color);
    }

    public void setHasTexture(boolean hasTexture){
        fragment.bool("hasTexture").load(hasTexture);
    }
    
}
