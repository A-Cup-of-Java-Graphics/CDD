package CDD.models;

import CDD.texture.Texture;

public class TexturedModel extends Model {
    private Texture texture;

    public TexturedModel(Model model, Texture texture){
        super(model.getVao());
        this.texture = texture;
    }

    public void bind(){
        super.bind();
        texture.bind();
    }

    public void unbind(){
        super.unbind();
        texture.unbind();
    }

    public Texture getTexture(){
        return texture;
    }
    
}
