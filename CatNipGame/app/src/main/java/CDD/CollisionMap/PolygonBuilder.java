package CDD.CollisionMap;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.utils.Converters;

import org.joml.Vector2f;

public class PolygonBuilder {
    public static Map<Polygon, Vector2f> CoordToPolygon(List<MatOfPoint> contours, int multiplier, int width, int height) {
        Map<Polygon, Vector2f> red = new HashMap<Polygon, Vector2f>();

        //GripPipeline pipeline = new GripPipeline();
        //pipeline.process("src/main/java/org/game/map.png");

        for (MatOfPoint mat : contours) {
            List<Point> points = new ArrayList<>();
            Converters.Mat_to_vector_Point(mat, points);

            List<Integer> x = new LinkedList<>();
            List<Integer> y = new LinkedList<>();

            int xCenter = 0, yCenter = 0;

            for(Point point : points){
                int xPoint = (int) (point.x * multiplier);
                int yPoint = (int) (-point.y * multiplier);
                xCenter += xPoint;
                yCenter += yPoint;
            }

            xCenter /= points.size();
            yCenter /= points.size();

            for(Point point : points){
                int xPoint = (int) (point.x * multiplier) - xCenter;
                int yPoint = (int) (-point.y * multiplier) - yCenter;
                x.add(xPoint);
                y.add(yPoint);
            }
            xCenter -= width / 2;
            yCenter += height / 2;

            int[] arrX = x.stream().mapToInt(Integer::intValue).toArray();
            int[] arrY = y.stream().mapToInt(Integer::intValue).toArray();

            red.put(new Polygon(arrX, arrY, arrX.length), new Vector2f(xCenter, yCenter));
        }
        return red;
    }
}
