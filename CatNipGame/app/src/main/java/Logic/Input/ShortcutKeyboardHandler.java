package Logic.Input;

import java.util.HashMap;
import java.util.Map;

public class ShortcutKeyboardHandler implements IKeyboardHandler {

    private Map<KeyCombo, Runnable> shortcuts = new HashMap<KeyCombo, Runnable>();

    public void addShortcut(KeyCombo keyCombo, Runnable action){
        if(!shortcuts.containsKey(keyCombo))
            shortcuts.put(keyCombo, action);
        else
            throw new IllegalArgumentException("Cannot add a shortcut key combination when this combination has already been binded with another action");
    }

    @Override
    public void handle(long window, int key, int scancode, int action, int mods) {
        // TODO Auto-generated method stub
        for(KeyCombo keyCombo : shortcuts.keySet()){
            label1:{
                for(int k : keyCombo.getKeys()){
                    if(k == key) continue;
                    if(!Input.KeyPressed(window, k)) break label1;
                }
                shortcuts.get(keyCombo).run();
            }
        }
    }
    
}
