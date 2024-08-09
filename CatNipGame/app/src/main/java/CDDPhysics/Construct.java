package CDDPhysics;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.sprite.Sprite;
import CDDPhysics.collision.Collider;

public class Construct extends GameObject {

    public Construct(Sprite sprite, Collider collider, Vector3f position, Vector2f scale, float rotation, float mass) {
        super(sprite, collider, position, scale, rotation, mass, false);
        //TODO Auto-generated constructor stub
    }
    
}
