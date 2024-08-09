package CDD.window;

import static org.lwjgl.glfw.GLFW.glfwGetMonitors;

public class Monitor {
    public static void GetMonitors() {
        glfwGetMonitors();
    }
}
