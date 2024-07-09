package CDD;

import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
//import static org.lwjgl.glfw.GLFW.glfwGetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
//import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
//import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_DEPTH;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_DEPTH;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
//mport static org.lwjgl.opengl.GL11.glColor3d;
//import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

//import java.lang.management.ManagementFactory;
//import java.lang.management.OperatingSystemMXBean;
//import java.lang.management.ManagementFactory;
import java.nio.IntBuffer;
//import java.util.Set;

//import javax.management.monitor.Monitor;

//import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
//import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;


//import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
//import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_REFRESH_RATE;

public class Window {
    static int WindowWidth;
    static int WindowHeight;

    public static long Window;
    static String Title;
    static String Type = "defult"; // Fullscreen, Windowed, Custom, ect.
    static boolean resizable = true;
    public int FrameLimit;

    // Background Color
    public static float NRed;
    public static float NGreen;
    public static float NBlue;
    public static float NAlpha;


    public int Create() { // Initialize The Window
        // Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable To Initialize GLFW");
        }

        // Configure GLFW
        if (resizable == true) {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        }
        else {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        }

        if (Type.toLowerCase() == "maximized") {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        } else if(Type.toLowerCase() == "defult") {
            WindowWidth = 300;
            WindowHeight = 500;

        } else if(Type.toLowerCase() == "fullscreen") {
            //glfwSetWindowMonitor(Window, Monitor, 0, 0, mode.width(), mode.height(), mode.refreshRate());
        } else {
            System.out.println(Type + " Is Not A Valid Type. Please Choose Between maximized, defult, and fullscreen");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_REFRESH_RATE, 100);
        
        //glfwSetWindowIcon(Window, Icon);
        // Create Window
        if (Title == null) {
            Title = "";
        }
        Window = glfwCreateWindow(WindowWidth, WindowHeight, Title, NULL, NULL);
        glfwMakeContextCurrent(Window);
        
        if (Window == NULL) {
            throw new IllegalStateException("Failed To Create GLFW Window");
        }

        // Make OpenGL the context current
        glfwMakeContextCurrent(Window);
        // Enable v-sync
        glfwSwapInterval(0);

        // Make window visable
        glfwShowWindow(Window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings avalable for use.
        // TLDR: Everything Will Break Without The Next Line
        GL.createCapabilities();

        glfwSetWindowSize(Window, WindowWidth, WindowHeight);
        MemoryStack stack = stackPush();
        IntBuffer pWidth = stack.mallocInt(1);
        IntBuffer pHeight = stack.mallocInt(1);
        glfwGetWindowSize(Window, pWidth, pHeight);
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(Window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);

        Update(10);
        return 0; // Returns the windows memory address
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
        long loopstart = Time.StartMilliTime();
        while (!glfwWindowShouldClose(Window)) {
            int RR = GLFWVidMode.REFRESHRATE;
            System.out.println("Current Rate: " + RR);
            glfwWindowHint(GLFW_REFRESH_RATE, new Window().FrameLimit);
            // Poll events (Key/mouse events)
            glfwPollEvents();
            
            // Set The RGB
            glClearColor(NRed, NGreen, NBlue, NAlpha);
            
            // Clears the window buffer
            glClear(GL_COLOR_BUFFER_BIT);

            // Swaps the buffers to the last buffer sent to the hadle
            glfwSwapBuffers(Window);

            // Replace this with the Time.GetTimeInSeconds(); function
            CurrentTime = Time.GetDiffInMilliSeconds(loopstart);
            if (CurrentTime >= LastTime + 1000 /*&& window is not minimized*/) {
                System.out.println(CurrentFramesRendered);
                CurrentFramesRendered = 0;
                LastTime = CurrentTime;
            }

            // Check If The TTR Is Up
            long CurrentTime = Time.GetDiffInMilliSeconds(loopstart);
            if (CurrentTime >= TimeToRun && TimeToRun != 0) {
                break;
            }
            
            CurrentFramesRendered++;
            LoopTime = LoopTime + CurrentTime;
            FirstLoop = false;
        }
    }
}