import java.util.ArrayList;

import ky.Asset;
import ky.AudioPlayer;
import ky.Entity;
import ky.Vector2D;

public class SelectionCursor extends Entity {

    public boolean selected = false;
    private int player = 0;
    private Asset cursorAsset;
    private boolean liftedLeft = true;
    private boolean liftedRight = true;
    private int leftKey;
    private int rightKey;
    private int selectKey;
    public int characterIndex;
    private AudioPlayer charselect;

    private Main main;

    public SelectionCursor(String filepath, Vector2D position, int layer, int player, Main main) {
        super(position, layer, "cursor");
        this.player = player;
        this.main = main;

        cursorAsset = new Asset(filepath, new Vector2D(0, 0), 4);
        cursorAsset.rescale(2);
        cursorAsset.setVisible(true);
        add(cursorAsset);
        setVisible(true);

        PlayerInput inputSystem = player == 1 ? PlayerInput.PLAYER_ONE_INPUT : PlayerInput.PLAYER_TWO_INPUT;
        characterIndex = player;
        bindKeys(inputSystem);
        charselect = new AudioPlayer("assets/SFX/characterselect.wav");
    }

    private void bindKeys(PlayerInput i) {
        leftKey = i.leftKey.get();
        rightKey = i.rightKey.get();
        selectKey = i.attackKey.get();
    }

    // this probably needs some major refactoring
    @Override
    public void update(double deltaT, ArrayList<Integer> keyCodes) {
        if (!keyCodes.contains(leftKey)) {
            liftedLeft = true;
        }
        if (!keyCodes.contains(rightKey)) {
            liftedRight = true;
        }
        if (keyCodes.contains(selectKey)) {
            selected = true;
            if (player == 1) {
                main.player1 = main.characters[characterIndex][0];
            } else if (player == 2) {
                main.player2 = main.characters[characterIndex][1];
            }
        }
        if (!selected) {
            if (keyCodes.contains(rightKey) && liftedRight) {
                if (characterIndex < 3) {
                    this.addPos(cursorAsset.getWidth() + 20, 0);
                    liftedRight = false;
                    characterIndex++;
                    charselect.reset();
                    charselect.play();
                }
            }
            if (keyCodes.contains(leftKey) && liftedLeft) {
                if (characterIndex > 0) {
                    this.addPos(-(cursorAsset.getWidth() + 20), 0);
                    liftedLeft = false;
                    characterIndex--;
                    charselect.reset();
                    charselect.play();
                }
            }
        }
    }

}
