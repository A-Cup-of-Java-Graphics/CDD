package CDD.render;

import CDD.Camera;
import CDD.background.BackgroundRenderer;
import CDD.gui.GUIRenderer;
import CDD.sprite.SpriteRenderer;

/**
 * 
 * A combined renderer that combines all the renderers into one.
 * This way, we only need to have 1 line of the FinalRenderer 
 * rendering.
 * 
 * This class serves only as a layer of abstraction, and does not directly
 * influence anything LWJGL related.
 * 
 */
public class FinalRenderer {

    public SpriteRenderer spriteRenderer;
    public BackgroundRenderer backgroundRenderer;
    public GUIRenderer guiRenderer;

    /**
     * 
     * Initializes all the renderers
     * 
     * @param camera - The camera to render from
     */
    public FinalRenderer(Camera camera){
        spriteRenderer = new SpriteRenderer(camera);
        backgroundRenderer = new BackgroundRenderer(camera);
        guiRenderer = new GUIRenderer(camera);
    }

    public void setProjection(Camera camera){
        spriteRenderer.setProjection(camera);
        backgroundRenderer.setProjection(camera);
        guiRenderer.setProjection(camera);
    }

    /**
     * 
     * Calls render on all renderers
     * 
     * @param camera - The camera to render from
     */
    public void render(Camera camera){
        backgroundRenderer.render();
        spriteRenderer.render(camera);
        guiRenderer.render(camera);
    }
    
}
