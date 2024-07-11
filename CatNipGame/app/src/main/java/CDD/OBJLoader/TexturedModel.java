package CDD.OBJLoader;

import CDD.texture.Texture;

public class TexturedModel extends RawModel {
    private Texture texture;

    public TexturedModel(RawModel model, Texture texture){
        super(RawModel.getVao());
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