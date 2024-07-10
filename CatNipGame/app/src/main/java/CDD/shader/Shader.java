package CDD.shader;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import CDD.util.GameFile;

public class Shader {

    private int type;
    private int id;
    private GameFile file;
    private Map<String, Uniform> uniforms = new HashMap<String, Uniform>();

    public Shader(int type, GameFile file){
        this.type = type;
        this.file = file;
        create();
        source();
        compile();
    }

    public void create(){
        id = GL20.glCreateShader(type);
    }

    public void source(){
        String source = String.join("\n", file.getData());
        GL20.glShaderSource(id, source);
    }

    public void compile(){
        GL20.glCompileShader(id);
        log(GL20.GL_COMPILE_STATUS);
    }

    public void log(int pname){
        int[] log = new int[1];
        GL20.glGetShaderiv(id, pname, log);
        if(log[0] == GL20.GL_FALSE){
            String infoLog = GL20.glGetShaderInfoLog(id);
            System.out.println(infoLog);
        }
    }

    public void readUniforms(int programID){
        for(String line : file.getData()){
            if(line.startsWith("uniform ")){
                Uniform uniform = Uniform.fromString(line, programID);
                uniforms.put(uniform.name, uniform);
            }
        }
    }

    public <T extends Uniform> T find(String name){
        return (T) uniforms.get(name);
    }

    public UniformMatrix matrix(String name){
        return find(name);
    }

    public UniformVector vector(String name){
        return find(name);
    }

    public UniformBoolean bool(String name){
        return find(name);
    }

    public UniformFloat phloat(String name){
        return find(name);
    }

    public UniformSampler sampler(String name){
        return find(name);
    }

    public int getId(){
        return id;
    }

    public int getType(){
        return type;
    }

    public void delete(){
        GL20.glDeleteShader(id);
    }

}