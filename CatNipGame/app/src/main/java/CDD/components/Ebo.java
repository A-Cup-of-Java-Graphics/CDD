package CDD.components;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

/**
 * 
 * Ebo class : Element Buffer Object
 * 
 * This is the wrapper class for the LWJGL-wrapped OpenGL buffer that contains the indices
 * of a mesh. 
 * 
 * An index buffer is a buffer that contains how information on how each vertex is linked to
 * another in order to form a mesh. Every 3 elements inside of the buffer is a triangle.
 * 
 * The benefits to using an index buffer is that it allows for the VBOs containing vertex
 * information to not have to contain repeat information.
 * 
 * For instance, a VBO containing vertex positions will have 3 floats per vertex, essentially
 * making 1 vertex worth 12 bytes. If we were to not use an index buffer, then the VBO would
 * have to contain repeats of certain vertices, since OpenGL has to know how the triangles are
 * connected, which means that a vertex that is shared by N amount of triangles will have to be
 * present inside of the VBO for N times. 
 * 
 * This means that a vertex is worth 12N bytes, where the more a vertex is shared between triangles,
 * the more expensive it is.
 * 
 * This becomes an even bigger problem when there are more vertices and VBOs in existence.
 * 
 * To combat this problem, a solution was invented : the index buffer.
 * 
 * The index buffer serves as a buffer that specifically tells OpenGL how the triangles are formed,
 * and as such, the other VBOs no longer need to contain repeat vertices, they now only needed to 
 * contain every vertex once inside of them, effectively turning a vertex from 
 * 
 * N * numberOfDataPerVertex * bytesPerDataType 
 * 
 * into
 * 
 * numberOfDataPerVertex * bytesPerDataType
 * 
 * The index buffer becomes more and more efficient as the models become more complexe and as number
 * of vertices increase. 
 * 
 * An index buffer must be bound before the mesh that is associated with it can be rendered, otherwise
 * it would result in an EXCEPTION_MEMORY_ACCESS error, a C++ crash code that does not show you any 
 * JAVA stacktraces.
 * 
 */
public class Ebo {

    private int id;
    private int type, drawType, dataType;
    private IntBuffer data;

    /**
     * 
     * This is the basic constructor to create an Ebo.
     * 
     * It creates an index buffer using LWJGL calls, and then
     * stores the given data into the index buffer, again using
     * LWJGL calls.
     * 
     * The constructor will result in an Ebo wrapper class
     * that allows for facilitated operations on the LWJGL
     * index buffer.
     * 
     * @param data
     * @param type
     * @param drawType
     * @param dataType
     */
    public Ebo(int[] data, int type, int drawType, int dataType){
        this.type = type;
        this.drawType = drawType;
        this.dataType = dataType;
        setData(data);
        create();
        store();
    }

    /**
     * Replaces ID with that of a newly created Buffer.
     * 
     * As of currently, the newly created buffer does not have any properties,
     * nor data yet.
     * 
     * *Should not be called after the Ebo is initialized using the constructor.
     */
    private void create(){
        id = GL15.glGenBuffers();
    }

    /**
     * 
     * Stores the data from {@link Ebo#data} into the index buffer inside of
     * LWJGL 
     * 
     */ 
    public void store(){
        store(this.data);
    }

    /**
     * 
     * Stores an IntBuffer into the index buffer inside of LWJGL
     * 
     * Should not be used, as this method does not change {@link Ebo#data},
     * potentially causing disparities between the wrapper class and the 
     * index buffer.
     * 
     * @param buffer - the Buffer that is going to be transfered to the index buffer within LWJGL. 
     * </br><br>THE BUFFER NEEDS TO BE FLIPPED.
     */
    public void store(IntBuffer buffer){
        GL15.glBufferData(type, buffer, drawType);
    }

    /**
     * 
     * Binds the index buffer
     * 
     * Should always be used before {@link Ebo#store()} and {@link Ebo#store(IntBuffer)}
     * 
     */
    public void bind(){
        GL15.glBindBuffer(type, id);
    }

    /**
     * 
     * Unbinds the index buffer
     * 
     * Unbinding is normally optional, as binding another buffer will automatically unbind this one
     * 
     */
    public void unbind(){
        GL15.glBindBuffer(type, 0);
    }

    /**
     * 
     * Deletes the index buffer
     * 
     * Completely frees up the space that was used to house the index buffer, including all the information
     * that was inside.
     * 
     * Should not be used unless the index buffer has been deemed useless.
     * 
     * Is used during the clean up process.
     * 
     */
    public void delete(){
        GL15.glDeleteBuffers(id);
    }

    /**
     * 
     * @return IntBuffer containing the last inputted index data.
     */
    public IntBuffer getData(){
        return data;
    }

    /**
     * 
     * @return IndexCount. The capacity of the IntBuffer that contains the last inputted index data.
     */
    public int getSize(){
        return data.capacity();
    }

    /**
     * 
     * @return The exact type of buffer that this Ebo wrapper made. Is usually GL_ELEMENT_ARRAY_BUFFER (AND SHOULD ONLY BE)
     */
    public int getType(){
        return type;
    }
    
    /**
     * 
     * GL_STATIC DRAW :
     *  Use when contents wil be modified once and used many times</br>
     * <br>GL_DYNAMIC_DRAW :
     *  Use when contents will be modified repeatedly and used many times</br>
     * <br>GL_STREAM_DRAW : 
     *  Use when contents will be modified once and used at most a few times
     * 
     * @return The drawType of this buffer, one of GL_STATIC_DRAW, GL_DYNAMIC_DRAW, GL_STREAM_DRAW
     */
    public int getDrawType(){
        return drawType;
    }

    /**
     * 
     * @return the data type of this index buffer, either GL_INT, or GL_UNSIGNED_INT
     */
    public int getDataType(){
        return dataType;
    }

    /**
     * 
     * Sets {@link Ebo#data} to buffer.</br>
     * <br>Should be accompagnied by an {@link Ebo#store()} directly after.
     * 
     * @param buffer - The new buffer to replace {@link Ebo#data}
     */
    public void setData(IntBuffer buffer){
        this.data = buffer;
    }

    /**
     * 
     * Sets {@link Ebo#data} to a new buffer containing all the indices inside of data.</br>
     * <br>Should be accompagnied by an {@link Ebo#store()} directly after.
     * 
     * @param data - The array of indices to be translated into a buffer
     */
    public void setData(int[] data){
        this.data = BufferUtils.createIntBuffer(data.length);
        this.data.put(data);
        this.data.flip();
    }

    /**
     * 
     * Sets the buffer's type. 
     * 
     * GL_ELEMENT_ARRAY_BUFFER should be the only acceptable answer here, but in case anything
     * needs to be modified or we need to hack the system, I've purposefully left these openings.
     * 
     * @param type - The new type for this buffer. Should only ever be GL_ELEMENT_ARRAY_BUFFER.
     */
    public void setType(int type){
        this.type = type;
    }

    /**
     * 
     * Sets the buffer's draw type.
     * GL_STATIC DRAW :
     *  Use when contents wil be modified once and used many times</br>
     * <br>GL_DYNAMIC_DRAW :
     *  Use when contents will be modified repeatedly and used many times</br>
     * <br>GL_STREAM_DRAW : 
     *  Use when contents will be modified once and used at most a few times
     * 
     * @param drawType - The new drawType of this buffer.
     */
    public void setDrawType(int drawType){
        this.drawType = drawType;
    }

    /**
     * 
     * Sets the buffer's data type
     * 
     * Should only be either GL_INT, or GL_UNSIGNED_INT,
     * but for the sake of leaving behind openings, any number 
     * is possible, but note that GL_SHORT, GL_BYTE, or GL_UNSIGNED_BYTE,
     * are likely to cause troubles, since the data for this buffer
     * is stored inside of a {@link java.nio.IntBuffer}.
     * 
     * @param dataType - The new data type for this Buffer. Should only be GL_INT or GL_UNSIGNED_INT
     */
    public void setDataType(int dataType){
        this.dataType = dataType;
    }
    
}
