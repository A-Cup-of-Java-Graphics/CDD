package CDDPhysics;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private List<GameObject> objects = new ArrayList<GameObject>();
    private Character character;
    private Settings settings;

    public Scene(Character character){
        this.character = character;
        this.settings = new Settings(this);
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
    
}
