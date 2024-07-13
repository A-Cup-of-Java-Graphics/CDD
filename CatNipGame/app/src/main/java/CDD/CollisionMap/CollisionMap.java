package CDD.CollisionMap;

import java.awt.Polygon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import CDD.util.GameFile;

public class CollisionMap {

    private GameFile file;
    private Map<EnumMarkerColorThresholds, List<MatOfPoint>> contours = new HashMap<EnumMarkerColorThresholds, List<MatOfPoint>>();
    private Map<EnumMarkerColorThresholds, Set<Polygon>> polygons = new HashMap<EnumMarkerColorThresholds, Set<Polygon>>();
    private int scalingValue;

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
    }

    private void calculatePolygons(){
        for(EnumMarkerColorThresholds color : EnumMarkerColorThresholds.values()){
            polygons.put(color, PolygonBuilder.CoordToPolygon(contours.get(color), scalingValue));
        }
    }

    public Set<Polygon> getPolygons(EnumMarkerColorThresholds filter){
        return polygons.get(filter);
    }
    
}
