package CDDPhysics.collision;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.SynthDesktopIconUI;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDDPhysics.GameObject;

public class PolygonBoundingBox extends Collider {

    private Polygon shape;

    public PolygonBoundingBox(Vector2f position, Polygon shape, GameObject host){
        this.position = position;
        this.shape = shape;
        this.host = host;
        this.vertices = calculateVertices();
        this.sides = calculateSides();
        this.axis = calculateAxis();
        this.bounds = new Vector2f((float) shape.getBounds2D().getMaxX() - position.x, (float) shape.getBounds2D().getMaxY() - position.y);
    }

    @Override
    protected List<Vector2f> calculateVertices(){
        List<Vector2f> vertices = new ArrayList<Vector2f>();
        for(int i = 0; i < shape.npoints; i++){
            System.out.println(position);
            vertices.add(new Vector2f(shape.xpoints[i], shape.ypoints[i]).add(position));
        }
        return vertices;
    }

    @Override
    protected Map<Edge, Vector2f> calculateSides(){
        Map<Edge, Vector2f> sides = new HashMap<Edge, Vector2f>();
        int sum = 0;
        for(int i = 0; i < vertices.size() ; i++){
            Vector2f vertex = vertices.get(i);
            Vector2f end = i == vertices.size() - 1 ? vertices.getFirst() : vertices.get(i + 1);
            Edge edge = new Edge(vertex, end);
            sum += (end.x - vertex.x) * (end.y + vertex.y);
            Vector2f normal = edge.getDirection().normalize().perpendicular();
            sides.put(edge, normal);
        }
        if(sum > 0){
            for(Vector2f normal : sides.values()){
                normal.mul(-1f);
            }
        }
        return sides;
    }
    
    /*
    protected Vector2f[] calculateNormals(){
        Vector2f[] normals = new Vector2f[edges.length];
        int sum = 0;
        for(int i = 0; i < shape.npoints; i++){
            int nextIndex = (i == shape.npoints - 1) ? 0 : i + 1;
            sum += (shape.xpoints[nextIndex] - shape.xpoints[i]) * (shape.ypoints[nextIndex] + shape.ypoints[i]);
        }
        float sign = Math.signum(sum);
        for(int i = 0; i < edges.length; i ++){
            Edge edge = edges[i];
            Vector2f dir = edge.getDirection();
            Vector3f d = new Vector3f(dir.x, dir.y, 0).rotateZ((float) Math.PI/2f);
            normals[i] = new Vector2f(d.x * sign, d.y * sign).normalize();
        }
        return normals;
    }*/

    @Override
    public boolean intersects(Edge edge){
        for(Edge e : getEdges()){
            if(e.intersects(edge)){
                System.out.println("POLY");
                return true;
            }
        }
        return contains(edge.getOrigin()) || contains(edge.getEnd());
    }

    @Override
    public boolean contains(Vector2f point){
        Vector2f pointRelative = point.sub(position, new Vector2f());
        return shape.contains(pointRelative.x, pointRelative.y);
    }

    @Override
    public boolean contains(Edge edge){
        for(Edge e : getEdges()){
            if(e.intersects(edge)){
                return false;
            }
        }
        return contains(edge.getOrigin()) && contains(edge.getEnd());
    }

}
