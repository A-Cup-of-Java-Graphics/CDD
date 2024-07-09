package CDD;

import static org.lwjgl.glfw.GLFW.*;

public class CDDGame {
	public static void main(String[] args) {
		int Red = 1;
			int Green = 1;
			int Blue = 1;
			int Alpha = 1;
		new Window().Create();
		Window.SetRGBA(Red, Green, Blue, Alpha);
		Window.Update(1);

		while (!glfwWindowShouldClose(Window.Window)) {
			// WASD
			int KeyW = Input.KeyPressed(Window.Window, GLFW_KEY_W); // Increaces Red By 10
			int KeyA = Input.KeyPressed(Window.Window, GLFW_KEY_A); // INcreaces Green By 10
			int KeyS = Input.KeyPressed(Window.Window, GLFW_KEY_S); // Increaces Blue By 10
			int KeyD = Input.KeyPressed(Window.Window, GLFW_KEY_D); // Increaces Alpha By 10

			// Arrows
			int UpArrow = Input.KeyPressed(Window.Window, GLFW_KEY_UP); // Decreaces Red By 10
			int RightArrow = Input.KeyPressed(Window.Window, GLFW_KEY_RIGHT); // Decreaces Green By 10
			int DownArrow = Input.KeyPressed(Window.Window, GLFW_KEY_DOWN); // Decreaces Blue By 10
			int LeftArrow = Input.KeyPressed(Window.Window, GLFW_KEY_LEFT); // Decreaces Alpha By 10

			// WASD Controls
			if (KeyW == 1) {
				Red = Red + 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			
			}
			if (KeyA == 1) {
				Green = Green + 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			}
			if (KeyS == 1) {
				Blue = Blue + 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			
			}
			if (KeyD == 1) {
				Alpha = Alpha + 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			}



			// Arrow Controls
			if (UpArrow == 1) {
				Red = Red - 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			
			}
			if (RightArrow == 1) {
				Green = Green - 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			}
			if (DownArrow == 1) {
				Blue = Blue - 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			
			}
			if (LeftArrow == 1) {
				Alpha = Alpha - 10;
				Window.SetRGBA(Red, Green, Blue, Alpha);
				Window.Update(50);
			}

			Window.Update(50);
		}
	}

}