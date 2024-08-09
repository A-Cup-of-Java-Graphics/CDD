package CDD.shape;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import CDD.components.Vao;
import CDD.models.Model;
import CDD.models.TexturedModel;
import CDD.texture.Texture;
import CDD.util.Tuple;
import CDDPhysics.collision.Edge;

public class Shapes {

    private static final Map<Tuple<Texture, Model>, TexturedModel> texturedModels = new HashMap<Tuple<Texture, Model>, TexturedModel>();

    public static final float[] SQUARE_POS = new float[]{
        0.5f, 0.5f, 0,
        0.5f, -0.5f, 0,
        -0.5f, -0.5f, 0,
        -0.5f, 0.5f, 0
    };

    public static final float[] SQUARE_TEXTURE_COORDS_STRIPS = new float[]{
        0, 0,
        0, 1,
        1, 0,
        1, 1
    };

    public static final float[] SQUARE_POS_STRIPS = new float[]{
        -0.5f, 0.5f, 0,
        -0.5f, -0.5f, 0,
        0.5f, 0.5f, 0,
        0.5f, -0.5f, 0
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

    private static Vao square;
    private static Model squareModel;

    public static void createDefaultVaos(){
        square = new Vao(4).bind().storeDataArrays(SQUARE_POS_STRIPS, SQUARE_TEXTURE_COORDS_STRIPS);
    }

    public static void createDefaultModels(){
        squareModel = new Model(square, GL11.GL_TRIANGLE_STRIP, false);
    }

    public static Vao square(){
        if(square != null){
            return square;
        }
        throw new RuntimeException("Default VAOs have not yet been initialized!");
    }

    public static Model squareModel(){
        if(squareModel != null){
            return squareModel;
        }
        throw new RuntimeException("Default Models have not yet been initialized!");
    }

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
            vao.storeDataArrays(points, textureCoords);
            return vao;
    }

    public static Vao line(Edge edge){
        Vao vao = new Vao(2);
        vao.bind();
        vao.storeDataArrays(new float[]{edge.getOrigin().x, edge.getOrigin().y, 0, edge.getEnd().x, edge.getEnd().y, 0}, new float[]{1, 1, 1, 1});
        return vao;
    }

    public static Model model(Vao vao, int drawMode){
        return new Model(vao, drawMode, vao.getEbo() != null);
    }

    public static TexturedModel texturedModel(Model model, Texture texture){
        Tuple<Texture, Model> key = new Tuple<Texture, Model>(texture, model);
        return new TexturedModel(model, texture);
        /*
        if(texturedModels.containsKey(key)){
            return texturedModels.get(key);
        }else{
            return texturedModels.put(key, new TexturedModel(model, texture));
        }*/
    }
    
}
