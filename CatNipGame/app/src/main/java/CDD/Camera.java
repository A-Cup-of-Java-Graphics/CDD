package CDD;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Camera{

    public Vector3f position = new Vector3f();
    public Matrix4f orthographic = new Matrix4f();
  
    public Camera(Vector3f position){
        this.position = position;
    }

    public Vector3f getPosition(){
        return this.position;
    }

    public void setPosition(Vector3f position){
        this.position.set(position);
    }

    //view matrix
    public Matrix4f getTransformation(){
        Matrix4f identity = new Matrix4f();
        identity.rotate(0, 0, 0, 0);
        identity.translate(position.negate(new Vector3f()));
        return identity;
    }

    //projection matrix
    /**
     * since the game is 2d, no projection is needed,
     * thus we use an orthographic matrix to make 
     * everything the same size, regardless of depth.
     * 
     * It is also used in GUIs
     */
    public Matrix4f getOrthographic(){
        return orthographic;
    }

    public void calculateOrthographic(float zNear, float zFar){
        if(Window.WindowHandle == MemoryUtil.NULL){
            throw new UnsupportedOperationException("Window has not been created yet, please use this method only after the window has been created");
        }
		float aspectRatio = (float) Window.WindowWidth / (float) Window.WindowHeight;
        setOrthographic(-aspectRatio, aspectRatio, 1, -1, zNear, zFar);
    }

    public void setOrthographic(float left, float right, float top, float bottom, float zNear, float zFar){
    orthographic.setOrtho(left, right, bottom, top, zNear, zFar);
    }

}