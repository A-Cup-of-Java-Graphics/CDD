package CDD.shader;

import org.lwjgl.opengl.GL20;

public class UniformBoolean extends Uniform {

    private boolean bool;

    public UniformBoolean(String name, int programID) {
        super(name, programID);
        //TODO Auto-generated constructor stub
    }

    public void load(boolean bool){
        if(this.bool != bool){
            this.bool = bool;
            GL20.glUniform1i(location, bool ? 1 : 0);
        }
    }
    
}
