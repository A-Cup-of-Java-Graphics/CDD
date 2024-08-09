package Logic.GUI;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.gui.GUI;

public class Line {

    private List<GUI> guis = new ArrayList<GUI>();
    private Vector3f position;
    private Vector2f scale;
    private float width;
    private float rotation;

    public Line(List<GUI> guis, Vector3f position, Vector2f scale, float rotation, float width){
        this.guis.addAll(guis);
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.width = width;
    }

    public void placeGUIs(){
        Vector2f offset = new Vector2f();
        Matrix2f rotationMat = new Matrix2f().rotate(rotation);
        for(GUI gui : guis){
            placeGUI(gui, rotationMat, offset);
        }
    }

    public void addGUI(GUI gui, int index){
        guis.add(index, gui);
        Vector2f offset = new Vector2f();
        Matrix2f rotationMat = new Matrix2f().rotate(rotation);
        for(int i = index + 1; i < guis.size(); i++){
            placeGUI(guis.get(i), rotationMat, offset);
        }
    }

    private void placeGUI(GUI gui, Matrix2f rotationMat, Vector2f offset){
        Vector2f bounds = gui.getBounds();
        Vector2f off = new Vector2f(bounds.x, 0);
        off.mul(rotationMat);
        gui.setPosition(position.add(offset.x, offset.y, 0, new Vector3f()));
        gui.setScale(scale);
        gui.setRotation(rotation);
        offset.add(off);
        width = offset.length();
    }

    public float getWidth(){
        return width;
    }

    public List<GUI> getGuis() {
        return guis;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    public void setGuis(List<GUI> guis) {
        this.guis = guis;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
}
