package CDDPhysics.collision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.awt.Polygon;
import java.awt.geom.Area;

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
        this.edges = calculateEdges();
        this.normals = calculateNormals();
        this.bounds = new Vector2f((float) shape.getBounds2D().getMaxX() - position.x, (float) shape.getBounds2D().getMaxY() - position.y);
    }

    protected Vector2f[] calculateVertices(){
        Vector2f[] vertices = new Vector2f[shape.npoints];
        for(int i = 0; i < shape.npoints; i++){
            System.out.println(position);
            vertices[i] = new Vector2f(shape.xpoints[i], shape.ypoints[i]).add(position);
        }
        return vertices;
    }

    protected Edge[] calculateEdges(){
        Edge[] edges = new Edge[shape.npoints];
        for(int i = 0; i < vertices.length ; i++){
            Vector2f end = i == vertices.length - 1 ? vertices[0] : vertices[i + 1];
            edges[i] = new Edge(vertices[i], end);
        }
        return edges;
    }
    
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
    }

    public boolean intersects(Edge edge){
        for(Edge e : edges){
            if(e.intersects(edge)){
                System.out.println("POLY");
                return true;
            }
        }
        return contains(edge.getOrigin()) || contains(edge.getEnd());
    }

    public boolean contains(Vector2f point){
        for(int i = 0; i < edges.length; i++){
            Vector2f dist = edges[i].distance(point);
            if(dist.dot(normals[i]) > 0){
                return false;
            }
        }
        return true;
    }

    public boolean contains(Edge edge){
        for(Edge e : edges){
            if(e.intersects(edge)){
                return false;
            }
        }
        return contains(edge.getOrigin()) && contains(edge.getEnd());
    }

}
