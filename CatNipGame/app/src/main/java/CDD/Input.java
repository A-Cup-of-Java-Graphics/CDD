package CDD;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public class Input {
    // Mouse Settings
    //int MouseType = GLFW_CURSOR_
    public static int KeyPressed(long WindowHandle, int Key) {
        // If this returns 0, then the key was released
        // If this returns 1, then the key was pressed
        // If this returns 2, then the key was held

        int KeyReturned = glfwGetKey(WindowHandle, Key);
        return KeyReturned;
    }

    public static int Mouse(long WindowHandle, int MouseButton) {
        glfwSetInputMode(WindowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        int MouseReturned = glfwGetMouseButton(WindowHandle, MouseButton);
        //glfwSetMouseButtonCallback(WindowHandle, null)

        return 0;
    }

    public static double GetMouseX(long WindowHandle) {
        glfwSetInputMode(WindowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        DoubleBuffer getx = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer gety = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowHandle, getx, gety);

        // Write The data
        return getx.get();
    
    }

    public static double GetMouseY(long WindowHandle) {
        glfwSetInputMode(WindowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        DoubleBuffer getx = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer gety = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowHandle, getx, gety);

        // Write The data
        return gety.get();
    
    }
}
