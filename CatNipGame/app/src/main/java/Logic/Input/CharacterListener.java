package Logic.Input;

public class CharacterListener {

    public static void characterCallback(long window, int codepoint){
        Input.getInput().handle(window, codepoint);
    }
    
}
