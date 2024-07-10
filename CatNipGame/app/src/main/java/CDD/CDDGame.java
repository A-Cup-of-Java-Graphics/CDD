package CDD;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import CDD.components.Vao;
import CDD.models.Model;
import CDD.models.TexturedModel;
import CDD.render.FinalRenderer;
import CDD.texture.Texture;
import CDD.util.GameFile;
import CDDPhysics.Character;
import CDDPhysics.Scene;

public class CDDGame {
	public static void main(String[] args) {
		int Red = 255;
			int Green = 0;
			int Blue = 0;
			int Alpha = 0;

		long window = Window.Create();
        GL.createCapabilities();
		Window.SetRGBA(Red, Green, Blue, Alpha);
		//Window.Update(300);
		long LastMilliTime = Time.CurrentMilliTime();
		//long LastKeyCheck = Time.CurrentMilliTime();
		float aspectRatio = (float) Window.WindowWidth / (float) Window.WindowHeight;
		Camera camera = new Camera(new Vector3f(0, 0, 0));
		camera.setOrthographic(-aspectRatio, aspectRatio, 1, -1, 0.001f, 1000);
		Vao vao = new Vao(4);
		vao.bind();
		vao.storeData(Shapes.SQUARE_INDICES, Shapes.SQUARE_POS, Shapes.SQUARE_TEXTURE_COORDS);
		Model model = new Model(vao);
		TexturedModel texModel = new TexturedModel(model, Texture.loadFromSTBI(new GameFile("CDD/textures/addict.png"), 1024));
		TexturedModel mapModel = new TexturedModel(model, Texture.loadFromSTBI(new GameFile("CDD/textures/map0.png"), 1024));
		Sprite cat = new Sprite(texModel, new Vector3f(0, 0, -1), new Vector2f(1), 0);
		Sprite back = new Sprite(mapModel, new Vector3f(0, 0, -10), new Vector2f(4), 0);
		Character character = new Character(camera, cat, new Vector3f(0, 0, -1), new Vector2f(1), 0, 1);
		Scene scene = new Scene(character);
		FinalRenderer renderer = new FinalRenderer(camera);
		//renderer.backgroundRenderer.setBackground(back);
		renderer.spriteRenderer.addSprite(cat);
		renderer.spriteRenderer.addSprite(back);
		while (!glfwWindowShouldClose(Window.Window)) {
			Window.clear();
			//long CurrentTime = Time.GetDiffInMilliSeconds(LastMilliTime);
			Input.handleInputs(window, scene);
			renderer.render(camera);
			Window.Update();
			//Window.framerate(50);
		}
	}

}
