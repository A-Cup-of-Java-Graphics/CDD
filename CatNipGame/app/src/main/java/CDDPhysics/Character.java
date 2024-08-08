package CDDPhysics;

import java.util.Properties;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.Camera;
import CDD.Sprite;
import CDDPhysics.collision.Collider;

public class Character extends GameObject {

    private Camera camera;
    private float speed = 1;

    public Character(Camera camera, Collider collider, Sprite sprite, Vector3f position, Vector2f scale, float rotation, float mass) {
        super(sprite, collider, position, scale, rotation, mass, true);
        this.camera = camera;
        //TODO Auto-generated constructor stub
    }

    public void interact(){

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

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public Properties store(Properties properties){
        properties.setProperty("character.position.x", "" + getPosition().x);
        properties.setProperty("character.position.y", "" + getPosition().y);
        properties.setProperty("character.position.z", "" + getPosition().z);
        properties.setProperty("character.rotation", "" + getRotation());
        properties.setProperty("character.scale.x", "" + getScale().x);
        properties.setProperty("character.scale.y", "" + getScale().y);
        properties.setProperty("character.mass", "" + getMass());
        return properties;
    }
    
}
