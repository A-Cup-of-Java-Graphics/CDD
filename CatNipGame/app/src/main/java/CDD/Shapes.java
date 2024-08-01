package CDD;

import java.awt.Polygon;

import org.joml.Vector2f;

import CDD.components.Vao;
import CDDPhysics.collision.Edge;

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

    public static Vao polygon(Polygon polygon){
			float[] points = new float[polygon.npoints * 3];
			float[] textureCoords = new float[polygon.npoints * 2];
			for(int i = 0; i < polygon.npoints; i++){
				points[i * 3] = polygon.xpoints[i];
				points[i * 3 + 1] = polygon.ypoints[i]; 
				points[i * 3 + 2] = 0;
				textureCoords[i * 2] = 1;
				textureCoords[i * 2 + 1] = 1;
			}
            Vao vao = new Vao(polygon.npoints);
            vao.bind();
            vao.storeData(new int[0], points, textureCoords);
            return vao;
    }

    public static Vao line(Edge edge){
        Vao vao = new Vao(2);
        vao.bind();
        vao.storeData(new int[2], new float[]{edge.getOrigin().x, edge.getOrigin().y, 0, edge.getEnd().x, edge.getEnd().y, 0}, new float[]{1, 1, 1, 1});
        return vao;
    }
    
}
