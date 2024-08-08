package CDDPhysics.collision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.joml.Vector2f;
import org.joml.Vector3f;

import CDD.util.Tuple;

public class Collision {

    private final static float COLLISION_DISTANCE_LIMIT = 10000;

    private Collider left, right;
    private Map<Collider, Set<Edge>> edges;
    private Map<Collider, Vector2f> forces;

    public Collision(Collider left, Collider right){
        this.left = left;
        this.right = right;
    }

    public void initialize(){
        edges = edges();
    }

    public Map<Collider, Set<Edge>> edges(){
        Map<Collider, Set<Edge>> edges = new HashMap<Collider, Set<Edge>>();
        edges.put(left, new HashSet<Edge>());
        edges.put(right, new HashSet<Edge>());
        for(Edge edge : left.getEdges()){
            if(right.intersects(edge)){
                edges.get(left).add(edge);
            }
        }
        for(Edge edge : right.getEdges()){
            if(left.intersects(edge)){
                edges.get(right).add(edge);
            }
        }
        return edges;
    }

    public Map<Collider, Vector2f> forces(){
        Map<Collider, Vector2f> forces = new HashMap<Collider, Vector2f>();
        forces.put(left, new Vector2f());
        forces.put(right, new Vector2f());
        float overlap = 0;
        Vector2f[] normalPair = new Vector2f[2];
        Edge[] edgePair = new Edge[2];
        for(Edge leftEdge : edges.get(left)){
            Vector2f normal = left.getSides().get(leftEdge);
            for(Edge rightEdge : edges.get(right)){
                Vector2f rightNormal = right.getSides().get(rightEdge);
                if(normal.dot(rightNormal) == 1) continue;
                Vector2f ovl = leftEdge.overlap(rightEdge);
                float ovlap = Math.max(ovl.x, ovl.y);
                if(ovlap > overlap){
                    overlap = ovlap;
                    normalPair[0] = normal;
                    normalPair[1] = rightNormal;
                    edgePair[0] = leftEdge;
                    edgePair[1] = rightEdge;
                }
            }
        }
        Vector2f distance = edgePair[0].distance(edgePair[1].getOrigin());
        Vector2f interspace = edgePair[0].distance(edgePair[1].getEnd());
        Vector2f movement = (distance.dot(normalPair[1]) > 0 ? interspace : distance).absolute();
        if(movement.x == 0 && movement.y == 0){
            movement.set(1, 1);
        }
        forces.put(left, normalPair[1].mul(movement, new Vector2f()));
        forces.put(right, normalPair[0].mul(movement, new Vector2f()));
        return forces;
    }

    public void ennactCollisionForce(){
        int i = 0;
        int numAxis = left.getAxis().size() + right.getAxis().size();
        while(i < numAxis && isColliding()){
            i++;
            forces = forces();
            if(forces.get(left).equals(0, 0) || forces.get(right).equals(0, 0)){
                break;
            }
            if(left.host != null && left.host.canMove()){
                left.host.move(forces.get(left));
            }
            if(right.host != null && right.host.canMove()){
                right.host.move(forces.get(right));
            }
        }
    }

    public boolean isColliding(){
        initialize();
        return edges.values().stream().anyMatch(v -> !v.isEmpty());
    }

    public boolean canCollide(){
        return left.position.distanceSquared(right.position) <= COLLISION_DISTANCE_LIMIT;
    }
    
}
