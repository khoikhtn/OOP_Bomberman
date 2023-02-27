package Bomberman;

import Bomberman.graphics.Sprite;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;

public class Pause {
    private Scene pauseScene;
    private AnchorPane pausePane;

    public Pause() {
        pausePane = new AnchorPane();
        pauseScene = new Scene(pausePane, BombermanGame.WIDTH * Sprite.SCALED_SIZE, BombermanGame.WIDTH * Sprite.SCALED_SIZE);
        try {
            Image backgroundIMG = new Image(new FileInputStream("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\pause.png"));
            ImageView background = new ImageView(backgroundIMG);
            background.fitWidthProperty().bind(BombermanGame.mainStage.widthProperty());
            background.fitHeightProperty().bind(BombermanGame.mainStage.heightProperty());
            pausePane.getChildren().add(background);
            pauseScene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        System.out.println("back to menu");
                        BombermanGame.checkPause = false;
                        BombermanGame.mainStage.setScene(BombermanGame.menu.menuScene);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("cannot load win image");
        }
    }

    public Scene getPauseScene() {
        return pauseScene;
    }
}