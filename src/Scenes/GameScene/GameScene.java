import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ky.Asset;
import ky.Text;
import ky.Vector2D;

public class GameScene extends Scene {

    static Ground ground;
    static Character player1;
    static Character player2;
    Text fpsText;
    double tTime = 0;
    int frames = 0;
    HealthBar p1HealthBar;
    HealthBar p2HealthBar;
    CooldownBar abilityBarP1;
    CooldownBar abilityBarP2;
    CooldownBar ultBarP1;
    CooldownBar ultBarP2;
    Asset[] p1LivesDisplay;
    Asset[] p2LivesDisplay;
    Asset p1Indicator;
    Asset p2Indicator;
    Asset pausedBackground;
    int finalSelect;
    Button exitButton;
    Button resumeButton;
    Button resetButton;
    boolean paused = false;

    String[] allMaps = {"assets/maps/default.map", "assets/maps/platformer.map", "assets/maps/staircase.map", "assets/maps/reversestaircase.map"};

    public GameScene(Main main) {
        super(main);
    }

    private void updatePauseMenu(boolean paused) {
        this.paused = paused;
        exitButton.setVisible(paused);
        resumeButton.setVisible(paused);
        main.setCursorVisible(paused);
        pausedBackground.setVisible(paused);
    }

    @Override
    public void update(double deltaT, ArrayList<Integer> keyCodes) {
        if (paused)
            return;

        if (player1.lives <= 0 || player2.lives <= 0) {
            main.setScene(4);
        }

        // handle player indicator
        p1Indicator.setPos(player1.getX(), player1.getY() - 75);
        p2Indicator.setPos(player2.getX(), player2.getY() - 75);

        for (int i = 2; i >= 0; i--) {
            if (player1.lives < i + 1) {
                p1LivesDisplay[i].setVisible(false);
            }
            if (player2.lives < i + 1) {
                p2LivesDisplay[i].setVisible(false);
            }
        }

        tTime += deltaT;
        if (tTime >= 1) {
            tTime = 0;
            fpsText.setText("FPS: " + frames);
            frames = 0;
        }
        frames++;

    }

    @Override
    public void initialize() {
        main.resetCharacters();
        ground = new Ground(width * 0.5, height * 0.9, width, (int) (height * 0.2), "ground");
        add(ground);

        finalSelect = MapSelectScene.getmap();

        Map currentMap = new Map(allMaps[finalSelect], this);

        player1 = main.player1;
        player2 = main.player2;

        player1.setPlayer(1);
        player2.setPlayer(2);

        player1.setPos(new Vector2D(player1.getCollisionBox().getWidth() * 0.6,
                height - player1.getCollisionBox().getHeight() * 0.55));
        player2.setPos(new Vector2D(width - player2.getCollisionBox().getWidth() * 0.6,
                height - player2.getCollisionBox().getHeight() * 0.55));

        player1.setVisible(true);
        player2.setVisible(true);
        player1.initialize();
        player2.initialize();
        add(player1);
        add(player2);

        p1HealthBar = new HealthBar(new Vector2D(300, 80), main.player1);
        p2HealthBar = new HealthBar(new Vector2D(Main.width - 300, 80), main.player2);
        p1HealthBar.setVisible(true);
        p2HealthBar.setVisible(true);
        add(p1HealthBar);
        add(p2HealthBar);

        abilityBarP1 = new CooldownBar(new Vector2D(80, 730), player1, false, player1.abilityIcon);
        add(abilityBarP1);
        ultBarP1 = new CooldownBar(new Vector2D(220, 730), player1, true, player1.ultIcon);
        add(ultBarP1);
        abilityBarP2 = new CooldownBar(new Vector2D(1500 - 220, 730), player2, false, player2.abilityIcon);
        add(abilityBarP2);
        ultBarP2 = new CooldownBar(new Vector2D(1500 - 80, 730), player2, true, player2.ultIcon);
        add(ultBarP2);

        fpsText = new Text("FPS: ", new Font("assets/JetBrainsMono-Bold.ttf", Font.BOLD, 30), Color.BLACK,
                new Vector2D(120, 130), 200, 40, 4);
        fpsText.setVisible(true);
        add(fpsText);

        p1LivesDisplay = new Asset[3];
        p2LivesDisplay = new Asset[3];
        for (int i = 0; i < 3; i++) {
            p1LivesDisplay[i] = new Asset(
                    "assets/misc/heart.png",
                    new Vector2D(80 + i * 80, 200),
                    64,
                    64,
                    2);

            p1LivesDisplay[i].setVisible(true);
            add(p1LivesDisplay[i]);

            p2LivesDisplay[i] = new Asset(
                    "assets/misc/heart.png",
                    new Vector2D(1240 + i * 80, 200),
                    64,
                    64,
                    2);

            p2LivesDisplay[i].setVisible(true);
            add(p2LivesDisplay[i]);

        }

        exitButton = new Button(new Vector2D(Main.width / 2, Main.height / 2 + 50), main) {
            @Override
            protected void onClick() {
                updatePauseMenu(false);
                main.setScene(1);
            }
        };
        exitButton.setText("Exit");

        resumeButton = new Button(new Vector2D(Main.width / 2, Main.height / 2 - 100), main) {
            @Override
            protected void onClick() {
                updatePauseMenu(false);
            }
        };
        resumeButton.setText("Resume");

        add(exitButton);
        add(resumeButton);
        exitButton.setVisible(false);
        resumeButton.setVisible(false);

        pausedBackground = new Asset("assets/misc/pausedBackground.png",
                new Vector2D(Main.width / 2 + 1, Main.height / 2), 3);
        pausedBackground.rescale(1.2);
        add(pausedBackground);

        Asset background = new Asset(currentMap.backgroundPath, new Vector2D(width / 2, height / 2),
                0);
        background.setVisible(true);
        add(background);

        // init player indicator
        p1Indicator = new Asset("assets/misc/p1indicator.png", new Vector2D(width/2, height/2), 2);
        p2Indicator = new Asset("assets/misc/p2indicator.png", new Vector2D(width/2, height/2), 2);
        p1Indicator.rescale(.5);
        p2Indicator.rescale(.5);
        p1Indicator.setVisible(true);
        p2Indicator.setVisible(true);
        add(p1Indicator);
        add(p2Indicator);

    }

    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            updatePauseMenu(!paused);
        }
    }
}
