package CDD.window;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.glfw.GLFWErrorCallback;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import CDDPhysics.Scene;
import Logic.Input.CharacterListener;
import Logic.Input.KeyboardListener;
import Logic.Input.MouseListener;

public class Window {

    private static final Map<Long, Window> windows = new HashMap<Long, Window>();

    private Scene scene;

    private int width;
    private int height;

    public  long address;
    private String title;
    public  EnumWindowPreset type; // Fullscreen, Windowed, Custom, ect.
    private boolean isResizable = true;
    private boolean useDecorations = true;

    // Background Color
    public Vector4f color;

    public Window(String title, Vector4f color, EnumWindowPreset type){
        this.title = title;
        this.color = color;
        this.type = type;
        create();
    }

    public Window(String title, Vector4f color, int width, int height, boolean isResizable, boolean useDecorations){
        this.title = title;
        this.color = color;
        this.width = width;
        this.height = height;
        this.isResizable = isResizable;
        this.useDecorations = useDecorations;
        create();
    }


    public long create() { // Initialize The Window
        // Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable To Initialize GLFW");
        }

        
        glfwDefaultWindowHints();
        if(type != null)
            type.setup(this);
        glfwWindowHint(GLFW_DECORATED, useDecorations ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, isResizable ? GLFW_TRUE : GLFW_FALSE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_SAMPLES, 4);
        //3.3 is the most modern version, 3.2 is the minimum version for macOS
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
        //allows the use of core versions in shader files
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        
        //glfwSetWindowIcon(Window, Icon);
        // Create Window
        if (title == null) {
            title = "";
        }
        address = glfwCreateWindow(width, height, title, NULL, NULL);
        glfwMakeContextCurrent(address);
        
        if (address == NULL) {
            throw new IllegalStateException("Failed To Create GLFW Window");
        }

        // Mouse Stuff
        GLFW.glfwSetFramebufferSizeCallback(address, ResizeListener::invoke);
        GLFW.glfwSetKeyCallback(address, KeyboardListener::keyboardCallback);
        GLFW.glfwSetCharCallback(address, CharacterListener::characterCallback);
        GLFW.glfwSetCursorPosCallback(address, MouseListener::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(address, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(address, MouseListener::mouseScrollCallback);

        // Make OpenGL the context current
        glfwMakeContextCurrent(address);
        // Enable v-sync
        glfwSwapInterval(0);
		GLCapabilities.initialize();

        // Make window visable
        glfwShowWindow(address);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings avalable for use.
        // TLDR: Everything Will Break Without The Next Line
        GL.createCapabilities();
        windows.put(address, this);
        return address; // Returns the windows memory address
    }

    public void clear(){
        clearBuffer();
        clearColor();
    }

    public void clearColor(){
        GL11.glClearColor(color.x, color.y, color.z, color.w);
    }

    public void clearBuffer(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void refresh(){
        GLFW.glfwPollEvents();
        GLFW.glfwSwapBuffers(address);
    }

    public int[] getFrameBufferDimensions(){
        int[] x = new int[1];
        int[] y = new int[1];
        glfwGetFramebufferSize(address, x, y);
        return new int[]{x[0], y[0]};
    }

    public Scene getScene(){
        return scene;
    }

    public long getAddress(){
        return address;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setUseDecorations(boolean useDecorations){
        this.useDecorations = useDecorations;
    }

    public void setIsResizable(boolean isResizable){
        this.isResizable = isResizable;
    }

    public void setRGBA(int red, int green, int blue, int alpha) {
        this.color.set(red, green, blue, alpha);
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public static Window getWindow(long address){
        return windows.get(address);
    }
}