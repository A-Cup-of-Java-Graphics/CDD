package CDD;

//import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
//import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
//import static org.lwjgl.opengl.GL11.GL_DEPTH;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_DEPTH;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;

public class Window {
    public static int WindowWidth;
    public static int WindowHeight;

    public static long Window;
    static String Title;
    static String Type = "defult"; // Fullscreen, Windowed, Custom, ect.
    static boolean resizable = true;
    public static int FrameLimit = 60;

    // Background Color
    public static float NRed;
    public static float NGreen;
    public static float NBlue;
    public static float NAlpha;


    public static long Create() { // Initialize The Window
        // Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable To Initialize GLFW");
        }
        
        glfwDefaultWindowHints();
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
            WindowWidth = 1080;
            WindowHeight = 720;

        } else if(Type.toLowerCase() == "fullscreen") {
            //glfwSetWindowMonitor(Window, Monitor, 0, 0, mode.width(), mode.height(), mode.refreshRate());
        } else {
            System.out.println(Type + " Is Not A Valid Type. Please Choose Between maximized, defult, and fullscreen");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_REFRESH_RATE, FrameLimit);
		glfwWindowHint(GLFW_SAMPLES, 4);
        //3.3 is the most modern version, 3.2 is the minimum version for macOS
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
        //allows the use of core versions in shader files
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        
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
        glfwSwapInterval(1);
        GLCapabilities.initialize();
        // Make window visable
        glfwShowWindow(Window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings avalable for use.
        // TLDR: Everything Will Break Without The Next Line

        //glfwSetWindowSize(Window, WindowWidth, WindowHeight);
        //MemoryStack stack = stackPush();
        //IntBuffer pWidth = stack.mallocInt(1);
        //IntBuffer pHeight = stack.mallocInt(1);
        //glfwGetWindowSize(Window, pWidth, pHeight);
       // GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        //glfwSetWindowPos(Window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);

        //Update(10);
        return Window; // Returns the windows memory address
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

    public static void clear(){
        glEnable(GL_DEPTH_TEST);
        // Clears the window buffer
        glClear(GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        // Set The RGB
        glClearColor(NRed, NGreen, NBlue, NAlpha);
    }

    public static void Update() { // This function loops the window buffer & renders new frames
        
        // Poll events (Key/mouse events)
        glfwPollEvents();
        // Swaps the buffers to the last buffer sent to the hadle
        glfwSwapBuffers(Window);
    }

    public static void framerate(int TimeToRun){
        // Replace this with the Time.GetTimeInSeconds(); function
        long loopstart = Time.CurrentMilliTime();
        while(true){
            CurrentTime = Time.GetDiffInMilliSeconds(loopstart);
            if (CurrentTime >= LastTime + 1000 /*&& window is not minimized*/) {
                System.out.println(CurrentFramesRendered);
                CurrentFramesRendered = 0;
                LastTime = CurrentTime;
            }

            // Check If The TTR Is Up
            long CurrentTime = Time.GetDiffInMilliSeconds(loopstart);
            if (CurrentTime >= TimeToRun && TimeToRun != 0) {
                return;
            }
            
            CurrentFramesRendered++;
            LoopTime = LoopTime + CurrentTime;
            FirstLoop = false;
        }
    }
}