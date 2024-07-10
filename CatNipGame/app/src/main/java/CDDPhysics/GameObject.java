package CDDPhysics;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.Sprite;

public class GameObject {

    private Sprite sprite;

    private Vector3f position;
    private Vector3f lastPosition;
    private Vector2f scale;
    private float rotation;
    private float mass;

    public GameObject(Sprite sprite, Vector3f position, Vector2f scale, float rotation, float mass){
        this.sprite = sprite;
        this.position = position;
        lastPosition = new Vector3f(position);
        this.scale = scale;
        this.rotation = rotation;
        this.mass = mass;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Vector3f getPosition(){
        return position;
    }

    public Vector2f getScale(){
        return scale;
    }

    public Vector3f getLastPosition(){
        return lastPosition;
    }

    public float getRotation(){
        return rotation;
    }

    public float getMass(){
        return mass;
    }

    public void move(Vector2f translation){
        move(translation.x, translation.y);
    }

    public void move(float x, float y){
        this.lastPosition.set(position);
        this.position.add(x, y, 0);
        this.sprite.getPosition().x = position.x;
        this.sprite.getPosition().y = position.y;
    }

    public void scale(Vector2f scale){
        this.scale.set(scale);
        this.sprite.setScale(scale);
    }

    public void rotate(float rotation){
        this.rotation += rotation;
        this.sprite.setRotation(this.rotation);
    }
    
}
