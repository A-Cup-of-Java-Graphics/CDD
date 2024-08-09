package CDD;

import java.awt.Font;
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
import CDD.font.GameFont;
import CDD.font.Text;
import CDD.gui.GUI;
import CDD.models.Model;
import CDD.models.TexturedModel;
import CDD.render.FinalRenderer;
import CDD.shape.Shapes;
import CDD.sprite.Sprite;
import CDD.texture.Texture;
import CDD.util.GameFile;
import CDD.window.EnumWindowPreset;
import CDD.window.Window;
import CDDPhysics.Character;
import CDDPhysics.Scene;
import CDDPhysics.collision.AABB;
import CDDPhysics.collision.Collision;
import CDDPhysics.collision.Edge;
import CDDPhysics.collision.PolygonBoundingBox;

import Logic.GUI.GridLayout;
import Logic.Input.Input;

import org.joml.Vector4f;

import Logic.Input.EnumInputStatus;

public class CDDGame {

	public static boolean TRIGGER = false;
	public static void main(String[] args) {
		SyncTimer timer = new SyncTimer(SyncTimer.JAVA_NANO);
		Window window = new Window("CatDrugDealer", new Vector4f(1, 0, 0, 1), EnumWindowPreset.DEFAULT);
        GL.createCapabilities();
		//Window.Update(300);
		//long LastKeyCheck = Time.CurrentMilliTime();
		Camera camera = new Camera(new Vector3f(0, 0, 0), 250);
		camera.calculateOrthographic(window, 0.0001f, 1000);
		Shapes.createDefaultVaos();
		Shapes.createDefaultModels();
		TexturedModel texModel = new TexturedModel(Shapes.squareModel(), Texture.loadFromSTBI(GameFile.readFile("CDD/textures/extremeCat.png"), true, 1024));
		TexturedModel mapModel = new TexturedModel(Shapes.squareModel(), Texture.loadFromSTBI(GameFile.readFile("CDD/textures/map1.jpg"), false, 1024));
		Sprite cat = new Sprite(texModel, new Vector3f(0, 0, -1), new Vector2f(50), 0);
		Sprite back = new Sprite(mapModel, new Vector3f(0, 0, -10), new Vector2f(1920, 1080), 0);
		AABB chara = new AABB(new Vector2f(0, 0), new Vector2f(25), null);
		Character character = new Character(camera, chara, cat, new Vector3f(0, 0, -1), new Vector2f(1), 0, 1);
		chara.setHost(character);

		Texture tex = Texture.loadFromSTBI(GameFile.readFile("CDD/textures/red.png"), false, 1024);
		Texture te = Texture.loadFromSTBI(GameFile.readFile("CDD/textures/pruple.png"), false, 1024);
		
		FinalRenderer renderer = new FinalRenderer(camera);
		//renderer.backgroundRenderer.setBackground(back);
		List<PolygonBoundingBox> pbbs = new ArrayList<PolygonBoundingBox>();

		CollisionMap map = new CollisionMap(GameFile.readFile("CDD/textures/map.png"), false, 1);
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
		window.setScene(scene);
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
		GameFile fontFile = GameFile.readFile("CDD/textures/fonts/Arial.png");
		System.out.println(fontFile.getAbsolutePath());
		GameFont arial = new GameFont("PNG", fontFile, new Font("Arial", Font.PLAIN, 64));
		Text text = new Text(arial, "SUCK MY COKE", new Vector3f(255, 255, 255), new Vector3f(0, 0, -1f), new Vector2f(10), 0);
		renderer.guiRenderer.addGUIs(text.getSymbols());
		//renderer.guiRenderer.addGUI(text.getSymbols().getFirst());
		GUI menu = new GUI(Texture.loadFromSTBI(GameFile.readFile("CDD/textures/boilerplate.png"), true, 1024), new Vector3f(0, 0, -3f), new Vector3f(1), new Vector2f(640, 320), 0);
		renderer.guiRenderer.addGUI(menu);
		List<GUI> slots = new ArrayList<GUI>();
		for(int i = 0; i < 21; i ++){
			slots.add(new GUI(Texture.loadFromSTBI(GameFile.readFile("CDD/textures/slot.png"), true, 1024), new Vector3f(0, 0, -2f), new Vector3f(1), new Vector2f(50), 0));
		}
		GridLayout gl = new GridLayout(slots, new Vector3f(-225, -80, -2f), new Vector2f(50), 0, 7, new Vector2f(1f));
		renderer.guiRenderer.addGUIs(gl.getGuis());
		Input.getInput().setStatus(EnumInputStatus.MOVING);
		while (!glfwWindowShouldClose(window.getAddress())) {
			//long CurrentTime = Time.GetDiffInMilliSeconds(LastMilliTime);
			//FrameLimiting code for inputs. Inputs will now register at 70fps
			try{
				int sync = timer.sync(70);
				delta = sync;
				for(int i = 0; i < delta; i++) {
					Input.getInput().handleInputs(window.getAddress(), scene);
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
			window.clear();
			scene.render();
			window.refresh();
		}
	}
}