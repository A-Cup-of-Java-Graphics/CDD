package CDD.texture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
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

    public static final Texture NO_TEXTURE = Texture.loadFromSTBI(GameFile.readFile("CDD/textures/NO_TEXTURE.png"), false, 1024);

    private int id;
    private GameFile file;
    private Vector2f scale = new Vector2f(1);
    private int type;
    private int unit = 0;

    public Texture(GameFile file, int unit){
        this(file, DEFAULT_TYPE, unit);
    }

    public Texture(GameFile file, int type, int unit){
        this(file, new Vector2f(1), type, unit);
    }

    public Texture(GameFile file, Vector2f scale, int type, int unit){
        this(file, -1, scale, type, unit);
        create();
    }

    public Texture(GameFile file, int id, Vector2f scale, int type, int unit){
        this.id = id;
        this.file = file;
        this.scale.set(scale);
        this.type = type;
        this.unit = unit;
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

    public static void calibrate(int type, boolean useMipmap, boolean pixelArt){
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        if(useMipmap){
            GL30.glGenerateMipmap(type);
            GL11.glTexParameteri(type, GL11.GL_TEXTURE_MIN_FILTER, pixelArt ? GL11.GL_NEAREST_MIPMAP_NEAREST : GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(type, GL15.GL_TEXTURE_LOD_BIAS, LOD_BIAS);
        }else{
            GL11.glTexParameteri(type, GL11.GL_TEXTURE_MIN_FILTER, pixelArt ? GL11.GL_NEAREST : GL11.GL_LINEAR);
        }
        GL11.glTexParameteri(type, GL11.GL_TEXTURE_MAG_FILTER, pixelArt ? GL11.GL_NEAREST : GL11.GL_LINEAR);
    }
    
    public static Texture loadFromSTBI(GameFile file, boolean pixelArt, int size){
        int[] x = new int[1];
        int[] y = new int[1];
        int[] component = new int[1];
        ByteBuffer image = STBImage.stbi_load_from_memory(file.getByteBuffer(size), x, y, component, 4);
        if(image == null){
            throw new RuntimeException("Could not load Image from " + file.getPath() + " " + STBImage.stbi_failure_reason() + "\n");
        }
        Texture texture = new Texture(file, 0);
        GL11.glBindTexture(texture.type, texture.id);
        int width = x[0];
        int height = y[0];
        GL13.glTexImage2D(texture.type, 0, GL13.GL_RGBA8, width, height, 0, GL13.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        calibrate(texture.type, USE_MIPMAP, pixelArt);
        return texture;
    }

    public static Texture loadFromImage(BufferedImage image, boolean pixelArt){
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        for(int h = 0; h < height; h++){
            for(int w = 0; w < width; w++){
                int pixel = pixels[(h * width + w)];

                System.out.println((pixel >> 16) & 0xFF);
                
                buffer.put((byte) ((pixel >> 16) & 0xFF));//r
                buffer.put((byte) ((pixel >> 8) & 0xFF));//g
                buffer.put((byte) ((pixel) & 0xFF));//b
                buffer.put((byte) ((pixel >> 24) & 0xFF));//a
            }
        }
        if(buffer == null){
            throw new RuntimeException("Couldn't load image from BufferedImage " + image);
        }
        buffer.flip();
        Texture texture = new Texture(null, 0);
        System.out.println("TEXID " + texture.id);
        GL11.glBindTexture(texture.type, texture.id);
        GL13.glTexImage2D(texture.type, 0, GL13.GL_RGBA8, width, height, 0, GL13.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        calibrate(texture.type, USE_MIPMAP, pixelArt);
        return texture;
    }

    public static byte[] getDataFromImage(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			ImageIO.write(image, "PNG", baos);
			bytes = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}

    public static Texture NULL_TEXTURE(){
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, 10, 10);
        try{
            File file = new File("C:/Users/codec/white.png");
            file.createNewFile();
            ImageIO.write(image, "png", file);
        }catch(Exception e){
            e.printStackTrace();
        }
        return loadFromImage(image, false);
    }

}
