package CDD.CollisionMap;

import java.awt.Polygon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import CDD.util.GameFile;

public class CollisionMap {

    private GameFile file;
    private Map<EnumMarkerColorThresholds, List<MatOfPoint>> contours = new HashMap<EnumMarkerColorThresholds, List<MatOfPoint>>();
    private Map<EnumMarkerColorThresholds, Map<Polygon, Vector2f>> polygons = new HashMap<EnumMarkerColorThresholds, Map<Polygon, Vector2f>>();
    private int scalingValue;
    private Vector2i dimensions = new Vector2i();

    public CollisionMap(GameFile file, boolean externalOnly, int scalingValue){
        this.file = file;
        this.scalingValue = scalingValue;
        load(externalOnly);
        calculatePolygons();
    }

    private void load(boolean externalOnly){
        Mat source = GripPipeline.loadMap(file.getAbsolutePath());
        for(EnumMarkerColorThresholds color : EnumMarkerColorThresholds.values()){
            contours.put(color, GripPipeline.findContoursForColor(source, color.getThreshold(), externalOnly));
        }
        dimensions.set(GripPipeline.findImageDimensions(source));
    }

    private void calculatePolygons(){
        for(EnumMarkerColorThresholds color : EnumMarkerColorThresholds.values()){
            polygons.put(color, PolygonBuilder.CoordToPolygon(contours.get(color), scalingValue, dimensions.x, dimensions.y));
        }
    }

    public Map<Polygon, Vector2f> getPolygons(EnumMarkerColorThresholds filter){
        return polygons.get(filter);
    }
    
}
