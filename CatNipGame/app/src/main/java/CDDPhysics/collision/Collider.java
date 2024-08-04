package CDDPhysics.collision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joml.Matrix2f;
import org.joml.Vector2f;

import CDDPhysics.GameObject;

public abstract class Collider {

    protected Map<Edge, Vector2f> sides;
    protected List<Vector2f> axis;
    protected List<Vector2f> vertices;
    protected Vector2f position;
    protected Vector2f bounds;
    protected Vector2f scale = new Vector2f(1);
    protected float rotation = 0;
    protected GameObject host;

    public void move(float x, float y){
        this.position.add(x, y);
        for(Vector2f vertex : vertices){
            vertex.add(x, y);
        }
    }
    public void move(Vector2f translation){
        move(translation.x, translation.y);
    }

    public void rotate(float rotation){
        this.rotation += rotation;
        Matrix2f rotationMat = new Matrix2f().rotate(rotation);
        for(Vector2f normal : sides.values()){
            normal.mul(rotationMat);
        }
        for(Vector2f vertex : vertices){
            vertex.mul(rotationMat);
        }
    }

    public void scale(Vector2f scale){
        Vector2f scaleDiff = scale.div(this.scale, new Vector2f());
        this.scale.set(scale);
        updateVerticesScale(scaleDiff);
        this.bounds.mul(scaleDiff);
    }

    private void updateVerticesScale(Vector2f scale){
        for(Vector2f vertex : vertices){
            vertex.mul(scale);
        }
    }

    protected abstract List<Vector2f> calculateVertices();
    protected abstract Map<Edge, Vector2f> calculateSides();
    protected List<Vector2f> calculateAxis(){
        List<Vector2f> rAxis = new ArrayList<Vector2f>();
        Collection<Edge> edges = getEdges();
        Set<Edge> falsified = new HashSet<Edge>();
        Set<Edge> checked = new HashSet<Edge>();
        for(Edge edge : edges){
            if(falsified.contains(edge)) continue;
            Vector2f axis = sides.get(edge);
            for(Edge other : edges){
                if(other.equals(edge) || falsified.contains(other) || checked.contains(other)) continue;
                Vector2f otherAxis = sides.get(other);
                if(otherAxis.x * axis.y + otherAxis.y * axis.x == 0){
                    falsified.add(other);
                }
            }
            checked.add(edge);
            rAxis.add(axis);
        }
        return rAxis;
    }

    public abstract boolean contains(Vector2f point);
    public abstract boolean contains(Edge edge);
    public abstract boolean intersects(Edge edge);

    public Vector2f getPosition(){
        return position;
    }

    public Map<Edge, Vector2f> getSides(){
        return sides;
    }

    public Collection<Edge> getEdges(){
        return sides.keySet();
    }

    public Collection<Vector2f> getNormals(){
        return sides.values();
    }

    public Collection<Vector2f> getAxis(){
        return axis;
    }

    public GameObject getHost(){
        return host;
    }

    public void setHost(GameObject host){
        this.host = host;
    }
    
}
