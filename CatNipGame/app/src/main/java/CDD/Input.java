package CDD;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.*;

public class Input {
    public static int KeyPressed(long WindowHandle, int Key) {
        // If this returns 0, then the key was released
        // If this returns 1, then the key was pressed
        // If this returns 2, then the key was held

        int KeyReturned = glfwGetKey(WindowHandle, Key);
        return KeyReturned;
    }
}
