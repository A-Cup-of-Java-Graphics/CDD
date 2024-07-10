package CDD.components;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.Set;
import java.util.HashSet;

/**
 * 
 * Vao class : VertexArrayObject
 * 
 * A VertexArrayObject is essentially an array of 16 vertex attributes.
 * 
 * Each attribute is a separate information about the vertices.
 * 
 * An attribute can range from a vertex' positions, to its normals, or
 * even to its color. 
 * 
 * Each VAO represents an individual mesh inside of OpenGL.
 * A VAO is what connects the OpenGL code with the GLSL shaders.
 * 
 * Each VAO attribute can be bound and linked into the shaders pipeline,
 * thus sending information for each vertex into the Vertex Shader.
 * 
 */
public class Vao {

    private static final int BYTES_PER_FLOAT = 4;

    private static Set<Vao> vaos = new HashSet<Vao>();

    /**
     * An array of 16 attributes, of which only 4 is ever gonna get used remotely
     */
    private Vbo[] vbos = new Vbo[16];
    /**
     * The index buffer. See {@link ACJ.components.Ebo}
     */
    private Ebo ebo;
    private int id;
    /**
     * The amount of indices inside this mesh.
     */
    private int indexCount;
    /**
     * The amount of non-repeating vertices in this mesh.
     */
    private int vertexCount;

    /**
     * 
     * The basic constructor for a VAO. It should always be constructed this way.
     * 
     * @param vertexCount - The amount of vertices in the mesh that this VAO represents.
     */
    public Vao(int vertexCount){
        this.vertexCount = vertexCount;
        create();
    }

    /**
     * 
     * Prompts LWJGL to create a OpenGL VertexArrayObject.</br>
     * <br>Should never be used outside of the constructor.
     * 
     */
    private void create(){
        id = GL30.glGenVertexArrays();
        vaos.add(this);
    }

    /**
     * 
     * Binds the VertexArrayObject.</br>
     * <br>Should always be used before {@link Vao#bindAttribute(int, int, int, boolean, int, int)},
     * {@link Vao#createAttribute(int, int, float[])}, {@link Vao#createIndexBuffer(int[])}, {@link Vao#storeData(int[], float[][])}, and
     * {@link Vao#storeData(int[], int[], float[][])}.
     * 
     */
    public void bind(){
        GL30.glBindVertexArray(id);
    }

    /**
     * 
     * Unbinds the currently bound VAO.</br>
     * <br>Is optional, since binding a VAO will automatically unbound the previous one.
     * 
     */
    public void unbind(){
        GL30.glBindVertexArray(0);
    }

    /**
     * 
     * Binds a VBO inside of the VAO to the attributes pipeline.
     * Essentially connects a VBO to a shader.
     * 
     * @param attrNum - The index/number of the attribute, the position that the VBO possesses inside of the VAO. Should be between 0 - 15
     * @param attrSize - The number of data elements per vertex. E.g. for a 3D position, there is x, y, and z, so the attrSize would be 3.
     * @param dataType - The data type of the specific attribute. One of GL_FLOAT, GL_INT, GL_SHORT, GL_LONG, GL_DOUBLE, GL_UNSIGNED_INT, GL_UNSIGNED_BYTE, GL_HALF_FLOAT
     * @param normalized - If the data will be normalized. 
     * @param bytesPerVertex - Number of bytes per vertex. This is equal to attrSize * bytesPerDataType
     * @param offset - Whether or not this attribute should start with an offset, and if so, how much.
     */
    public void bindAttribute(int attrNum, int attrSize, int dataType, boolean normalized, int bytesPerVertex, int offset){
        GL30.glVertexAttribPointer(attrNum, attrSize, dataType, normalized, bytesPerVertex, offset);
    }

    /**
     * 
     * Creates an Index Buffer for the VAO.</br>
     * <br>Replaces the old Index Buffer if an old one exists.
     * 
     * @param data
     * @return
     */
    public Ebo createIndexBuffer(int[] data){
        ebo = new Ebo(data, GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, GL11.GL_INT);
        ebo.bind();
        ebo.store();
        ebo.unbind();
        indexCount = data.length;
        return ebo;
    }

    /**
     * 
     * Creates a new VBO in order to represent a new attribute for the mesh.
     * 
     * @param attrNum - The position that this VBO will take inside of the VAO
     * @param stride - How many elements to skip within the buffer until a new vertex begins.
     * @param data - The data to be transferred into the new VBO.
     * @return
     */
    public Vbo createAttribute(int attrNum, int stride, float[] data){
        Vbo vbo = new Vbo(data, GL15.GL_ARRAY_BUFFER, stride, GL15.GL_STATIC_DRAW, GL11.GL_FLOAT);
        vbo.bind();
        vbo.store();
        bindAttribute(attrNum, stride, vbo.getDataType(), false, stride * BYTES_PER_FLOAT, 0);
        vbo.unbind();
        vbos[attrNum] = vbo;
        return vbo;
    }

    /**
     * 
     * Enables the specified vertex attribute. 
     * Essentially tells OpenGL that this attribute will be used in the shader,
     * so enable it and funnel the data that it is connected to into the shader
     * 
     * @param attrNum
     */
    public void enableAttribute(int attrNum){
        GL20.glEnableVertexAttribArray(attrNum);
    }

    /**
     * <p>
     * Essentially an unbinding code for the vertex attribute.</br>
     * <br>Sometimes, different shaders and renderers use different sets of attributes.</p>
     * 
     * <p><font color="green"><br>E.g. EntityRenderer and EntityShader uses attributes 1,2 and 3 of Entity, but 
     * FlatRenderer and FlatShader may only use attributes 1 and 2 of Entity.</font></p>
     * 
     * <p>
     * <br>The disabling code is not inherently needed, unless a case like the above happens,
     * where different shader-renderer pairings use vastly different sets of attributes.
     * Otherwise, the disableAttribute functions mostly the same as any other unbinding code,
     * only for aesthetic purposes.
     * </p>
     * @param attrNum
     */
    public void disableAttribute(int attrNum){
        GL20.glDisableVertexAttribArray(attrNum);
    }

    /**
     * 
     * Executes {@link Vao#enableAttribute(int)} for all attributes inside the VAO</br>
     * <br>See {@link Vao#enableAttribute(int)} for more info.
     * 
     */
    public void enableAttributes(){
        for(int i = 0; i < vbos.length; i++){
            Vbo vbo = vbos[i];
            if(vbo != null){
                enableAttribute(i);
            }
        }
    }

    /**
     * 
     * Executes {@link Vao#disableAttribute(int)} for all attributes inside the VAO</br>
     * <br>See {@link Vao#disableAttribute(int)} for more info.
     * 
     */
    public void disableAttributes(){
        for(int i = 0; i < vbos.length; i++){
            Vbo vbo = vbos[i];
            if(vbo != null){
                disableAttribute(i);
            }
        }
    }

    /**
     * Gets the Index Buffer (EBO) for this Vao
     * 
     * @return - The Index Buffer for this Vao
     */
    public Ebo getEbo(){
        return ebo;
    }

    /**
     * Gets the amount of indices required for this VAO mesh
     * 
     * @return - The amount of indices inside the {@link Vao#getEbo()}
     */
    public int getIndexCount(){
        return indexCount;
    }

    /**
     * 
     * Finds all the strides for every data array
     * 
     * @param datas - The data arrays
     * @return - An array containing all the strides for every data array. The strides are aligned with the data arrays.
     */
    public int[] findStrides(float[]...datas){
        int[] strides = new int[datas.length];
        for(int i = 0; i < datas.length; i++){
            strides[i] = datas[i].length / vertexCount;
        }
        return strides;
    }

    /**
     * 
     * Stores the given indices and data arrays into the VAO,
     * esentially making the VAO a representation of the data inside these arrays.
     * 
     * @param indices - The indices for the mesh
     * @param datas - An array of data arrays that represent the mesh's vertex attributes
     */
    public void storeData(int[] indices, float[]...datas){
        storeData(indices, findStrides(datas), datas);
    }

    /**
     * 
     * Stores the given indices and data arrays into the VAO,
     * esentially making the VAO a representation of the data inside these arrays.</br>
     * <br>
     * Essentially the same as {@link Vao#storeData(int[], float[]...)} but with the additional
     * requirement for a set of strides
     * 
     * @param indices - The indices for the mesh
     * @param strides - The strides of each data array
     * @param datas - An array of data arrays that represent the mesh's vertex attributes
     */
    public void storeData(int[] indices, int[] strides, float[]...datas){
        for(int i = 0; i < datas.length; i ++){
            createAttribute(i, strides[i], datas[i]);
        }
        createIndexBuffer(indices);
    }

    /**
     * 
     * Deletes the VAO along with all its VBOs and its EBO
     * 
     * Should be part of clean up process
     * 
     */
    public void delete(){
        GL30.glDeleteVertexArrays(id);
        for(Vbo vbo : vbos){
            if(vbo != null){
                vbo.delete();
            }
        }
        if(ebo != null){
            ebo.delete();
        }
    }

    /**
     * 
     * Executes {@link Vao#delete()} on all Vaos created
     * 
     */
    public void cleanUp(){
        for(Vao vao : vaos){
            vao.delete();
        }
    }

    

    //its k just do it
}//for me to run the gradle build i need to downgrade my jdk to 1.17. im not sure if it requires a restart