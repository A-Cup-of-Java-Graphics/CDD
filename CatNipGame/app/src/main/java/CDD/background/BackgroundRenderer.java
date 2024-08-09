package CDD.background;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import CDD.Camera;
import CDD.models.TexturedModel;
import CDD.sprite.Sprite;
import CDD.util.GameMaths;

public class BackgroundRenderer {

    private BackgroundShader shader = new BackgroundShader();

    /**
     * Since there will only ever be 1 background at a time, 
     * the BackgroundRenderer does not need a batching system
     * like the SpriteRenderer.
     */
    private Sprite background;

    /**
     * 
     * Constructor for BackgroundRenderer.</br>
     * <br>
     * Since the game is in 2D, and it is unlikely for the camera to change
     * settings, we can simply funnel its Projection Matrix into the shader 
     * once, and never again, since the Uniform variables in GLSL will not change
     * value unless information is explicitely funneled into them via OpenGL.</br>
     * <br>
     * Hence, the effect of {@link BackgroundShader#setProjection(org.joml.Matrix4f)}
     * will be effective until the end of the game.
     * 
     * @param camera
     */
    public BackgroundRenderer(Camera camera){
        setProjection(camera);
    }

    public void setProjection(Camera camera){
        shader.start();
        shader.setProjection(camera.getOrthographic());
        shader.stop();
    }

    /**
     * 
     * Renders the background.
     * 
     * Since the background is static in motion, we want it to
     * not move when the camera is moving. To achieve that,
     * when we funnel the Camera View Matrix into the GLSL
     * shaders via {@link BackgroundShader#setView(Matrix4f)},
     * we want to send in an Identity Matrix, essentially
     * pretending that the Camera is never moving. 
     * </br><br>
     * However, due to the background also being a sprite, certain 
     * objects will also be rendered behind it, which is unideal,
     * since the background is meant to be at the furthest back,
     * behind every other renderable object.
     * </br><br>
     * To combat this, we disable the OpenGL depth mask 
     * temporarily, by using {@link GL11#glDepthMask(boolean)},
     * and enable it again after the background has been rendererd,
     * via the same method.
     * </br><br>
     * However, since the depth mask has now lost its effect,
     * objects will be rendered depending on the order of their
     * rendering. 
     * </br><br>
     * The first one to be rendered, will be rendered at the back,
     * and any other object that is rendered next, will be stacked on
     * top of the render output.
     * </br><br>
     * Due to this, we must make sure that the background is the first one to be rendered,
     * so that it is placed constantly at the furthest back.
     * 
     */
    public void render(){
        if(background != null){
            GL11.glDepthMask(false);
            shader.start();
            shader.setView(GameMaths.IDENTITY);
            background.getModel().bind();
            if(background.getModel() instanceof TexturedModel texturedModel)
                shader.setTexture0(texturedModel.getTexture());
            shader.setModel(background.getTransformation());
            background.getModel().render();
            background.getModel().unbind();
            shader.stop();
            GL11.glDepthMask(true);
        }
    }

    /**
     * 
     * Sets the background for this renderer.
     * 
     * @param background - The new background to be rendered.
     */
    public void setBackground(Sprite background){
        this.background = background;
    }
    
}
