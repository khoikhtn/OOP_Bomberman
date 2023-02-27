package Bomberman.entities.CharacterSubClass;

import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Oneal extends Enemy {
    public Oneal (int x, int y, Image img) {
        super(x, y, img);
        direction = "Right";
    }

    @Override
    public void move() {
        if(death == true) {
            img = Sprite.oneal_dead.getFxImage();
            return;
        }

        if(direction == "Right") {
            if(!empty_right()) {
                direction = "Left";
            } else {
                moveRight();
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, time, 30);
                img = sprite.getFxImage();
            }

        } else if (direction == "Left") {
            if(!empty_left()) {
                direction = "Right";
            } else {
                moveLeft();
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, time, 30);
                img = sprite.getFxImage();
            }
        }
    }

    @Override
    public void update() {
        time++;
        if(time % 2 == 0) {
            move();
        }
    }
}
