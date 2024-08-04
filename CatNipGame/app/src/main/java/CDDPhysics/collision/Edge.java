package CDDPhysics.collision;

import java.awt.geom.Line2D;

import org.joml.Vector2f;

public class Edge {

    private Vector2f origin;
    private Vector2f end;
    private float rotation;

    public Edge(Vector2f origin, Vector2f end){
        this.origin = origin;
        this.end = end;
    }

    public Vector2f getDifference(){
        return end.sub(origin, new Vector2f());
    }

    public Vector2f getOrigin(){
        return origin;
    }

    public Vector2f getEnd(){
        return end;
    }

    public Vector2f getCenter(){
        return getDifference().div(2, new Vector2f()).add(origin);
    }

    public Vector2f getDirection(){
        return getDifference().normalize(new Vector2f());
    }

    public float length(){
        return getDifference().length();
    }

    public boolean intersects(Edge other){
        return Line2D.linesIntersect(other.origin.x, other.origin.y, other.end.x, other.end.y, origin.x, origin.y, end.x, end.y);
    }

    public Vector2f overlap(Edge other){
        float originProjected = other.getDirection().dot(origin);
        float endProjected = other.getDirection().dot(end);
        float min1 = Math.min(originProjected, endProjected);
        System.out.println(other.getDirection());
        System.out.println(other);
        float overlap = other.overlap(min1, min1 == originProjected ? endProjected : originProjected);
        float otherOriginProjected = getDirection().dot(other.origin);
        float otherEndProjected = getDirection().dot(other.end);
        float min2 = Math.min(otherOriginProjected, otherEndProjected);
        float overlap2 = overlap(min2, min2 == otherOriginProjected ? otherEndProjected : otherOriginProjected);
        System.out.println(overlap + " -- " + overlap2);
        System.out.println(getDirection());
        return new Vector2f(overlap, overlap2);
    }

    private float overlap(float min, float max){
        if(min == max) return 0;
        float origin = getOrigin().dot(getDirection());
        float end = origin + length();
        System.out.println(max + " " + min + " -> " + origin + " " + end);
        return Math.abs(Math.min(end, max) - Math.max(origin, min));
    }

    public Vector2f nonOverlap(Edge other){
        float originProjected = other.getDirection().dot(origin);
        float endProjected = other.getDirection().dot(end);
        float overlap = other.nonOverlap(originProjected, endProjected);
        float otherOriginProjected = getDirection().dot(other.origin);
        float otherEndProjected = getDirection().dot(other.end);
        float overlap2 = nonOverlap(otherOriginProjected, otherEndProjected);
        return new Vector2f(overlap, overlap2);
    }

    public float nonOverlap(float min, float max){
        float origin = getOrigin().length();
        float end = origin + length();
        return length() + Math.abs(max - min) - Math.max(0, Math.min(end, max)) - Math.max(origin, min);
    }

    public Vector2f project(Vector2f point){
        Vector2f dir = getDirection();
        Vector2f difference = point.sub(origin, new Vector2f());
        float dot = dir.dot(difference);
        return dir.mul(dot).add(origin);
    }

    public Vector2f distance(Vector2f point){
        Vector2f projected = project(point);
        return point.sub(projected, new Vector2f());
    }

    public float longestDistance(Edge edge, Vector2f normal){
        Vector2f dir = getDirection();

        Vector2f endDiff = edge.end.sub(origin, new Vector2f());
        float projectedEnd = endDiff.dot(dir);
        Vector2f endProj = dir.mul(projectedEnd, new Vector2f());

        Vector2f originDiff = edge.origin.sub(origin, new Vector2f());
        float projectedOrigin = originDiff.dot(dir);
        Vector2f originProj = dir.mul(projectedOrigin);

        Vector2f endd = endProj.sub(edge.end);
        Vector2f origind = originProj.sub(edge.origin);
        System.out.println("RECASE " + endd.angle(normal));
        if(endd.angle(normal) == 0){
            return (float) Math.sqrt(endd.lengthSquared());
        }else if(origind.angle(normal) == 0){
            return (float) Math.sqrt(origind.lengthSquared());
        }
        return 0;
    }

    public String toString(){
        return origin + "-" + end;
    }
    
}
