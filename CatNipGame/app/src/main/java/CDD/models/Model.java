package CDD.models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import CDD.components.Vao;

public class Model {

    private Vao vao;

    public Model(Vao vao){
        this.vao = vao;
    }

    public Vao getVao(){
        return vao;
    }

    public void bind(){
        getVao().bind();
        getVao().enableAttributes();
        getVao().getEbo().bind();
    }

    public void unbind(){
        getVao().getEbo().unbind();
        getVao().disableAttributes();
        getVao().unbind();
    }

    public void render(){
        GL15.glDrawElements(GL11.GL_TRIANGLES, vao.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
    }
    
}
