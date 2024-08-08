package CDD.models;

import java.util.Properties;

import CDD.texture.Texture;

public class TexturedModel extends Model {
    private Texture texture;

    public TexturedModel(Model model, Texture texture){
        super(model.getVao(), model.getDrawMode(), model.isUsingElements());
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

    public Properties store(String prefix, Properties properties){
        String id = toString();
        properties.setProperty(prefix + "texturedModel", id);
        properties.setProperty(prefix + "texturedModel." + id + ".texture", texture.toString());
        properties.setProperty(prefix + "texturedModel." + id + ".vao", getVao().toString());
        properties.setProperty(prefix + "texturedModel." + id + ".drawMode", "" + getDrawMode());
        properties.setProperty(prefix + "texturedModel." + id + ".usingElements", "" + isUsingElements());
        return properties;
    }
    
}
