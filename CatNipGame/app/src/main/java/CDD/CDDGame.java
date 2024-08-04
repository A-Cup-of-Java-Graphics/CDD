package CDD;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
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
import CDDPhysics.collision.AABB;
import CDDPhysics.collision.Collision;
import CDDPhysics.collision.Edge;
import CDDPhysics.collision.PolygonBoundingBox;

public class CDDGame {

	public static boolean TRIGGER = false;
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
		Camera camera = new Camera(new Vector3f(0, 0, 0), 250);
		camera.calculateOrthographic(0.0001f, 1000);
		Vao vao = new Vao(4);
		vao.bind();
		vao.storeData(Shapes.SQUARE_INDICES, Shapes.SQUARE_POS, Shapes.SQUARE_TEXTURE_COORDS);
		Model model = new Model(vao, GL11.GL_TRIANGLES, true);
		TexturedModel texModel = new TexturedModel(model, Texture.loadFromSTBI(new GameFile("CDD/textures/extremeCat.png"), true, 1024));
		TexturedModel mapModel = new TexturedModel(model, Texture.loadFromSTBI(new GameFile("CDD/textures/map1.jpg"), false, 1024));
		Sprite cat = new Sprite(texModel, new Vector3f(0, 0, -1), new Vector2f(50), 0);
		Sprite back = new Sprite(mapModel, new Vector3f(0, 0, -10), new Vector2f(1920, 1080), 0);
		AABB chara = new AABB(new Vector2f(0, 0), new Vector2f(25), null);
		Character character = new Character(camera, chara, cat, new Vector3f(0, 0, -1), new Vector2f(1), 0, 1);
		chara.setHost(character);

		Texture tex = Texture.loadFromSTBI(new GameFile("CDD/textures/red.png"), false, 1024);
		Texture te = Texture.loadFromSTBI(new GameFile("CDD/textures/pruple.png"), false, 1024);
		
		FinalRenderer renderer = new FinalRenderer(camera);
		//renderer.backgroundRenderer.setBackground(back);
		List<PolygonBoundingBox> pbbs = new ArrayList<PolygonBoundingBox>();

		CollisionMap map = new CollisionMap(new GameFile("CDD/textures/map.png"), false, 1);
		Map<Polygon, Vector2f> polygons = map.getPolygons(EnumMarkerColorThresholds.COLLIDER);
		for(Polygon polygon : polygons.keySet()){
			float[] points = new float[polygon.npoints * 3];
			float[] textureCoords = new float[polygon.npoints * 2];
			Vector2f center = polygons.get(polygon);
			for(int i = 0; i < polygon.npoints; i++){
				points[i * 3] = polygon.xpoints[i];
				points[i * 3 + 1] = polygon.ypoints[i]; 
				points[i * 3 + 2] = 0;
				textureCoords[i * 2] = 1;
				textureCoords[i * 2 + 1] = 1;
			}
			Vao v = new Vao(polygon.npoints);
			v.bind();
			v.storeData(new int[polygon.npoints], points, textureCoords);
			Model m = new Model(v, GL11.GL_LINE_LOOP, false);
			TexturedModel t = new TexturedModel(m, tex);
			
			Sprite s = new Sprite(t, new Vector3f(center.x, center.y, -2), new Vector2f(1), 0);
			//renderer.spriteRenderer.addSprite(s);
			
			PolygonBoundingBox bb = new PolygonBoundingBox(new Vector2f(center), polygon, null);
			pbbs.add(bb);
		}
		for(PolygonBoundingBox bb : pbbs){
			for(Edge edge : bb.getEdges()){
				Vao e = new Vao(2);
				e.bind();
				e.storeData(new int[2], new float[]{edge.getOrigin().x, edge.getOrigin().y, 0, edge.getEnd().x, edge.getEnd().y, 0}, new float[]{1, 1, 1, 1});
				Model m = new Model(e, GL11.GL_LINES, false);
				TexturedModel t = new TexturedModel(m, te);
				Sprite spr = new Sprite(t, new Vector3f(0, 0, -3f), new Vector2f(1), 0);
				renderer.spriteRenderer.addSprite(spr);

				Vao n = new Vao(2);
				n.bind();
				n.storeData(new int[1], new float[]{0, 0, 0, bb.getSides().get(edge).x * 10, bb.getSides().get(edge).y * 10, 0}, new float[]{1, 1, 1, 1});
				Model nm = new Model(n, GL11.GL_LINES, false);
				TexturedModel tnm = new TexturedModel(nm, tex);
				Vector2f center = edge.getCenter();
				Sprite s = new Sprite(tnm, new Vector3f(center.x, center.y, -2f), new Vector2f(1), 0);
				renderer.spriteRenderer.addSprite(s);
			}
		}
		GL11.glLineWidth(1);
		renderer.spriteRenderer.addSprite(cat);
		renderer.spriteRenderer.addSprite(back);
		Scene scene = new Scene(character, camera, renderer);
		Window.scene = scene;
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		float delta;
		List<Sprite> sprs = new ArrayList<Sprite>();
		for(Edge edge : chara.getEdges()){
			Vao e = new Vao(2);
				e.bind();
				e.storeData(new int[2], new float[]{0, 0, 0, edge.getEnd().x - edge.getOrigin().x, edge.getEnd().y - edge.getOrigin().y, 0}, new float[]{1, 1, 1, 1});
				Model m = new Model(e, GL11.GL_LINES, false);
				TexturedModel t = new TexturedModel(m, te);
				Sprite spr = new Sprite(t, new Vector3f(0, 0, -3f), new Vector2f(1), 0);
				renderer.spriteRenderer.addSprite(spr);
				sprs.add(spr);
		}
		character.setCanMove(true);
		while (!glfwWindowShouldClose(Window.WindowHandle)) {
			//long CurrentTime = Time.GetDiffInMilliSeconds(LastMilliTime);
			//FrameLimiting code for inputs. Inputs will now register at 70fps
			try{
				int sync = timer.sync(70);
				delta = sync;
				for(int i = 0; i < delta; i++) {
					Input.handleInputs(Window.WindowHandle, scene);
					int j = 0;
					for(Edge edge : chara.getEdges()){
						sprs.get(j).setPosition(new Vector3f(edge.getOrigin(), -1f));
						j++;
					}
					for(PolygonBoundingBox bb : pbbs){
						Collision collision = new Collision(chara, bb);
						collision.ennactCollisionForce();
					}
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