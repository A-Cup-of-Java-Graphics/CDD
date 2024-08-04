package CDDPhysics.collision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDDPhysics.GameObject;

public class AABB extends Collider {

    public AABB(Vector2f position, Vector2f bounds, GameObject host){
        this.position = position;
        this.bounds = bounds;
        this.host = host;
        this.vertices = calculateVertices();
        this.sides = calculateSides();
        this.axis = calculateAxis();
    }

    protected List<Vector2f> calculateVertices(){
        List<Vector2f> vertices = new ArrayList<Vector2f>(Arrays.asList(new Vector2f[]{
            new Vector2f(position.x - bounds.x, position.y + bounds.y), 
            position.add(bounds, new Vector2f()),
            new Vector2f(position.x + bounds.x, position.y - bounds.y), 
            position.sub(bounds, new Vector2f())
        }));
        return vertices;
    }

    protected Map<Edge, Vector2f> calculateSides(){
        Map<Edge, Vector2f> sides = new HashMap<Edge, Vector2f>();
        sides.put(new Edge(vertices.get(0), vertices.get(1)), new Vector2f(0, 1));
        sides.put(new Edge(vertices.get(1), vertices.get(2)), new Vector2f(1, 0));
        sides.put(new Edge(vertices.get(2), vertices.get(3)), new Vector2f(0, -1));
        sides.put(new Edge(vertices.get(3), vertices.get(0)), new Vector2f(-1, 0));
        return sides;
    }
    /*
    protected Vector2f[] calculateNormals(){
        Vector2f[] normals = new Vector2f[edges.length];
        for(int i = 0; i < edges.length; i ++){
            Edge edge = edges[i];
            Vector2f dir = edge.getDirection();
            Vector3f d = new Vector3f(dir.x, dir.y, 0).rotateZ((float) Math.PI/2f);
            normals[i] = new Vector2f(d.x, d.y).normalize();
        }
        return normals;
    }*/

    public boolean intersects(Edge edge){
        for(Edge e : getEdges()){
            if(e.intersects(edge)){
                System.out.println("AABB");
                return true;
            }
        }
        return contains(edge.getOrigin()) || contains(edge.getEnd());
    }

    public boolean contains(Vector2f point){
        if(point.x >= position.x - bounds.x && point.x <= position.x + bounds.x){
            if(point.y >= position.y - bounds.y && point.y <= position.y + bounds.y){
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
