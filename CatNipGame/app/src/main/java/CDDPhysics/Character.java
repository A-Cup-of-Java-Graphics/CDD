package CDDPhysics;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import CDD.Camera;
import CDD.Sprite;

public class Character extends GameObject {

    private Camera camera;

    public Character(Camera camera, Sprite sprite, Vector3f position, Vector2f scale, float rotation, float mass) {
        super(sprite, position, scale, rotation, mass);
        this.camera = camera;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void move(float x, float y){
        super.move(x, y);
        camera.getPosition().x = getPosition().x;
        camera.getPosition().y = getPosition().y;
    }

    public Camera getCamera(){
        return camera;
    }
    
}
