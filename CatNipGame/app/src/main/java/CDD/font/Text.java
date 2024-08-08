package CDD.font;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Text {

    private List<Symbol> symbols = new ArrayList<Symbol>();
    private String data;

    private Vector3f color;

    private Vector3f position;
    private Vector2f scale;
    private float rotation;

    private final GameFont font;

    public Text(GameFont font, String data, Vector3f color, Vector3f position, Vector2f scale, float rotation){
        this.data = data;
        this.font = font;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.color = color;
        translateToSymbols();
    }

    public void translateToSymbols(){
        Vector2f offset = new Vector2f();
        Matrix2f rotationMat = new Matrix2f().rotate(rotation);
        for(int i = 0; i < data.length(); i++){
            char c = data.charAt(i);
            if(font.getFont().canDisplay(c)){
                Glyph glyph = font.getCharacterInfo().get((int) c);
                Vector2i bounds = glyph.getBounds();
                Vector2f off = new Vector2f(bounds.x, 0).div(2).mul((float) bounds.y / (float) font.getHeight()).mul(scale);
                off.mul(rotationMat);
                offset.add(off);
                Vector3f pos = position.add(offset.x, offset.y, 0, new Vector3f());
                symbols.add(new Symbol(c, this, font.getCharacterModels().get(c), font.getCharacterInfo().get((int) c), new Vector3f(color), pos, new Vector2f(scale), rotation));
                offset.add(off);
                continue;
            }
            offset.add(1, 0);
        }
    }

    public void update(){
        Vector2f offset = new Vector2f();
        Matrix2f rotationMat = new Matrix2f().rotate(rotation);
        for(Symbol symbol : symbols){
            Glyph glyph = font.getCharacterInfo().get((int) symbol.getCharacter());
            Vector2i bounds = glyph.getBounds();
            Vector2f off = new Vector2f(bounds.x, 0).mul(scale);
            off.mul(rotationMat);
            symbol.setPosition(position.add(offset.x, offset.y, 0, new Vector3f()));
            symbol.setColor(color);
            symbol.setScale(scale);
            symbol.setRotation(rotation);
            offset.add(off);
        }
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbol(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector3f getColor(){
        return color;
    }

    public void setColor(Vector3f color){
        this.color = color;
    }

    public GameFont getFont(){
        return font;
    }
    
}
