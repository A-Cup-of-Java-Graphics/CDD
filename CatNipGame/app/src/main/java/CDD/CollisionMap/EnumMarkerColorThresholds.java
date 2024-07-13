package CDD.CollisionMap;

public enum EnumMarkerColorThresholds {

    COLLIDER(new ColorThreshold(
        new double[]{255, 255},
        new double[]{0, 0},
        new double[]{0, 0}
    )),
    GAME_TRIGGER(new ColorThreshold(
        new double[]{0, 0},
        new double[]{119.24460431654676, 255},
        new double[]{143.5521582733813, 255}
    )),
    NPC_PATH(new ColorThreshold(
        new double[]{217.85071942446044, 255},
        new double[]{224.73021582733813, 255},
        new double[]{0, 0}
        ));

    private ColorThreshold threshold;

    private EnumMarkerColorThresholds(ColorThreshold threshold){
        this.threshold = threshold;
    }

    public ColorThreshold getThreshold(){
        return threshold;
    }
    
}
