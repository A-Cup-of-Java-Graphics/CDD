package CDD.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineEvent;

import org.lwjgl.opengl.GL11;

import CDD.components.Vao;
import CDD.models.Model;
import CDD.models.TexturedModel;
import CDD.shape.Shapes;
import CDD.texture.Texture;
import CDD.util.GameFile;

public class GameFont {

    private static final float DEFAULT_LINE_ADJUSTMENT = 1.4f;

    private String name;
    private String format;
    private GameFile file;
    private int fontType;
    private int size;
    private Font font;
    private int width = 0, height, lineHeight;
    private Texture fontTexture;
    private Map<Integer, Glyph> characterInfo = new HashMap<Integer, Glyph>();

    private Map<Character, TexturedModel> characterModels = new HashMap<Character, TexturedModel>();
    
    public GameFont(String format, GameFile file, Font font){
        this.format = format;
        this.file = file;
        this.font = font;
        init();
    }

    public void init(){
        BufferedImage image;
        if(!file.exists()){
            GameFile lastValidDirectory = file.getLastValidDirectory();
            String name = file.getName();
            file.clear().setPath(lastValidDirectory.getDirectoryPath() + "/" + name);
            image = createBitMap(new File(lastValidDirectory.getAbsolutePath() + "/" + name), DEFAULT_LINE_ADJUSTMENT);
            fontTexture = Texture.loadFromImage(image, false);
        }else{
            image = loadImageFromFile(file);
            fontTexture = Texture.loadFromSTBI(file, false, 1024);
            //fontTexture = Texture.loadFromSTBI(GameFile.readFile("CDD/textures/pruple.png"), false, 1024);
        }
        width = image.getWidth();
        height = image.getHeight();
        characterInfo = createGlyphs(DEFAULT_LINE_ADJUSTMENT);
        characterModels = createModels();
    }

    private BufferedImage loadImageFromFile(GameFile file){
        try {
            return ImageIO.read(file.getStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Texture createTexture(BufferedImage image){
        return Texture.loadFromImage(image, false);
    }

    public Map<Character, TexturedModel> createModels(){
        Map<Character, TexturedModel> models = new HashMap<Character, TexturedModel>();
        for(int i = 0; i < font.getNumGlyphs(); i++){
            if(font.canDisplay(i)){
                Glyph g = characterInfo.get(i);
                Vao vao = new Vao(4);
                vao.bind();
                vao.storeDataArrays(g.getVertices(), g.getTextureCoords());
                Model model = new Model(vao, GL11.GL_TRIANGLE_STRIP, false);
                TexturedModel texturedModel = new TexturedModel(model, fontTexture);
                models.put((char) i, texturedModel);
                //System.out.println(g.getBounds());
            }
        }
        //System.exit(1);
        return models;
    }

    public Map<Integer, Glyph> createGlyphs(float lineAdjustment){
        Map<Integer, Glyph> glyphs = new HashMap<Integer, Glyph>();
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        int estimatedWidth = (int) (Math.sqrt(font.getNumGlyphs()) * font.getSize() + 1);
        int width = 0;
        int height = metrics.getHeight();
        int lineHeight = metrics.getHeight();
        int x = 0;
        int y = (int) (metrics.getHeight() * lineAdjustment);
        for(int i = 0; i < font.getNumGlyphs(); i++){
            if(font.canDisplay(i)){
                int charWidth = metrics.charWidth(i);
                Glyph g = new Glyph(x, y, charWidth, lineHeight);
                width = Math.max(x + charWidth, width);
                glyphs.put(i, g);
                x += g.getBounds().x;
                if(x > estimatedWidth){
                    x = 0;
                    y += metrics.getHeight() * lineAdjustment;
                    height += metrics.getHeight() * lineAdjustment;
                }
            }
        }
        height += metrics.getHeight() * lineAdjustment;
        for(Glyph glyph : glyphs.values()){
            glyph.calculateVertices(width, height);
            glyph.calculateTextureCoords(width, height);
        }
        return glyphs;
    }

    public BufferedImage createBitMap(File output, float lineAdjustment){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        int estimatedWidth = (int) (Math.sqrt(font.getNumGlyphs()) * font.getSize() + 1);
        width = 0;
        height = metrics.getHeight();
        lineHeight = metrics.getHeight();
        int x = 0;
        int y = (int) (metrics.getHeight() * lineAdjustment);
        for(int i = 0; i < font.getNumGlyphs(); i++){
            if(font.canDisplay(i)){
                int charWidth = metrics.charWidth(i);
                Glyph g = new Glyph(x, y, charWidth, lineHeight);
                characterInfo.put(i, g);
                width = Math.max(x + charWidth, width);
                
                x += g.getBounds().x;
                if(x > estimatedWidth){
                    x = 0;
                    y += metrics.getHeight() * lineAdjustment;
                    height += metrics.getHeight() * lineAdjustment;
                }
            }
        }
        height += metrics.getHeight() * lineAdjustment;
        g2d.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        prepare(g2d);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        for(int i = 0; i < font.getNumGlyphs(); i++){
            if(font.canDisplay(i)){
                Glyph g = characterInfo.get(i);
                g.calculateTextureCoords(width, height);
                g.calculateVertices(width, height);
                g2d.drawString("" + (char) i, g.getPosition().x, g.getPosition().y);
            }
        }
        g2d.dispose();
        return writeImage(img, output);
    }
    
    private BufferedImage writeImage(BufferedImage img, File output){
        try {
            output.createNewFile();
            ImageIO.write(img, format, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    private void prepare(Graphics2D g2d){
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public Map<Character, TexturedModel> getCharacterModels(){
        return characterModels;
    }

    public Map<Integer, Glyph> getCharacterInfo(){
        return characterInfo;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Font getFont(){
        return font;
    }
    
}
