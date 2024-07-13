package CDD.models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import CDD.components.Vao;

public class Model {

    private Vao vao;
    private int drawMode;
    private boolean useElements;

    public Model(Vao vao, int drawMode, boolean useElements){
        this.vao = vao;
        this.drawMode = drawMode;
        this.useElements = useElements;
    }

    public Vao getVao(){
        return vao;
    }

    public int getDrawMode(){
        return drawMode;
    }

    public boolean isUsingElements(){
        return useElements;
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
        if(useElements)
            GL15.glDrawElements(drawMode, vao.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
        else
            GL15.glDrawArrays(drawMode, 0, vao.getVertexCount());
    }
    
}
