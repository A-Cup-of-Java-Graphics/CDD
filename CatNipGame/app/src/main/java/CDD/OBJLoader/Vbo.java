package CDD.OBJLoader;
//what is vao and vbo?
//ill explain on discord

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public class Vbo {

    private int id;
    private int type, stride, drawType, dataType;
    private FloatBuffer data;

    public Vbo(int type){
        this.type = type;
        this.stride = stride;
        this.drawType = drawType;
        this.dataType = dataType;
        setData(data);
        create();
    }

    private void create(){
        id = GL15.glGenBuffers();
    }

    public void store(){
        store(this.data);
    }

    public void store(FloatBuffer buffer){
        GL15.glBufferData(type, buffer, drawType);
    }

    public void bind(){
        GL15.glBindBuffer(type, id);
    }

    public void unbind(){
        GL15.glBindBuffer(type, 0);
    }

    public void delete(){
        GL15.glDeleteBuffers(id);
    }

    public FloatBuffer getData(){
        return data;
    }

    public int getSize(){
        return data.capacity();
    }

    public int getType(){
        return type;
    }

    public int getStride(){
        return stride;
    }

    public int getDrawType(){
        return drawType;
    }

    public int getDataType(){
        return dataType;
    }

    /**
     * simply a setter for the data floatbuffer
     * 
     * it is useless until I add the add(float[] data) functions
     * @param buffer
     */
    public void setData(FloatBuffer buffer){
        this.data = buffer;
    }

    public void setData(float[] data){
        this.data = BufferUtils.createFloatBuffer(data.length);
        this.data.put(data);
        this.data.flip();
    }

    public void setType(int type){
        this.type = type;
    }

    public void setStride(int stride){
        this.stride = stride;
    }

    public void setDrawType(int drawType){
        this.drawType = drawType;
    }

    public void setDataType(int dataType){
        this.dataType = dataType;
    }
    
}