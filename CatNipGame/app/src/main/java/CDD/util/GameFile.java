package CDD.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class GameFile {

    private List<String> data = new ArrayList<String>();
    private String path;

    public GameFile(String path){
        this.path = path;
        read(path);
    }

    public void read(String path){
        InputStreamReader isr = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
        BufferedReader br = new BufferedReader(isr);
        String line;
        try{
            while((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getPath(){
        return path;
    }

    public String getAbsolutePath(){
        return getAsFile().getAbsolutePath();
    }

    public List<String> getData(){
        return data;
    }

    public InputStream getStream(){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }

    public File getAsFile(){
        return new File(ClassLoader.getSystemClassLoader().getResource(path).getPath());
    }

    public OutputStream getOutputStream(){
        try{
            return new FileOutputStream(getAsFile());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public BufferedReader getReader(){
        InputStreamReader isr = new InputStreamReader(getStream());
        BufferedReader br = new BufferedReader(isr);
        return br;
    }

    public ByteBuffer getByteBuffer(int size){
        InputStream is = getStream();
        ByteBuffer buffer = ByteBuffer.allocateDirect(size);
        int data = -1;
        try{
            while((data = is.read()) != -1){
                buffer.put((byte) data);
                if(buffer.remaining() == 0){
                    ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() + size);
                    buffer.flip();
                    newBuffer.put(buffer);
                    buffer = newBuffer;
                }
            }
            is.close();
            buffer.flip();
            return buffer;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }finally{
            if(is != null){
                try{
                    is.close();
                }catch(Exception e){    
                    e.printStackTrace();
                }
            }
        }
    }
    
}
