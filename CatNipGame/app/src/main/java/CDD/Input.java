package CDD;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import CDDPhysics.EnumKeys;
import CDDPhysics.Scene;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public class Input {
    public static boolean KeyPressed(long WindowHandle, int Key) {
        // If this returns 0, then the key was released
        // If this returns 1, then the key was pressed
        // If this returns 2, then the key was held

        int KeyReturned = glfwGetKey(WindowHandle, Key);
        return KeyReturned == GLFW_TRUE;
    }

    public static void handleInputs(long window, Scene scene){
        if(KeyPressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_FORWARD))){
            scene.getSettings().hitKey(EnumKeys.MOVE_FORWARD).accept(scene);;
        }else if(KeyPressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_BACKWARD))){
            scene.getSettings().hitKey(EnumKeys.MOVE_BACKWARD).accept(scene);;
        }
        if(KeyPressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_LEFT))){
            scene.getSettings().hitKey(EnumKeys.MOVE_LEFT).accept(scene);;
        }else if(KeyPressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_RIGHT))){
            scene.getSettings().hitKey(EnumKeys.MOVE_RIGHT).accept(scene);;
        }
    }
}
