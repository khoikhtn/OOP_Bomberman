package Bomberman;

import Bomberman.graphics.Sprite;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class Gameover extends Ending{

    public Gameover() {
        super();
        try {
            Image backgroundIMG = new Image(new FileInputStream("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\gameover.png"));
            ImageView background = new ImageView(backgroundIMG);
            background.fitWidthProperty().bind(BombermanGame.mainStage.widthProperty());
            background.fitHeightProperty().bind(BombermanGame.mainStage.heightProperty());
            endingPane.getChildren().add(background);
            endingScene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        System.out.println("back to menu");
                        BombermanGame.checkMenu = true;
                        BombermanGame.mainStage.setScene(BombermanGame.menu.menuScene);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("cannot load gameoover image");
        }
    }
}
