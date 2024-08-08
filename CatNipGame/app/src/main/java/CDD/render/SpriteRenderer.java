package CDD.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CDD.Camera;
import CDD.Sprite;
import CDD.models.Model;
import CDD.models.TexturedModel;
import CDD.shader.BaseShader;

/**
 * 
 * A renderer class for sprites (2D planes)
 * 
 * Since this game is 2D, I just didn't bother with making the renderers
 * look good in the IDE, they only contain a constructor and a render
 * method.
 * 
 */
public class SpriteRenderer {

    private SpriteShader shader = new SpriteShader();

    /**
     * Batches of sprites. This makes rendering less expensive, as we can effectively recycle
     * the Vao and Texture from 1 VAO for the entire batch, and so every individual sprite
     * would only need to funnel their Model Transformation Matrix into the shader.
     */
    private Map<Model, List<Sprite>> sprites = new HashMap<Model, List<Sprite>>();

    /**
     * 
     * The constructor for SpriteRenderer.</br>
     * <br>
     * Since the game is in 2D, and it is unlikely for the camera to change
     * settings, we can simply funnel its Projection Matrix into the shader 
     * once, and never again, since the Uniform variables in GLSL will not change
     * value unless information is explicitely funneled into them via OpenGL.</br>
     * <br>
     * Hence, the effect of {@link BaseShader#setProjection(org.joml.Matrix4f)}
     * will be effective until the end of the game.
     * 
     * @param camera - The camera to render from
     */
    public SpriteRenderer(Camera camera){
        setProjection(camera);
    }

    public void setProjection(Camera camera){
        shader.start();
        shader.setProjection(camera.getOrthographic());
        shader.stop();
    }

    /**
     * 
     * Renders all the sprites
     * 
     * @param camera - The camera to render from
     */
    public void render(Camera camera){
        shader.start();
        shader.setView(camera.getTransformation());
        for(Model model : sprites.keySet()){
            prepare(model);
            if(model instanceof TexturedModel texturedModel){
                shader.setTexture0(texturedModel.getTexture());
            }
            for(Sprite sprite : sprites.get(model)){
                shader.setColor(sprite.getColor());
                shader.setModel(sprite.getTransformation());
                model.render();
            }
            unprepare(model);
        }
        shader.stop();
    }

    public void prepare(Model model){
        //nothing yet;
        model.bind();
    }

    public void unprepare(Model model){
        //nothing yet
        model.unbind();
    }

    public void addSprite(Sprite sprite){
        if(sprites.containsKey(sprite.getModel())){
            sprites.get(sprite.getModel()).add(sprite);
        }else{
            List<Sprite> list = new ArrayList<Sprite>();
            list.add(sprite);
            sprites.put(sprite.getModel(), list);
        }
    }

    public void addSprites(List<? extends Sprite> sprites){
        for(Sprite sprite : sprites){
            addSprite(sprite);
        }
    }
    
}
