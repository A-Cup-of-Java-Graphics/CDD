package CDD.shader;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

public class UniformMatrix extends Uniform {

    private int size;

    public UniformMatrix(String name, int programID, int size) {
        super(name, programID);
        this.size = size;
        //TODO Auto-generated constructor stub
    }

    public void load(Matrix3f matrix){
        if(size != 3){
            throw new IllegalArgumentException("This Uniform variable is dedicated to a 4x4 matrix!");
        }
        float[] data = matrix.get(new float[9]);
        GL20.glUniformMatrix3fv(location, false, data);
    }

    public void load(Matrix4f matrix){
        if(size != 4){
            throw new IllegalArgumentException("This Uniform variable is dedicated to a 3x3 matrix!");
        }
        float[] data = matrix.get(new float[16]);
        GL20.glUniformMatrix4fv(location, false, data);
    }
    
}
