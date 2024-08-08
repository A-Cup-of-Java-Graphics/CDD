package CDDPhysics;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.Sprite;
import CDDPhysics.collision.Collider;

public class GameObject {

    private Sprite sprite;

    private Vector3f position;
    private Vector3f lastPosition;
    private Vector2f scale;
    private Vector2f speed;
    private float rotation;
    private float mass;
    private Collider collider;
    private boolean canMove;

    public GameObject(Sprite sprite, Collider collider, Vector3f position, Vector2f scale, float rotation, float mass, boolean canMove){
        this.sprite = sprite;
        this.position = position;
        this.lastPosition = new Vector3f(position);
        this.scale = scale;
        this.rotation = rotation;
        this.mass = mass;
        this.collider = collider;
        this.canMove = canMove;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Vector3f getPosition(){
        return position;
    }

    public Vector3f getLastPosition(){
        return lastPosition;
    }

    public Vector2f getScale(){
        return scale;
    }

    public float getRotation(){
        return rotation;
    }

    public float getMass(){
        return mass;
    }

    public Collider getCollider(){
        return collider;
    }

    public void move(Vector2f translation){
        move(translation.x, translation.y);
    }

    public void move(float x, float y){
        this.lastPosition.set(this.position);
        this.position.add(x, y, 0);
        this.sprite.getPosition().x = position.x;
        this.sprite.getPosition().y = position.y;
        this.collider.move(x, y);
    }

    public void scale(Vector2f scale){
        this.scale.set(scale);
        this.sprite.setScale(scale);
        this.collider.scale(scale);
    }

    public void rotate(float rotation){
        this.rotation += rotation;
        this.sprite.setRotation(this.rotation);
        this.collider.rotate(rotation);
    }

    public boolean canMove(){
        return canMove;
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }
    
}
