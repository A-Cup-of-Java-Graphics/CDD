package CDD.font;

import org.joml.Vector2i;

import CDD.shape.Shapes;

public class Glyph {

    private Vector2i position;
    private Vector2i bounds;
    public char c;

    private float[] textureCoords;
    private float[] vertices;

    public Glyph(int x, int y, int width, int height){
        this(new Vector2i(x, y), new Vector2i(width, height));
    }

    public Glyph(Vector2i position, Vector2i bounds){
        this.position = position;
        this.bounds = bounds;
    }

    public Vector2i getPosition() {
        return position;
    }

    public Vector2i getBounds() {
        return bounds;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] calculateTextureCoords(int width, int height){
        float x0 = position.x / (float) width;
        float x1 = (position.x + bounds.x) / (float) width;
        float y0 = position.y / (float) height;
        float y1 = (position.y - bounds.y) / (float) height;
        textureCoords = new float[]{
            x0, y1,
            x0, y0,
            x1, y1,
            x1, y0
        };
        return textureCoords;
    }

    public float[] calculateVertices(int width, int height){
        vertices = new float[4 * 2];
        for(int i = 0; i < 4; i++){
            float x = ((float) bounds.x) * Shapes.SQUARE_POS_STRIPS[i * 3];
            float y = ((float) bounds.y) * Shapes.SQUARE_POS_STRIPS[i * 3 + 1];
            vertices[i * 2] = x * bounds.y / height;
            vertices[i * 2 + 1] = y * bounds.y / height;
        }
        return vertices;
    }
    
}
