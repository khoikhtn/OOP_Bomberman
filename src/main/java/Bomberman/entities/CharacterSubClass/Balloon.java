package Bomberman.entities.CharacterSubClass;

import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        direction = "Up";
    }

    @Override
    public void move() {
        if(death == true) {
            img = Sprite.balloom_dead.getFxImage();
            return;
        }

        if(direction == "Up") {
            if(!empty_up()) {
                direction = "Down";
            } else {
                moveUp();
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, time, 30);
                img = sprite.getFxImage();
            }

        } else if (direction == "Down") {
            if(!empty_down()) {
                direction = "Up";
            } else {
                moveDown();
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, time, 30);
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
