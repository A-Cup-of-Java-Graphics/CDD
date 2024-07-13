package CDD;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Polygon;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import CDD.CollisionMap.CollisionMap;
import CDD.CollisionMap.EnumMarkerColorThresholds;
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
		SyncTimer timer = new SyncTimer(SyncTimer.JAVA_NANO);
		new Window().Create();
        GL.createCapabilities();
		Window.SetRGBA(Red, Green, Blue, Alpha);
		//Window.Update(300);
		long LastMilliTime = Time.CurrentMilliTime();
		//long LastKeyCheck = Time.CurrentMilliTime();
		Camera camera = new Camera(new Vector3f(0, 0, 0), 500);
		camera.calculateOrthographic(0.0001f, 1000);
		Vao vao = new Vao(4);
		vao.bind();
		vao.storeData(Shapes.SQUARE_INDICES, Shapes.SQUARE_POS, Shapes.SQUARE_TEXTURE_COORDS);
		Model model = new Model(vao, GL11.GL_TRIANGLES, true);
		TexturedModel texModel = new TexturedModel(model, Texture.loadFromSTBI(new GameFile("CDD/textures/extremeCat.png"), true, 1024));
		TexturedModel mapModel = new TexturedModel(model, Texture.loadFromSTBI(new GameFile("CDD/textures/map1.jpg"), false, 1024));
		Sprite cat = new Sprite(texModel, new Vector3f(0, 0, -1), new Vector2f(100), 0);
		Sprite back = new Sprite(mapModel, new Vector3f(0, 0, -10), new Vector2f(1920, 1080), 0);
		Character character = new Character(camera, cat, new Vector3f(0, 0, -1), new Vector2f(1), 0, 1);
		
		Texture tex = Texture.loadFromSTBI(new GameFile("CDD/textures/red.png"), false, 1024);
		
		FinalRenderer renderer = new FinalRenderer(camera);
		//renderer.backgroundRenderer.setBackground(back);

		CollisionMap map = new CollisionMap(new GameFile("CDD/textures/map.png"), false, 1);
		for(Polygon polygon : map.getPolygons(EnumMarkerColorThresholds.COLLIDER)){
			float[] points = new float[polygon.npoints * 3];
			float[] textureCoords = new float[polygon.npoints * 2];
			Vector3f center = new Vector3f(0, 0, -2);
			for(int i = 0; i < polygon.npoints; i++){
				center.add(polygon.xpoints[i], polygon.ypoints[i], 0);
				points[i * 3] = polygon.xpoints[i];
				points[i * 3 + 1] = -polygon.ypoints[i]; 
				System.out.println(polygon.xpoints[i] / 1000);
				points[i * 3 + 2] = 0;
				textureCoords[i * 2] = 1;
				textureCoords[i * 2 + 1] = 1;
			}
			Vao v = new Vao(polygon.npoints);
			v.bind();
			v.storeData(new int[polygon.npoints], points, textureCoords);
			Model m = new Model(v, GL11.GL_LINE_LOOP, false);
			TexturedModel t = new TexturedModel(m, tex);
			
			center.div(polygon.npoints);
			Sprite s = new Sprite(t, new Vector3f(-1920f/2f, 1080f/2f, -2), new Vector2f(1), 0);
			renderer.spriteRenderer.addSprite(s);
		}
		GL11.glLineWidth(10);
		renderer.spriteRenderer.addSprite(cat);
		renderer.spriteRenderer.addSprite(back);
		Scene scene = new Scene(character, camera, renderer);
		Window.scene = scene;
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		float delta;
		while (!glfwWindowShouldClose(Window.WindowHandle)) {
			//long CurrentTime = Time.GetDiffInMilliSeconds(LastMilliTime);
			//FrameLimiting code for inputs. Inputs will now register at 70fps
			try{
				int sync = timer.sync(70);
				delta = sync;
				for(int i = 0; i < delta; i++) {
					Input.handleInputs(Window.WindowHandle, scene);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(Red, Green, Blue, Alpha);
			scene.render();
			GLFW.glfwPollEvents();
			GLFW.glfwSwapBuffers(Window.WindowHandle);
		}
	}
}