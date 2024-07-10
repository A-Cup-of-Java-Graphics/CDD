package CDD.shader;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

import CDD.texture.Texture;
import CDD.util.GameFile;

public class BaseShader extends ShaderProgram {

    private static final Shader VERTEX = new Shader(GL20.GL_VERTEX_SHADER, new GameFile("CDD/shader/vertex.glsl"));
    private static final Shader FRAGMENT = new Shader(GL20.GL_FRAGMENT_SHADER, new GameFile("CDD/shader/fragment.glsl"));

    public BaseShader() {
        super(VERTEX, FRAGMENT);
        //TODO Auto-generated constructor stub
    }

    public void setProjection(Matrix4f projection){
        vertex.matrix("projection").load(projection);
    }

    public void setView(Matrix4f view){
        vertex.matrix("view").load(view);
    }

    public void setModel(Matrix4f model){
        vertex.matrix("model").load(model);
    }

    public void setTexture0(Texture texture){
        fragment.sampler("texture0").load(texture.getUnit());
    }
    
}
