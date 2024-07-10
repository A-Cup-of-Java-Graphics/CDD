package CDD.texture;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import CDD.util.GameFile;

public class Texture {

    private static final float LOD_BIAS = -0.4f;
    private static final boolean USE_MIPMAP = true;
    private static final int DEFAULT_TYPE = GL11.GL_TEXTURE_2D;
    private static final List<Texture> TEXTURES = new ArrayList<Texture>();

    private int id;
    private GameFile file;
    private Vector2f scale = new Vector2f(1);
    private int type;
    private int unit = 0;

    public Texture(GameFile file, int unit, boolean calibrate){
        this(file, DEFAULT_TYPE, unit, calibrate);
    }

    public Texture(GameFile file, int type, int unit, boolean calibrate){
        this(file, new Vector2f(1), type, unit, calibrate);
    }

    public Texture(GameFile file, Vector2f scale, int type, int unit, boolean calibrate){
        this(file, -1, scale, type, unit, calibrate);
        create();
    }

    public Texture(GameFile file, int id, Vector2f scale, int type, int unit, boolean calibrate){
        this.id = id;
        this.file = file;
        this.scale.set(scale);
        this.type = type;
        this.unit = unit;
        if(calibrate){
            Texture.calibrate(type, USE_MIPMAP);
        }
        TEXTURES.add(this);
    }

    public void create(){
        id = GL11.glGenTextures();
    }

    public void bind(){
        bind(unit);
    }

    public void bind(int offset){
        GL15.glActiveTexture(GL13.GL_TEXTURE0 + offset);
        GL11.glBindTexture(type, id);
    }

    public void unbind(){
        GL11.glBindTexture(type, 0);
    }

    public int getId(){
        return id;
    }

    public GameFile getFile(){
        return file;
    }

    public int getType(){
        return type;
    }

    public int getUnit(){
        return unit;
    }

    public Vector2f getScale(){
        return scale;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setUnit(int unit){
        if(unit < 0){
            throw new IllegalArgumentException("A texture unit cannot be lower than 0");
        }
        this.unit = unit;
    }

    public void setScale(Vector2f scale){
        this.scale.set(scale);
    }

    public static void calibrate(int type, boolean useMipmap){
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        if(useMipmap){
            GL30.glGenerateMipmap(type);
            GL11.glTexParameteri(type, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(type, GL15.GL_TEXTURE_LOD_BIAS, LOD_BIAS);
        }else{
            GL11.glTexParameteri(type, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        }
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
    }
    
    public static Texture loadFromSTBI(GameFile file, int size){
        int[] x = new int[1];
        int[] y = new int[1];
        int[] component = new int[1];
        ByteBuffer image = STBImage.stbi_load_from_memory(file.getByteBuffer(size), x, y, component, 4);
        if(image == null){
            throw new RuntimeException("Could not load Image from " + file.getPath() + " " + STBImage.stbi_failure_reason() + "\n");
        }
        Texture texture = new Texture(file, 0, USE_MIPMAP);
        GL11.glBindTexture(texture.type, texture.id);
        int width = x[0];
        int height = y[0];
        GL13.glTexImage2D(texture.type, 0, GL13.GL_RGBA8, width, height, 0, GL13.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        calibrate(texture.type, USE_MIPMAP);
        return texture;
    }

}
