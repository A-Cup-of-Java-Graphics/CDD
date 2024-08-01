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
        for(Edge edge : left.edges){
            if(right.intersects(edge)){
                System.out.println("PROOF");
                edges.get(left).add(edge);
            }
        }
        for(Edge edge : right.edges){
            if(left.intersects(edge)){
                System.out.println("PROVEN");
                edges.get(right).add(edge);
            }
        }
        return edges;
    }

    public Map<Collider, Vector2f> forces(){
        Map<Collider, Vector2f> forces = new HashMap<Collider, Vector2f>();
        forces.put(left, new Vector2f());
        forces.put(right, new Vector2f());
        List<Edge> leftEdges = Arrays.asList(left.edges);
        List<Edge> rightEdges = Arrays.asList(right.edges);
        float overlap = 0;
        Vector2f[] normalPair = new Vector2f[2];
        Edge[] edgePair = new Edge[2];
        for(Edge leftEdge : edges.get(left)){
            Vector2f normal = left.normals[leftEdges.indexOf(leftEdge)];
            for(Edge rightEdge : edges.get(right)){
                Vector2f ovl = leftEdge.overlap(rightEdge);
                float ovlap = Math.max(ovl.x, ovl.y);
                if(ovlap > overlap){
                    overlap = ovlap;
                    normalPair[0] = normal;
                    normalPair[1] = right.normals[rightEdges.indexOf(rightEdge)];
                    edgePair[0] = leftEdge;
                    edgePair[1] = rightEdge;
                }
            }
        }
        System.out.println("KORQ " + edges.get(left).size() + " " + edges.get(right).size());
        Vector2f distance = edgePair[0].distance(edgePair[1].getOrigin());
        Vector2f interspace = edgePair[0].distance(edgePair[1].getEnd());
        Vector2f movement = (distance.dot(normalPair[0]) > 0 ? distance : interspace);
        Vector2f vec = normalPair[1].mul(movement, new Vector2f());
        //vec = new Vector2f((float) Math.max(vec.x, 10), (float) Math.max(vec.y, 10));
        System.out.println("NORM " + normalPair[1] + " " + edgePair[0] + " " + edgePair[1] + " " + distance + " " + interspace);
        forces.put(left, normalPair[1].mul(10, new Vector2f()));
        forces.put(right, normalPair[0].mul(10, new Vector2f()));
        return forces;
    }

    public void ennactCollisionForce(){
        while(isColliding()){
            System.out.println("ISCOL");
            forces = forces();
            System.out.println(forces.get(left));
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
