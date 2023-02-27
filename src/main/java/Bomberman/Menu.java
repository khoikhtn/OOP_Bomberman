package Bomberman;

import Bomberman.BombermanGame;
import Bomberman.graphics.Sprite;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Menu {
    private AnchorPane mainPane;
    public Scene menuScene;
    private Btn one_player;

    private Btn two_players;
    private Btn exit_btn;
    private Media menuTheme;

    public MediaPlayer menuThemePlayer;

    public Menu() {
        mainPane = new AnchorPane();
        menuScene = new Scene(mainPane, BombermanGame.WIDTH * Sprite.SCALED_SIZE, BombermanGame.WIDTH * Sprite.SCALED_SIZE);
        setBackground();
        setAudio();
        one_player = createButton("1 Player", (BombermanGame.WIDTH * Sprite.SCALED_SIZE - 110) / 2, 225);
        two_players = createButton("2 Players", (BombermanGame.WIDTH * Sprite.SCALED_SIZE - 110) / 2, 275);
        exit_btn = createButton("EXIT", (BombermanGame.WIDTH * Sprite.SCALED_SIZE - 110) / 2, 325);
        exit_btn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    Platform.exit();
                    System.exit(0);
                }
            }
        });

        one_player.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    one_player.setButtonPressedStyle();
                }
            }
        });

        two_players.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    two_players.setButtonPressedStyle();
                }
            }
        });
    }

    public Btn getOne_player() {
        return one_player;
    }

    public Btn getTwo_players() {
        return two_players;
    }

    public Btn getExit_btn() {
        return exit_btn;
    }

    public void setBackground() {
        try {
            Image backgroundIMG = new Image(new FileInputStream("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\Menu_background.png"));
            ImageView background = new ImageView(backgroundIMG);
            background.fitWidthProperty().bind(BombermanGame.mainStage.widthProperty());
            background.fitHeightProperty().bind(BombermanGame.mainStage.heightProperty());
            mainPane.getChildren().add(background);
        } catch (FileNotFoundException e) {
            System.out.println("cannot load background");
        }

    }

    public void setAudio() {
        try {
            menuTheme = new Media(new File("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\menuTheme.mp3").toURI().toString());
            menuThemePlayer = new MediaPlayer(menuTheme);
            menuThemePlayer.setCycleCount(1000);
            menuThemePlayer.setVolume(0);
           // menuThemePlayer.play();
            menuThemePlayer.setOnRepeat(Thread.currentThread());
        } catch (Exception e) {
            System.out.println("cannot load sound effect");
            e.printStackTrace();
        }
    }

    public Btn createButton(String text, int x, int y) {
        Btn button = new Btn(text, x, y);
        mainPane.getChildren().add(button);
        return button;
    }

}