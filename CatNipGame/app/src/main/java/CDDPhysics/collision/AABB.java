package CDDPhysics.collision;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDDPhysics.GameObject;

public class AABB extends Collider {

    public AABB(Vector2f position, Vector2f bounds, GameObject host){
        this.position = position;
        this.bounds = bounds;
        this.host = host;
        this.vertices = calculateVertices();
        this.edges = calculateEdges();
        this.normals = calculateNormals();
    }

    protected Vector2f[] calculateVertices(){
        Vector2f[] vertices = new Vector2f[]{
            new Vector2f(position.x - bounds.x, position.y + bounds.y), 
            position.add(bounds, new Vector2f()),
            new Vector2f(position.x + bounds.x, position.y - bounds.y), 
            position.sub(bounds, new Vector2f())
        };
        return vertices;
    }

    protected Edge[] calculateEdges(){
        Edge[] edges = new Edge[4];
        edges[0] = new Edge(vertices[0], vertices[1]);
        edges[1] = new Edge(vertices[1], vertices[2]);
        edges[2] = new Edge(vertices[2], vertices[3]);
        edges[3] = new Edge(vertices[3], vertices[0]);
        return edges;
    }

    protected Vector2f[] calculateNormals(){
        Vector2f[] normals = new Vector2f[edges.length];
        for(int i = 0; i < edges.length; i ++){
            Edge edge = edges[i];
            Vector2f dir = edge.getDirection();
            Vector3f d = new Vector3f(dir.x, dir.y, 0).rotateZ((float) Math.PI/2f);
            normals[i] = new Vector2f(d.x, d.y).normalize();
        }
        return normals;
    }

    public boolean intersects(Edge edge){
        for(Edge e : edges){
            if(e.intersects(edge)){
                System.out.println("AABB");
                return true;
            }
        }
        return contains(edge.getOrigin()) || contains(edge.getEnd());
    }

    public boolean contains(Vector2f point){
        if(point.x > position.x - bounds.x && point.x < position.x + bounds.x){
            if(point.y > position.y - bounds.y && point.y < position.y + bounds.y){
                return true;
            }
        }
        return false;
    }

    public boolean contains(Edge edge){
        return contains(edge.getOrigin()) && contains(edge.getEnd());
    }

    public void setPosition(Vector2f position){
        Vector2f dif = position.sub(this.position, new Vector2f());
        move(dif);
    }

    @Override
    public void rotate(float rotation) {
        // TODO Auto-generated method stub
        //Doesn't do anything cuz its AABB
    }

    @Override
    public void scale(Vector2f scale) {
        // TODO Auto-generated method stub
        
    }
    
}
