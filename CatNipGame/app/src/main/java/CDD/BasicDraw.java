package CDD;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_RGB;

import org.lwjgl.opengl.GL11;

// Ignore This Class
public class BasicDraw {
    public static void DrawLine(int Red, int Green, int Blue, int Alpha) {
        float NRed = Red / 255.0f;
        float NGreen = Green / 255.0f;
        float NBlue = Blue / 255.0f;
        float NAlpha = Alpha / 255.0f;

        GL11.glBegin(GL11.GL_QUADS);
        /*GL11.glColor4f(NRed, NGreen, NBlue, NAlpha);
        GL11.glVertex2f( 1.0f,1.0f);
        GL11.glVertex2f(1.0f,1.0f);
        GL11.glVertex2f(1.0f,1.0f);
        GL11.glVertex2f(1.0f, 1.0f);*/
        float raster[] = {1.0f, 1.0f};
        GL11.glDrawPixels(500, 500, GL_RGB, GL_FLOAT, raster);
        GL11.glEnd();
        GL11.glRasterPos2f(1.0f, 1.0f);
    }
}
