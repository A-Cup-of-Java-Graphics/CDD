package CDD.shader;

import org.lwjgl.opengl.GL20;

public class UniformSampler extends Uniform {

    private int sample;

    public UniformSampler(String name, int programID) {
        super(name, programID);
        //TODO Auto-generated constructor stub
    }

    public void load(int sample){
        if(this.sample != sample){
            this.sample = sample;
            GL20.glUniform1i(location, sample);
        }
    }
    
}
