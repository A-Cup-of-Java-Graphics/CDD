package CDD.gui;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.models.Model;
import CDD.shape.Shapes;
import CDD.sprite.Sprite;
import CDD.texture.Texture;

public class GUI extends Sprite{

    private Vector2f bounds;

    public GUI(Vector3f position, Vector3f color, Vector2f scale, float rotation){
        super(Shapes.squareModel(), color, position, scale, rotation);
        this.bounds = new Vector2f(0.5f);
    }

    public GUI(Texture texture, Vector3f position, Vector3f color, Vector2f scale, float rotation){
        super(Shapes.texturedModel(Shapes.squareModel(), texture), color, position, scale, rotation);
        this.bounds = new Vector2f(0.5f);
    }

    public GUI(Model model, Vector2f bounds, Vector3f position, Vector3f color, Vector2f scale, float rotation){
        super(model, color, position, scale, rotation);
        this.bounds = bounds;
    }

    public Vector2f getBounds(){
        return getScale().mul(bounds, new Vector2f());
    }
    
}
