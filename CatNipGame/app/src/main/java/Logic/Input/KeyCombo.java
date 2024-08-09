package Logic.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class KeyCombo {

    private List<Integer> keys = new ArrayList<Integer>();
    
    public KeyCombo(Integer...keys){
        this.keys.addAll(Arrays.asList(keys));
    }

    public KeyCombo(List<Integer> keys){
        this.keys.addAll(keys);
    }

    public List<Integer> getKeys(){
        return keys;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof KeyCombo combo){
            combo.keys.sort(Comparator.naturalOrder());
            keys.sort(Comparator.naturalOrder());
            return combo.keys.equals(keys);
        }
        return false;
    }
    
}
