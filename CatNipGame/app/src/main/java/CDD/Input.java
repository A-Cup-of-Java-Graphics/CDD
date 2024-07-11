package CDD;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public class Input {
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

    public static void GetMousePos(long WindowHandle) {
        DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowHandle, b1, b2);
        System.out.println("x : " + b1.get(0) + ", y = " + b2.get(0));
    }
}
