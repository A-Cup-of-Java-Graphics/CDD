package CDD;

import java.util.Properties;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.models.Model;
import CDD.models.TexturedModel;

public class Sprite {

    private Vector3f position;
    private Vector3f color;
    private Vector2f scale;
    private float rotation;
    private Model model;

    public Sprite(Model model, Vector3f color, Vector3f position, Vector2f scale, float rotation){
        this.model = model;
        this.color = color;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Sprite(Model model, Vector3f position, Vector2f scale, float rotation){
        this(model, new Vector3f(1), position, scale, rotation);
    }

    public Matrix4f getTransformation(){
        Matrix4f identity = new Matrix4f();
        identity.translate(position);
        identity.rotate(rotation, 0, 0, 1);
        identity.scale(scale.x, scale.y, 0);
        return identity;
    }

    public Model getModel(){
        return model;
    }

    public Vector3f getColor(){
        return color;
    }

    public void setColor(Vector3f color){
        this.color = color;
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

    public Properties store(String prefix, Properties properties){
        String id = toString();
        properties.setProperty(prefix + "sprite", id);
        properties.setProperty(prefix + "sprite." + id + ".texturedModel", model.toString());
        properties.setProperty(prefix + "sprite." + id + ".position.x", "" + position.x);
        properties.setProperty(prefix + "sprite." + id + ".position.y", "" + position.y);
        properties.setProperty(prefix + "sprite." + id + ".position.z", "" + position.z);
        properties.setProperty(prefix + "sprite." + id + ".scale.x", "" + scale.x);
        properties.setProperty(prefix + "sprite." + id + ".scale.y", "" + scale.y);
        properties.setProperty(prefix + "sprite." + id + ".rotation", "" + rotation);
        return properties;
    }
    
}
