package Bomberman.entities.Terrain;

import Bomberman.BombermanGame;
import Bomberman.entities.CharacterSubClass.Bomber;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Bomb extends Bomberman.entities.Entity {
    public Bomb() {}

    public Bomb(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public boolean exploded() {
        time++;

        if(time >= 80) {
            return true;
        } else if (time == 50) {
//            try {
//                Media bomb_sound = new Media(new File("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\bomb_sound.mp3").toURI().toString());
//                MediaPlayer bomb_sound_player = new MediaPlayer(bomb_sound);
//                bomb_sound_player.play();
//            } catch (Exception e) {
//                System.out.println("load sound effect fail");
//            }
            img = Sprite.bomb_exploded.getFxImage();

            Flame flame_up = new Flame(this.getX(), this.getY() - 32, Sprite.explosion_vertical_top_last.getFxImage(), "Up");
            Flame flame_down = new Flame(this.getX(), this.getY() + 32, Sprite.explosion_vertical_down_last.getFxImage(), "Down");
            Flame flame_right = new Flame(this.getX() + 32, this.getY(), Sprite.explosion_horizontal_right_last.getFxImage(), "Right");
            Flame flame_left = new Flame(this.getX() - 32, this.getY(), Sprite.explosion_horizontal_left_last.getFxImage(), "Left");
            Flame flame_center = new Flame(this.getX(), this.getY(), Sprite.bomb_exploded.getFxImage(), "Center");

            if(!flame_up.checkCollision()) {
                BombermanGame.stillObjects.add(flame_up);
            }
            if(!flame_down.checkCollision()) {
                BombermanGame.stillObjects.add(flame_down);
            }
            if(!flame_right.checkCollision()) {
                BombermanGame.stillObjects.add(flame_right);
            }
            if(!flame_left.checkCollision()) {
                BombermanGame.stillObjects.add(flame_left);
            }
            if(!flame_center.checkCollision()) {
                BombermanGame.stillObjects.add(flame_center);
            }
        } else if (time >= 50) {
            for(int j = 0; j < BombermanGame.stillObjects.size(); j++) {
                if(BombermanGame.stillObjects.get(j) instanceof Flame) {
                    Flame flame = (Flame) BombermanGame.stillObjects.get(j);
                    flame.time++;

                    if(flame.direction.equals("Up")) {
                        flame.img = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, time, 15).getFxImage();
                    } else if(flame.direction.equals("Down")) {
                        flame.img = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, time, 15).getFxImage();
                    } else if(flame.direction.equals("Right")) {
                        flame.img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, time, 15).getFxImage();
                    } else if(flame.direction.equals("Left")) {
                        flame.img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, time, 15).getFxImage();
                    } else if(flame.direction.equals("Center")) {
                        flame.img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, time, 15).getFxImage();
                    }

                    img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, time, 15).getFxImage();
                } else if(BombermanGame.stillObjects.get(j) instanceof Brick) {
                    Brick brick = (Brick) BombermanGame.stillObjects.get(j);
                    if (brick.exploded == true) {
                        brick.img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, time, 30).getFxImage();
                        brick.time++;
                    }
                }
            }
        } else {
            Sprite timing = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, time, 45);
            img = timing.getFxImage();
        }
        return false;
    }

    @Override
    public void update() {

    }


}
