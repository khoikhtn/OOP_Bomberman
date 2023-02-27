package Bomberman;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Btn extends Button {
    private final String FONT_PATH = "D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\ARCADECLASSIC.TTF";
    protected final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url(button_clicked.png); " +
            "-fx-background-repeat: no-repeat; -fx-background-position: center center;-fx-background-size: cover; -fx-text-fill: Black";
    protected final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url(button.png); " +
            "-fx-background-repeat: no-repeat; -fx-background-position: center center;-fx-background-size: cover; -fx-text-fill: #BADC94";

    public Btn(String text, double x, double y) {
        setText(text);
        setButtonFont();
        setPrefWidth(110);
        setPrefHeight(45);
        setLayoutX(x);
        setLayoutY(y);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListener();
    }

    private void setButtonFont() {
        try {
            setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 12));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 12));
        }
    }

    public void setButtonPressedStyle() {
        Media btn_clicked_sound = new Media(new File("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\buttonSound.mp3").toURI().toString());
        MediaPlayer btn_clicked_sound_player = new MediaPlayer(btn_clicked_sound);
        btn_clicked_sound_player.setVolume(0.1);
        btn_clicked_sound_player.play();
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
    }

    public void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(45);
    }

    private void initializeButtonListener() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }

}