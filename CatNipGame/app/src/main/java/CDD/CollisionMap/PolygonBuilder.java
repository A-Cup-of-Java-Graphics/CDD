package CDD.CollisionMap;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.utils.Converters;

public class PolygonBuilder {
    public static Set<Polygon> CoordToPolygon(List<MatOfPoint> contours, int multiplier) {
        Set<Polygon> red = new HashSet<>();

        //GripPipeline pipeline = new GripPipeline();
        //pipeline.process("src/main/java/org/game/map.png");

        for (MatOfPoint mat : contours) {
            List<Point> points = new ArrayList<>();
            Converters.Mat_to_vector_Point(mat, points);

            List<Integer> x = new LinkedList<>();
            List<Integer> y = new LinkedList<>();

            points.forEach(point -> {
                x.add((int) (point.x * multiplier));
                y.add((int) (point.y * multiplier));
            });

            int[] arrX = x.stream().mapToInt(Integer::intValue).toArray();
            int[] arrY = y.stream().mapToInt(Integer::intValue).toArray();

            red.add(new Polygon(arrX, arrY, arrX.length));
        }
        return red;
    }
}
