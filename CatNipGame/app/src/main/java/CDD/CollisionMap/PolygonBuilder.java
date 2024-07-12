package CDD.CollisionMap;

import java.awt.*;

public class PolygonBuilder {
    public static void CoordToPolygon(double[] xpoly, double[] ypoly) {
        // Collision Map Array's That Polygon Will Read
        int[] XPolyAsInt = {GripPipeline.findContours0Output};
        int[] YPolyAsInt = {};

        // Convert The X Array
        for (double Point : xpoly) {
            int NewXPoly = (int) Point;
            XPolyAsInt[NewXPoly] = XPolyAsInt[NewXPoly];
        }
        
        // Convert The Y Array
        for (double Point : xpoly) {
            float YDoubleAsFloat = Math.round(Point);
            int NewYPoly = (int) YDoubleAsFloat;
            YPolyAsInt[NewYPoly] = YPolyAsInt[NewYPoly];
        }
        
        // Print The Data *This Will Be Changed To Returning The Data Instead Of Printing It On The Release Version*
        Polygon poli = new Polygon(XPolyAsInt, YPolyAsInt, XPolyAsInt.length);
        System.out.println("Poli Data: " + poli);

    }
}
