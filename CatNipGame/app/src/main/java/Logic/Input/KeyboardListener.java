package Logic.Input;

public class KeyboardListener {

    public static void keyboardCallback(long window, int key, int scancode, int action, int mods){

        Input.getInput().handle(window, key, scancode, action, mods);

    }
    
}
