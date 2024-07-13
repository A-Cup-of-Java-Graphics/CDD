package CDD;

//import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
//import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_DEPTH;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_DEPTH;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
//mport static org.lwjgl.opengl.GL11.glColor3d;
//import static org.lwjgl.opengl.GL11.glGetString;
//import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;

//import java.lang.management.ManagementFactory;
//import java.lang.management.OperatingSystemMXBean;
//import java.lang.management.ManagementFactory;
//import java.nio.IntBuffer;
//import java.util.Set;

//import javax.management.monitor.Monitor;

//import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
//import org.lwjgl.glfw.GLFWImage;
//import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
//import org.lwjgl.system.MemoryStack;

import CDDPhysics.Scene;

public class Window {

    public static Scene scene;

    static int WindowWidth;
    static int WindowHeight;

    public static long WindowHandle;
    static String Title;
    public static String Type = "default"; // Fullscreen, Windowed, Custom, ect.
    static boolean Resizable = true;
    static boolean AllowDecorations = true;

    // Background Color
    public static float NRed;
    public static float NGreen;
    public static float NBlue;
    public static float NAlpha;


    public long Create() { // Initialize The Window
        // Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable To Initialize GLFW");
        }

        // Configure GLFW
        if (Resizable == true) {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        }
        else {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        }

        if (Type.toLowerCase() == "maximized") {
            Resizable = true;
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        } else if(Type.toLowerCase() == "default") {
            WindowWidth = 300;
            WindowHeight = 500;

        } else if(Type.toLowerCase() == "fullscreen") {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        } else {
            System.out.println(Type + " Is Not A Valid Type. Please Choose Between maximized, defult, and fullscreen");
        }

        if (AllowDecorations == true && Type.toLowerCase() != "fullscreen") {
            glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        } else if (AllowDecorations == false) {
            glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        }

        //glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_SAMPLES, 4);
        //3.3 is the most modern version, 3.2 is the minimum version for macOS
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		//glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
        //allows the use of core versions in shader files
		//glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        
        //glfwSetWindowIcon(Window, Icon);
        // Create Window
        if (Title == null) {
            Title = "";
        }
        WindowHandle = glfwCreateWindow(WindowWidth, WindowHeight, Title, NULL, NULL);
        glfwMakeContextCurrent(WindowHandle);
        
        if (WindowHandle == NULL) {
            throw new IllegalStateException("Failed To Create GLFW Window");
        }

        // Mouse Stuff
        glfwSetFramebufferSizeCallback(WindowHandle, ResizeListener::invoke);
        GLFW.glfwSetCursorPosCallback(WindowHandle, MouseListener::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(WindowHandle, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(WindowHandle, MouseListener::mouseScrollCallback);

        // Make OpenGL the context current
        glfwMakeContextCurrent(WindowHandle);
        // Enable v-sync
        glfwSwapInterval(0);
		GLCapabilities.initialize();

        // Make window visable
        glfwShowWindow(WindowHandle);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings avalable for use.
        // TLDR: Everything Will Break Without The Next Line
        GL.createCapabilities();

        Update(10);
        return WindowHandle; // Returns the windows memory address
    }

    public static int[] getFrameBufferDimensions(){
        int[] x = new int[1];
        int[] y = new int[1];
        glfwGetFramebufferSize(WindowHandle, x, y);
        return new int[]{x[0], y[0]};
    }

    public static void Size(int Width, int Height) {
        WindowWidth = Width;
        WindowHeight = Height;
    }

    public static void SetRGBA(int Red, int Green, int Blue, int Alpha) {
        // Cast the integers into floats, and divide them in order to get the correct data/RGB

        NRed = Red / 255.0f;
        NGreen = Green / 255.0f;
        NBlue = Blue / 255.0f;
        NAlpha = Alpha / 255.0f;

    }

    static int CurrentFramesRendered;
    static long LoopTime;
    static boolean FirstLoop = true;
    static long CurrentTime;
    static long LastTime;

    public static void Update(int TimeToRun) { // This function loops the window buffer & renders new frames
        long loopstart = Time.CurrentMilliTime();
        while (!glfwWindowShouldClose(WindowHandle)) {
            // Poll events (Key/mouse events)
            glfwPollEvents();
            
            // Set The RGB
            glClearColor(NRed, NGreen, NBlue, NAlpha);
            
            // Clears the window buffer
            glClear(GL_COLOR_BUFFER_BIT);

            // Swaps the buffers to the last buffer sent to the hadle
            glfwSwapBuffers(WindowHandle);

            // Replace this with the Time.GetTimeInSeconds(); function
            CurrentTime = Time.GetDiffInMilliSeconds(loopstart);
            if (CurrentTime >= LastTime + 1000 /*&& window is not minimized*/) {
                System.out.println(CurrentFramesRendered);
                CurrentFramesRendered = 0;
                LastTime = CurrentTime;
            }

            if (CurrentTime >= TimeToRun && TimeToRun != 0) {
                break;
            }
            
            CurrentFramesRendered++;
            LoopTime = LoopTime + CurrentTime;
            FirstLoop = false;
        }
    }
}