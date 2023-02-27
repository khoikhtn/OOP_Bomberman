package Bomberman.entities.CharacterSubClass;

import Bomberman.BombermanGame;
import Bomberman.entities.Entity;
import Bomberman.entities.CharacterSubClass.Enemy;
import Bomberman.entities.Terrain.*;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Player extends Bomberman.entities.Character {
    public int bomb_numbers = 1;

    private boolean checkSpeedUp = false;

    public Player(int x, int y, Image img) {
        super(x, y, img);
        direction = "Left";
    }

    public void setCheckSpeedUp(boolean checkSpeedUp) {
        this.checkSpeedUp = checkSpeedUp;
    }

    public void sand_enter() {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Sand) {
                int item_x = stillObjects.get(i).getX();
                int item_y = stillObjects.get(i).getY();
                if (!((x >= item_x + 32 || x + 32 <= item_x) || (y >= item_y + 32 || y + 32 <= item_y))) {
                    speed = 1;
                }
            }
        }
    }

    public void water_enter() {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Water) {
                int item_x = stillObjects.get(i).getX();
                int item_y = stillObjects.get(i).getY();
                if (!((x >= item_x + 32 || x + 32 <= item_x) || (y >= item_y + 32 || y + 32 <= item_y))) {
                    speed = 5;
                }
            }
        }
    }

    public void grass_enter() {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Grass) {
                int item_x = stillObjects.get(i).getX();
                int item_y = stillObjects.get(i).getY();
                if (!((x >= item_x + 32 || x + 32 <= item_x) || (y >= item_y + 32 || y + 32 <= item_y))) {
                    if (checkSpeedUp) {
                        speed = 3;
                    } else {
                        speed = 2;
                    }
                }
            }
        }
    }

    @Override
    public void move() {
        if(death == true) {
            Sprite sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, death_timing, 30);
            img = sprite.getFxImage();
            return;
        }
        if(Entity.listEvent.contains(Entity.Key.W)) {
            moveUp();
            direction = "Up";
        }
        if(Entity.listEvent.contains(Entity.Key.S)) {
            moveDown();
            direction = "Down";
        }
        if(Entity.listEvent.contains(Entity.Key.D)) {
            moveRight();
            direction = "Right";
        }
        if(Entity.listEvent.contains(Entity.Key.A)) {
            moveLeft();
            direction = "Left";
        }
        if(Entity.listEvent.isEmpty()) {
            if(direction.equals("Right")) {
                img = Sprite.player_right.getFxImage();
            }
            else if(direction.equals("Left")) {
                img = Sprite.player_left.getFxImage();
            }
            else if(direction.equals("Down")) {
                img = Sprite.player_down.getFxImage();
            }
            else if(direction.equals("Up")) {
                img = Sprite.player_up.getFxImage();
            }
        }
    }

    public void item_pick_up() {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof power_speed_up) {
                int item_x = stillObjects.get(i).getX();
                int item_y = stillObjects.get(i).getY();
                if (!((x >= item_x + 32 || x + 32 <= item_x) || (y >= item_y + 32 || y + 32 <= item_y)) && (((power_speed_up) stillObjects.get(i)).exploded_count > 0)) {
                    stillObjects.remove(i);
                    speed = 3;
                }
            } else if (stillObjects.get(i) instanceof power_more_bombs) {
                int item_x = stillObjects.get(i).getX();
                int item_y = stillObjects.get(i).getY();
                if (!((x >= item_x + 32 || x + 32 <= item_x) || (y >= item_y + 32 || y + 32 <= item_y)) && (((power_more_bombs) stillObjects.get(i)).exploded_count > 0)) {
                    stillObjects.remove(i);
                    bomb_numbers++;
                }
            }
        }
    }

    @Override
    public void update() {
        move();
        grass_enter();
        sand_enter();
        water_enter();

        item_pick_up();
        time++;
    }
}