package Bomberman.entities.CharacterSubClass;

import Bomberman.BombermanGame;
import Bomberman.entities.Terrain.Bomb;
import Bomberman.graphics.Sprite;
import Bomberman.entities.CharacterSubClass.Bomber;
import Bomberman.graphics.SpriteSheet;
import javafx.scene.image.Image;

import java.util.LinkedList;

public class Kondoria extends Enemy {
    public boolean thinking = true;

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        direction = "Right";
    }

    @Override
    public void move() {
        int src = find_src();
        int dest = find_dest(BombermanGame.bomberman_x, BombermanGame.bomberman_y);

        int pred[] = new int[800];
        int dist[] = new int[800];

        BFS(src, dest, pred, dist);

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);

        // trace back the path
        while(pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        if (path.size() - 2 < 0) {
            return;
        }

        int cur_x = stillObjects.get(path.get(path.size() - 1)).getX();
        int cur_y = stillObjects.get(path.get(path.size() - 1)).getY();
        int next_x = stillObjects.get(path.get(path.size() - 2)).getX();
        int next_y = stillObjects.get(path.get(path.size() - 2)).getY();

        if (cur_y == next_y && cur_x == next_x - 32) {
            direction = "Right";
        } else if (cur_y == next_y && cur_x == next_x + 32) {
            direction = "Left";
        } else if (cur_x == next_x && cur_y == next_y - 32) {
            direction = "Down";
        } else if(cur_x == next_x && cur_y == next_y + 32) {
            direction = "Up";
        }
        thinking = false;
    }

    @Override
    public void update() {
        if (death == true) {
            img = Sprite.kondoria_dead.getFxImage();
            return;
        }

        adj.clear();
        Graph();
        addEdge();

        if (x % 32 == 0 && y % 32 == 0 && thinking == true) {
            move();
        } else if (direction == "Right") {
            moveRight();
            thinking = true;
            Sprite sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, time, 30);
            img = sprite.getFxImage();
        } else if (direction == "Left") {
            moveLeft();
            thinking = true;
            Sprite sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, time, 30);
            img = sprite.getFxImage();
        } else if (direction == "Up") {
            moveUp();
            thinking = true;
            Sprite sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, time, 30);
            img = sprite.getFxImage();
        } else if (direction == "Down") {
            moveDown();
            thinking = true;
            Sprite sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, time, 30);
            img = sprite.getFxImage();
        }
        time++;
    }
}
