import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import ky.AudioPlayer;
import ky.KYscreen;
import ky.Vector2D;

public class Main extends KYscreen implements MouseInputListener {

    public static int width = 1500;
    public static int height = 800;
    public Character player1;
    public Character player2;
    public Character[][] characters;
    public int sceneIndex = 0;
    public Scene[] scenes;
    public AudioPlayer testAudio;
    public AudioPlayer backgroundMusic;
    public Vector2D mousePos = new Vector2D(0,0);
    public boolean mousePressed=false;

    boolean cSelect = true;
    Scene currentScene;

    public Main() {
        super(width, height, false);
    }

    @Override
    public void start() {
        setDebugMode(true);
        setResizable(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        // testAudio = new AudioPlayer("assets/SFX/idk.wav");
        // testAudio.play();
        // backgroundMusic = new AudioPlayer("assets/SFX/background_music_test.wav");
        // backgroundMusic.setVolume(-8);
        // backgroundMusic.setLoop(true);
        // backgroundMusic.play();

        scenes = new Scene[6];
        scenes[0] = new StartScene(this);
        scenes[1] = new MainMenuScene(this);
        scenes[2] = new CharacterSelectScene(this);
        scenes[3] = new GameScene(this);
        scenes[4] = new EndScene(this);
        scenes[5] = new InputSettingsScene(this);

        resetCharacters();

        currentScene = scenes[sceneIndex];
        setScene(currentScene);
    }

    @Override
    public void update() {
        if(currentScene == null) return; // we should probably throw and error
        ArrayList <Integer> clonedKeyCodes = activeKeyCodes;
        currentScene.update(deltaT, clonedKeyCodes);
    }

    public void resetCharacters () {
        characters = new Character[4][2];  // different characters
        characters[0][0] = new OtherTestCharacter(this);
        characters[1][0] = new TestCharacter(this);
        characters[2][0] = new TestCharacter(this);
        characters[3][0] = new TestCharacter(this);
        characters[0][1] = new OtherTestCharacter(this);
        characters[1][1] = new TestCharacter(this);
        characters[2][1] = new TestCharacter(this);
        characters[3][1] = new TestCharacter(this);
    }

	public void setScene (Scene scene) {
        // if(!scene.hasInitialized) {

        scene.delete(); // just reload that stuff lmfao
        scene.initialize(); // re-initialize it xD
        
        //     scene.hasInitialized = true;
        // }

        assetLayers = scene.getAssetLayers();
        entityLayers = scene.getEntityLayers();
        collisionEntities = scene.getCollisionEntities();
        currentScene = scene;

        if (currentScene instanceof GameScene || currentScene instanceof CharacterSelectScene) {
            setCursorVisible(false);
        } else {
            setCursorVisible(true);
        }
	}

    public void setScene(int index) {
        setScene(scenes[index]);
    }

    @Override
    public void keyPressed(int keyCode) {
        // if (keyCode == KeyEvent.VK_ESCAPE) { 
        //     System.exit(0);
        // }
    }

    @Override
    public void keyReleased(int keyCode) {
        
    }

    @Override
    public void keyTyped(int keyCode) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mousePos = new Vector2D(e.getX(), e.getY());
        mousePressed = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos = new Vector2D(e.getX(), e.getY());
        mousePressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = new Vector2D(e.getX(), e.getY());
        mousePressed = false;
    }

    public static void main (String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        new Main();
    }

}