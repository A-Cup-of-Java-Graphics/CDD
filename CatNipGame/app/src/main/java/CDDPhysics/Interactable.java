package CDDPhysics;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.Sprite;
import CDDPhysics.collision.Collider;

public abstract class Interactable extends Construct {

    public Interactable(Sprite sprite, Collider collider, Vector3f position, Vector2f scale, float rotation,
            float mass) {
        super(sprite, collider, position, scale, rotation, mass);
        //TODO Auto-generated constructor stub
    }

    public abstract void interact(GameObject interacter);
    
}
