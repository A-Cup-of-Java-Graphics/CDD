package CDD;

public class Shapes {

    public static final float[] SQUARE_POS = new float[]{
        0.5f, 0.5f, 0,
        0.5f, -0.5f, 0,
        -0.5f, -0.5f, 0,
        -0.5f, 0.5f, 0
    };

    public static final float[] SQUARE_TEXTURE_COORDS = new float[] {
        1, 0,
        1, 1,
        0, 1,
        0, 0
    };

    public static final int[] SQUARE_INDICES = new int[]{
        0, 1, 3, 1, 2, 3
    };
    
}
