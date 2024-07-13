package CDD.CollisionMap;

public class ColorThreshold {

    private double[] r = new double[2], g = new double[2], b = new double[2];

    public ColorThreshold(double[] r, double[] g, double[] b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double[] getR() {
        return r;
    }

    public void setR(double[] r) {
        this.r = r;
    }

    public double[] getG() {
        return g;
    }

    public void setG(double[] g) {
        this.g = g;
    }

    public double[] getB() {
        return b;
    }

    public void setB(double[] b) {
        this.b = b;
    }
    
}
