package CDD.font;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.gui.GUI;
import CDD.models.TexturedModel;

public class Symbol extends GUI {
    private Glyph glyph;

    private Text text;

    private char character;
    
    public Symbol(char character, Text text, TexturedModel model, Glyph glyph, Vector3f color, Vector3f position, Vector2f scale, float rotation){
        super(
            model, 
            new Vector2f(
                glyph.getBounds().x, 
                glyph.getBounds().y)
            .div(2)
            .mul(glyph.getBounds().y / text.getFont().getHeight()), 
            position, 
            color, 
            scale, 
            rotation);
        this.glyph = glyph;
        this.text = text;
        this.character = character;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public char getCharacter(){
        return character;
    }

    public void setCharacter(char character){
        this.character = character;
    }
    
}
