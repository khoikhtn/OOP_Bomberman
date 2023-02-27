package Bomberman;

import Bomberman.graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public abstract class Ending {
    protected AnchorPane endingPane;
    protected Scene endingScene;

    public Ending() {
        endingPane = new AnchorPane();
        endingScene = new Scene(endingPane,  BombermanGame.WIDTH * Sprite.SCALED_SIZE, BombermanGame.WIDTH * Sprite.SCALED_SIZE);
    }

    public Scene getEndingScene() {
        return endingScene;
    }
}
