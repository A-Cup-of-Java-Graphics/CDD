package CDDPhysics;

import java.util.ArrayList;
import java.util.List;

import CDD.Camera;
import CDD.render.FinalRenderer;

public class Scene {

    private List<GameObject> objects = new ArrayList<GameObject>();
    private Character character;
    private Settings settings;
    private Camera camera;
    private FinalRenderer renderer;

    public Scene(Character character, Camera camera, FinalRenderer renderer){
        this.character = character;
        this.camera = camera;
        this.renderer = renderer;
        this.settings = new Settings(this);
    }

    public void render(){
        renderer.render(camera);
    }

    public List<GameObject> getObjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObjects'");
    }

    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    public void setName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }

    public void addObject(GameObject object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addObject'");
    }

    public void removeObject(GameObject object) {
        // TODO Auto-generated method stub
        objects.remove(object);
    }

    public Character getCharacter(){
        return character;
    }

    public void setCharacter(Character character){
        this.character = character;
    }

    public Settings getSettings(){
        return settings;
    }

    public Camera getCamera(){
        return camera;
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public FinalRenderer getRenderer(){
        return renderer;
    }

    public void setRenderer(FinalRenderer renderer){
        this.renderer = renderer;
    }
    
}
