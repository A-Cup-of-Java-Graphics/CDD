package CDD;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.models.TexturedModel;

public class Sprite {

    private Vector3f position;
    private Vector2f scale;
    private float rotation;
    private TexturedModel model;

    public Sprite(TexturedModel model, Vector3f position, Vector2f scale, float rotation){
        this.model = model;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Matrix4f getTransformation(){
        Matrix4f identity = new Matrix4f();
        identity.translate(position);
        identity.rotate(rotation, 0, 0, 1);
        identity.scale(scale.x, scale.y, 0);
        return identity;
    }

    public TexturedModel getTexturedModel(){
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale.set(scale);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
}
