package CDD;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;
import CDDPhysics.EnumKeys;
import CDDPhysics.Scene;

@SuppressWarnings("unused")
public class Input {
    public static boolean Pressed(long WindowHandle, int Key) {
        // If this function returns 1, then the key was pressed
        // If this function returns 0, then the key was released
        
        int KeyReturned = glfwGetKey(WindowHandle, Key);
        if (KeyReturned == GLFW_PRESS) {
            return true;
        } else if (KeyReturned == GLFW_RELEASE) {
            return false;
        }

        return false;
    }

    public static int Mouse(long WindowHandle, int MouseButton) {
        glfwSetInputMode(WindowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        int MouseReturned = glfwGetMouseButton(WindowHandle, MouseButton);
        //glfwSetMouseButtonCallback(WindowHandle, null)

        return 0;
    }

    public static void GetMousePos(long WindowHandle) {
        DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowHandle, b1, b2);
        System.out.println("x : " + b1.get(0) + ", y = " + b2.get(0));
    }

    public static void handleInputs(long window, Scene scene){
        if(Pressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_FORWARD))){
            scene.getSettings().hitKey(EnumKeys.MOVE_FORWARD).accept(scene);
        }else if(Pressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_BACKWARD))){
            scene.getSettings().hitKey(EnumKeys.MOVE_BACKWARD).accept(scene);
        }
        if(Pressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_LEFT))){
            scene.getSettings().hitKey(EnumKeys.MOVE_LEFT).accept(scene);
        }else if(Pressed(window, scene.getSettings().getKeyFor(EnumKeys.MOVE_RIGHT))){
            scene.getSettings().hitKey(EnumKeys.MOVE_RIGHT).accept(scene);
        }
    }
}
