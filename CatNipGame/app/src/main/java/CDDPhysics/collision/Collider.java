package CDDPhysics.collision;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.joml.Matrix2f;
import org.joml.Vector2f;

import CDDPhysics.GameObject;

public abstract class Collider {

    protected Edge[] edges;
    protected Vector2f[] normals;
    protected Vector2f[] vertices;
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
        for(Vector2f normal : normals){
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

    protected abstract Vector2f[] calculateVertices();
    protected abstract Edge[] calculateEdges();
    protected abstract Vector2f[] calculateNormals();

    public abstract boolean contains(Vector2f point);
    public abstract boolean contains(Edge edge);
    public abstract boolean intersects(Edge edge);

    public Vector2f getPosition(){
        return position;
    }

    public Edge[] getEdges(){
        return edges;
    }

    public Vector2f[] getNormals(){
        return normals;
    }

    public GameObject getHost(){
        return host;
    }

    public void setHost(GameObject host){
        this.host = host;
    }
    
}
