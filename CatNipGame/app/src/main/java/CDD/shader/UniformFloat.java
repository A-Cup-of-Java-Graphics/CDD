package CDD.shader;

import org.lwjgl.opengl.GL20;

public class UniformFloat extends Uniform {

    private float data;

    public UniformFloat(String name, int programID) {
        super(name, programID);
        //TODO Auto-generated constructor stub
    }

    public void load(float data){
        if(this.data != data){
            this.data = data;
            GL20.glUniform1f(location, data);
        }
    }
    
}
