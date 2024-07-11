package CDD.OBJLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import CDD.components.Vao;

public class RawModel {

    private static Vao vao;

    public RawModel(Vao vao2) {
        //TODO Auto-generated constructor stub
    }

    public void Model(Vao vao){
        RawModel.vao = vao;
    }

    public static Vao getVao(){
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