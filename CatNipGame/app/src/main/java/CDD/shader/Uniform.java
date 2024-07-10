package CDD.shader;

import org.lwjgl.opengl.GL20;

public abstract class Uniform {

    protected int location;
    protected String name;
    protected int programID;

    public Uniform(String name, int programID){
        this.name = name;
        this.programID = programID;
        this.location = getUniformLocation();
    }

    public int getUniformLocation(){
        return GL20.glGetUniformLocation(programID, name);
    }

    public static Uniform fromString(String line, int programID){
        String[] args = line.replace("uniform ", "").replace(";", "").split(" ");
        String type = args[0];
        String name = args[1];
        switch(type){
            case "vec4":
                return new UniformVector(name, programID, 4);
            case "vec3":
                return new UniformVector(name, programID, 3);
            case "vec2":
                return new UniformVector(name, programID, 2);
            case "mat3":
                return new UniformMatrix(name, programID, 3);
            case "mat4":
                return new UniformMatrix(name, programID, 4);
            case "float":
                return new UniformFloat(name, programID);
            case "boolean":
                return new UniformBoolean(name, programID);
            case "sampler2D":
            case "sampler2DShadow":
                return new UniformSampler(name, programID);
        }
        throw new IllegalArgumentException("There is no Uniform class for this Uniform variable!");
    }
    
}
