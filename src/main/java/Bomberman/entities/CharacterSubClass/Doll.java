package Bomberman.entities.CharacterSubClass;

import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Doll extends Enemy {
    public Doll (int x, int y, Image img) {
        super(x, y, img);
        direction = "Right";
    }

    @Override
    public void move() {
        if(death == true) {
            img = Sprite.doll_dead.getFxImage();
            return;
        }
        if(empty_down()) {
            dir[0] = true;
            dem++;
        } else {
            dir[0] = false;
        }

        if(empty_up()) {
            dir[1] = true;
            dem++;
        } else {
            dir[1] = false;
        }

        if(empty_right()) {
            dir[2] = true;
            dem++;
        } else {
            dir[2] = false;
        }

        if(empty_left()) {
            dir[3] = true;
            dem++;
        } else {
            dir[3] = false;
        }

        // Check all possible ways and random to change a path
        if((dem >= 3 && this.x % 32 == 0 && this.y % 32 == 0) ||
                (direction == "Up" && !empty_up()) ||
                (direction == "Down" && !empty_down()) ||
                (direction == "Right" && !empty_right()) ||
                (direction == "Left" && !empty_left())) {

            go = false;
            waiting++;

            if (waiting == 10) {
                go = true;
                waiting = 0;

                int rand = (int) (Math.random() * (dem + 1));

                int i, j = 0;
                for (i = 0; i < 4; i++) {
                    if (j == rand && dir[i] == true) {
                        break;
                    } else if (j == rand && dir[i] == false) {
                        continue;
                    } else if (j != rand && dir[i] == true) {
                        j++;
                    }
                }

                if (i == 0) {
                    direction = "Down";
                } else if (i == 1) {
                    direction = "Up";
                } else if (i == 2) {
                    direction = "Right";
                } else {
                    direction = "Left";
                }

            }
        }

        if (go == true && death == false) {
            if(direction == "Down") {
                moveDown();
                Sprite sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, time, 30);
                img = sprite.getFxImage();
            } else if(direction == "Up") {
                moveUp();
                Sprite sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, time, 30);
                img = sprite.getFxImage();
            } else if(direction == "Right") {
                moveRight();
                Sprite sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, time, 30);
                img = sprite.getFxImage();
            } else if(direction == "Left") {
                moveLeft();
                Sprite sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, time, 30);
                img = sprite.getFxImage();
            }
        }
        dem = 0;
    }

    @Override
    public void update() {
        time++;
        move();
    }
}
