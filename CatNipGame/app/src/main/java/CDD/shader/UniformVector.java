package CDD.shader;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

public class UniformVector extends Uniform {

    private float[] contents;

    public UniformVector(String name, int programID, int size) {
        super(name, programID);
        contents = new float[size];
        //TODO Auto-generated constructor stub
    }

    public void load(Vector2f vector){
        if(contents.length != 2){
            throw new IllegalArgumentException("This Uniform variable is dedicated to another vector of a different size!");
        }
        if(contents[0] != vector.x || contents[1] != vector.y){
            contents[0] = vector.x;
            contents[1] = vector.y;
            GL20.glUniform2f(location, vector.x, vector.y);
        }
    }

    public void load(Vector3f vector){
        if(contents.length != 3){
            throw new IllegalArgumentException("This uniform variable is dedicated to another vector of a different size!");
        }
        if(contents[0] != vector.x || contents[1] != vector.y || contents[2] != vector.z){
            contents[0] = vector.x;
            contents[1] = vector.y;
            contents[2] = vector.z;
            GL20.glUniform3f(location, vector.x, vector.y, vector.z);
        }
    }

    public void load(Vector4f vector){
        if(contents.length != 4){
            throw new IllegalArgumentException("This uniform variable is dedicated to another vector of a different size!");
        }
        if(contents[0] != vector.x || contents[1] != vector.y || contents[2] != vector.z || contents[3] != vector.w){
            contents[0] = vector.x;
            contents[1] = vector.y;
            contents[2] = vector.z;
            contents[3] = vector.w;
            GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
        }
    }
    
}
