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

    public static double[] GetMousePOS(long WindowHandle) {
        glfwSetInputMode(WindowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        DoubleBuffer getx = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer gety = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowHandle, getx, gety);

        // xy[0] returns the x
        // xy[1] returns the y
        double[] xy = {getx.get(), gety.get()};

        // Write The data
        return xy;
    
    }
}
