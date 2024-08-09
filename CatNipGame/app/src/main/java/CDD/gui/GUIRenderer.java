package CDD.gui;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import CDD.Camera;
import CDD.font.Symbol;
import CDD.models.Model;
import CDD.models.TexturedModel;
import CDD.sprite.Sprite;
import CDD.texture.Texture;
import CDD.util.GameFile;

public class GUIRenderer {

    private GUIShader shader = new GUIShader();

    private Map<Model, List<GUI>> batches = new HashMap<Model, List<GUI>>();

    public GUIRenderer(Camera camera){
        setProjection(camera);
    }

    public void setProjection(Camera camera){
        shader.start();
        shader.setProjection(camera.getOrthographic());
        shader.stop();
    }

    public void render(Camera camera){
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        shader.start();
        for(Model model : batches.keySet()){
            prepare(model);
            shader.setHasTexture(model instanceof TexturedModel);
            if(model instanceof TexturedModel texturedModel){
                shader.setTexture0(texturedModel.getTexture());
            }
            for(GUI gui : batches.get(model)){
                shader.setModel(gui.getTransformation());
                shader.setColor(gui.getColor());
                model.render();
            }
            unprepare(model);
        }
        shader.stop();
    }

    private void prepare(Model model){
        model.bind();
    }

    private void unprepare(Model model){
        model.bind();
    }

    public void addGUI(GUI gui){
        if(batches.containsKey(gui.getModel())){
            batches.get(gui.getModel()).add(gui);
        }else{
            List<GUI> list = new ArrayList<GUI>();
            list.add(gui);
            batches.put(gui.getModel(), list);
        }
    }

    public void addGUIs(List<? extends GUI> guis){
        for(GUI gui : guis){
            addGUI(gui);
        }
    }

    public Map<Model, List<GUI>> getBatches(){
        return batches;
    }
    
}
