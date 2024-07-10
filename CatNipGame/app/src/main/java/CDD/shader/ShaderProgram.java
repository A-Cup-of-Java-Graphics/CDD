package CDD.shader;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShaderProgram {

    private int program;
    private Map<Integer, Shader> shaders = new HashMap<Integer, Shader>();
    protected Shader vertex;
    protected Shader fragment;

    public ShaderProgram(Shader vertex, Shader fragment){
        this.vertex = vertex;
        this.fragment = fragment;
        create();
        attach(vertex);
        attach(fragment);
        bindFragmentOutputLocation(0, "FragColor");
        link();
        storeAllUniformLocations();
        validate();
        vertex.delete();
        fragment.delete();
    }

    public void create(){
        program = GL20.glCreateProgram();
    }

    public void link(){
        GL20.glLinkProgram(program);
        log(GL20.GL_LINK_STATUS);
    }

    public void validate(){
        GL20.glValidateProgram(program);
        log(GL20.GL_VALIDATE_STATUS);
    }

    public void storeAllUniformLocations(){
        for(Shader shader : shaders.values()){
            shader.readUniforms(program);
        }
    }

    public void log(int pname){
        int[] log = new int[1];
        GL20.glGetProgramiv(pname, pname, log);
        if(log[0] == GL20.GL_FALSE){
            System.out.println(GL20.glGetProgramInfoLog(program));
        }
    }

    public void start(){
        GL20.glUseProgram(program);
    }

    public void stop(){
        GL20.glUseProgram(0);
    }

    public void attach(Shader shader){
        GL20.glAttachShader(program, shader.getId());
        shaders.put(shader.getType(), shader);
    }

    public void detach(Shader shader){
        GL20.glDetachShader(program, shader.getId());
        shaders.put(shader.getType(), null);
    }

    public void delete(){
        GL20.glDeleteProgram(program);
    }

    public void bindFragmentOutputLocation(int colorNumber, String name){
        GL30.glBindFragDataLocation(program, colorNumber, name);
    }
    
}
