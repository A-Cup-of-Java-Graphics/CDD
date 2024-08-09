package CDD.window;

import static org.lwjgl.glfw.GLFW.*;

public enum EnumWindowPreset {

    DEFAULT(){
        public void setup(Window window){
            window.setIsResizable(true);
            window.setSize(500, 300);
        }
    },
    FULLSCREEN(){
        public void setup(Window window){
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        }
    },
    MAXIMIZED(){
        public void setup(Window window){
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            window.setUseDecorations(true);
        }
    };

    public void setup(Window window){

    }
    
}
