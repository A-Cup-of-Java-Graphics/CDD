package CDDPhysics;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

public class Settings {

    private Map<EnumKeys, Consumer<Scene>> keyInteractions = new HashMap<EnumKeys, Consumer<Scene>>();
    private Map<EnumKeys, Integer> keyMap = new HashMap<EnumKeys, Integer>();
    private Scene scene;

    public Settings(Scene scene){
        setKeyInteraction(EnumKeys.MOVE_FORWARD, (sc) -> {sc.getCharacter().move(0, 10f);});
        setKeyInteraction(EnumKeys.MOVE_BACKWARD, (sc) -> {sc.getCharacter().move(0, -10f);});
        setKeyInteraction(EnumKeys.MOVE_LEFT, (sc) -> {sc.getCharacter().move(-10f, 0);});
        setKeyInteraction(EnumKeys.MOVE_RIGHT, (sc) -> {sc.getCharacter().move(10f, 0);});
        setKeyFor(EnumKeys.MOVE_FORWARD, GLFW.GLFW_KEY_W);
        setKeyFor(EnumKeys.MOVE_LEFT, GLFW.GLFW_KEY_A);
        setKeyFor(EnumKeys.MOVE_BACKWARD, GLFW.GLFW_KEY_S);
        setKeyFor(EnumKeys.MOVE_RIGHT, GLFW.GLFW_KEY_D);
    }

    public Consumer<Scene> hitKey(EnumKeys key){
        return keyInteractions.get(key);
    }

    public void setKeyInteraction(EnumKeys key, Consumer<Scene> interaction){
        keyInteractions.put(key, interaction);
    }

    public Integer getKeyFor(EnumKeys key){
        return keyMap.get(key);
    }

    public void setKeyFor(EnumKeys key, int k){
        keyMap.put(key, k);
    }
    
}
