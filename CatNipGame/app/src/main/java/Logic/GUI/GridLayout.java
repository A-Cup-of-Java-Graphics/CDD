package Logic.GUI;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.gui.GUI;

public class GridLayout {

    private List<GUI> guis = new ArrayList<GUI>();

    private Vector3f position;
    private Vector2f scale;
    private Vector2f interlude;
    private int numColumns;
    private float rotation;

    public GridLayout(List<GUI> guis, Vector3f position, Vector2f scale, float rotation, int numColumns, Vector2f interlude){
        this.guis.addAll(guis);
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.numColumns = numColumns;
        this.interlude = interlude;
        placeGUIs();
    }

    public void placeGUIs(){
        Vector2f xoffset = new Vector2f(0);
        Vector2f yoffset = new Vector2f(0);
        Matrix2f rotationMat = new Matrix2f().rotate(rotation);
        for(int i = 0; i < guis.size(); i++){
            GUI gui = guis.get(i);
            gui.setRotation(rotation);
            gui.setScale(scale);
            if(i != 0 && i % numColumns == 0){
                System.out.println("LINE");
                xoffset.set(0);
                yoffset.add(0, gui.getBounds().y + interlude.y * scale.y);
                yoffset.mul(rotationMat);
            }
            Vector2f offset = xoffset.add(yoffset, new Vector2f());
            gui.setPosition(position.add(offset.x, offset.y, 0, new Vector3f()));
            Vector2f x = new Vector2f(gui.getBounds().x + interlude.x * scale.x, 0).mul(rotationMat);
            xoffset.add(x);
        }
    }

    public List<GUI> getGuis() {
        return guis;
    }

    public void setGuis(List<GUI> guis) {
        this.guis = guis;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale.set(scale);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}
