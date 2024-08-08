package CDDPhysics;

import java.io.BufferedReader;

import CDD.util.GameFile;

public class SceneLoader {

    public static Scene loadScene(GameFile file){
        BufferedReader reader = file.getReader();
        String line = null;
        try{
            while((line = reader.readLine()) != null){
                if(line.startsWith("camera")){
                    
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
}
