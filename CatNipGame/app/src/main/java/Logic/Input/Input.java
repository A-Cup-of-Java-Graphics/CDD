package Logic.Input;
import java.nio.DoubleBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import CDD.CDDGame;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import CDDPhysics.EnumKeys;
import CDDPhysics.Scene;

@SuppressWarnings("unused")
public class Input implements IKeyboardHandler, ICharacterHandler{

    private static Input INPUT = new Input();

    private EnumInputStatus status;
    private Map<EnumInputStatus, IKeyboardHandler> keyboardHandlerMap = new HashMap<EnumInputStatus, IKeyboardHandler>();
    private Map<EnumInputStatus, ICharacterHandler> characterHandlerMap = new HashMap<EnumInputStatus, ICharacterHandler>();

    private Input(){
        
    }

    // Mouse Settings
    //int MouseType = GLFW_CURSOR_
    public static boolean KeyPressed(long WindowHandle, int Key) {
        // If this returns 0, then the key was released
        // If this returns 1, then the key was pressed
        // If this returns 2, then the key was held

        int KeyReturned = glfwGetKey(WindowHandle, Key);
        return KeyReturned == GLFW_PRESS;
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
    
    public void handleInputs(long window, Scene scene){
        if(status == EnumInputStatus.MOVING){
            for(EnumKeys enumKey : EnumKeys.values()){
                if(KeyPressed(window, scene.getSettings().getKeyFor(enumKey))){
                    scene.getSettings().hitKey(enumKey).accept(scene);
                }
            }
            if(KeyPressed(window, GLFW.GLFW_KEY_TAB)){
                CDDGame.TRIGGER = !CDDGame.TRIGGER;
            }
        }
    }

    @Override
    public void handle(long window, int key, int scancode, int action, int mods) {
        // TODO Auto-generated method stub
        if(keyboardHandlerMap.containsKey(status))
            keyboardHandlerMap.get(status).handle(window, key, scancode, action, mods);
    }

    @Override
    public void handle(long window, int codepoint){
        if(characterHandlerMap.containsKey(codepoint)){
            characterHandlerMap.get(status).handle(window, codepoint);
        }
    }

    public EnumInputStatus getStatus(){
        return status;
    }

    public void setStatus(EnumInputStatus status){
        this.status = status;
    }

    public static Input getInput(){
        return INPUT;
    }
}
