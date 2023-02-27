package Bomberman.entities.CharacterSubClass;

import Bomberman.entities.Entity;
import Bomberman.entities.Terrain.*;
import javafx.scene.image.Image;

public abstract  class Enemy extends Bomberman.entities.Character {
    public Enemy(int x, int y, Image img) {
        super(x, y, img);
        direction = "Right";
    }

    public abstract void move();
//        if(death == true) {
//            return;
//        }
//        if(empty_down()) {
//            dir[0] = true;
//            dem++;
//        } else {
//            dir[0] = false;
//        }
//
//        if(empty_up()) {
//            dir[1] = true;
//            dem++;
//        } else {
//            dir[1] = false;
//        }
//
//        if(empty_right()) {
//            dir[2] = true;
//            dem++;
//        } else {
//            dir[2] = false;
//        }
//
//        if(empty_left()) {
//            dir[3] = true;
//            dem++;
//        } else {
//            dir[3] = false;
//        }
//
//        // Check all possible ways and random to change a path
//        if((dem >= 3 && this.x % 32 == 0 && this.y % 32 == 0) ||
//                (direction == "Up" && !empty_up()) ||
//                (direction == "Down" && !empty_down()) ||
//                (direction == "Right" && !empty_right()) ||
//                (direction == "Left" && !empty_left())) {
//
//            go = false;
//            waiting++;
//
//            if (waiting == 10) {
//                go = true;
//                waiting = 0;
//
//                int rand = (int) (Math.random() * (dem + 1));
//
//                int i, j = 0;
//                for (i = 0; i < 4; i++) {
//                    if (j == rand && dir[i] == true) {
//                        break;
//                    } else if (j == rand && dir[i] == false) {
//                        continue;
//                    } else if (j != rand && dir[i] == true) {
//                        j++;
//                    }
//                }
//
//                if (i == 0) {
//                    direction = "Down";
//                } else if (i == 1) {
//                    direction = "Up";
//                } else if (i == 2) {
//                    direction = "Right";
//                } else {
//                    direction = "Left";
//                }
//
//            }
//        }
//
//        if (go == true && death == false) {
//            if(direction == "Down") {
//                moveDown();
//            } else if(direction == "Up") {
//                moveUp();
//            } else if(direction == "Right") {
//                moveRight();
//            } else if(direction == "Left") {
//                moveLeft();
//            }
//        }
//        dem = 0;
//        time++;

    public abstract void update();
}
