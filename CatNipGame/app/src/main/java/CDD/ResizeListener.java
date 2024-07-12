package CDD;

import org.lwjgl.opengl.GL11;

public class ResizeListener {

    public static void invoke(long window, int argWidth, int argHeight) {
        resizeWindow(argWidth, argHeight);
    }

    private static void resizeWindow(int argWidth, int argHeight) {
        GL11.glViewport(0, 0, argWidth,argHeight);
        float aspectRatio = (float) argWidth / (float) argHeight;
        Window.scene.getCamera().setOrthographic(-aspectRatio, aspectRatio, 1, -1, 0.0001f, 1000);
        Window.scene.getRenderer().setProjection(Window.scene.getCamera());
        //adjustProjectionMatrix(width, height); // recalculating projection matrix (only if you are using one)
    }
    
}
