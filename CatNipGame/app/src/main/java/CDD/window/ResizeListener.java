package CDD.window;

import org.lwjgl.opengl.GL11;

public class ResizeListener {

    public static void invoke(long window, int argWidth, int argHeight) {
        resizeWindow(Window.getWindow(window), argWidth, argHeight);
    }

    private static void resizeWindow(Window window, int argWidth, int argHeight) {
        GL11.glViewport(0, 0, argWidth,argHeight);
        float aspectRatio = (float) argWidth / (float) argHeight;
        //Window.scene.getCamera().setOrthographic(-aspectRatio, aspectRatio, 1, -1, 0.0001f, 1000);
        window.getScene().getCamera().calculateOrthographic(window, 0.0001f, 1000);
        window.getScene().getRenderer().setProjection(window.getScene().getCamera());
        //adjustProjectionMatrix(width, height); // recalculating projection matrix (only if you are using one)
    }
    
}
