package Bomberman.entities;

import Bomberman.BombermanGame;
import Bomberman.entities.Terrain.*;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Character extends Entity{
    public int death_timing = 0;
    public int dem = 0;
    public int waiting = 0;
    public int speed = 2;

    public String direction = "Right";

    public boolean[] dir = new boolean[4];
    public boolean death = false;
    public boolean go = true;

    public ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(800);

    public Character(int x, int y, Image img) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public abstract void move();

    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean empty_up() {
        for(int i = 0; i < stillObjects.size(); i++) {
            if(!(stillObjects.get(i) instanceof Grass)) {
                Entity obj = stillObjects.get(i);
                if(obj.getX() == this.x && obj.getY() + 32 == this.y) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean empty_down() {
        for(int i = 0; i < stillObjects.size(); i++) {
            if(!(stillObjects.get(i) instanceof Grass)) {
                Entity obj = stillObjects.get(i);
                if(obj.getX() == this.x && obj.getY() - 32 == this.y) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean empty_right() {
        for(int i = 0; i < stillObjects.size(); i++) {
            if(!(stillObjects.get(i) instanceof Grass)) {
                Entity obj = stillObjects.get(i);
                if(obj.getX() == this.x + 32 && obj.getY() == this.y) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean empty_left() {
        for(int i = 0; i < stillObjects.size(); i++) {
            if(!(stillObjects.get(i) instanceof Grass)) {
                Entity obj = stillObjects.get(i);
                if(obj.getX() == this.x - 32 && obj.getY() == this.y) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveUp() {
        Sprite sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, time, 30);
        img = sprite.getFxImage();
        y -= speed;
        for(int i = 0; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Wall || stillObjects.get(i) instanceof Brick || stillObjects.get(i) instanceof Bomb || (stillObjects.get(i) instanceof Item && ((Item) stillObjects.get(i)).exploded_count == 0)){
                int wallx = stillObjects.get(i).getX();
                int wally = stillObjects.get(i).getY();
                if(y > wally + 20 && y < wally + 24) {
                    if((x >= wallx && x < wallx + 32) || (x + 24 > wallx && x < wallx)) {
                        y += speed;
                        break;
                    }
                }
            }
        }
    }

    public void moveDown() {
        Sprite sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, time, 30);
        img = sprite.getFxImage();
        y += speed;
        for(int i = 0; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Wall || stillObjects.get(i) instanceof Brick || stillObjects.get(i) instanceof Bomb || (stillObjects.get(i) instanceof Item && ((Item) stillObjects.get(i)).exploded_count == 0)){
                int wallx = stillObjects.get(i).getX();
                int wally = stillObjects.get(i).getY();
                if(y + 32 > wally && y + 28 < wally) {
                    if((x >= wallx && x < wallx + 32) || (x + 24 > wallx && x < wallx)) {
                        y -= speed;
                        break;
                    }
                }
            }
        }
    }

    public void moveRight() {
        Sprite sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, time, 30);
        img = sprite.getFxImage();
        x += speed;
        for(int i = 0; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Wall || stillObjects.get(i) instanceof Brick || stillObjects.get(i) instanceof Bomb || (stillObjects.get(i) instanceof Item && ((Item) stillObjects.get(i)).exploded_count == 0)){
                int wallx = stillObjects.get(i).getX();
                int wally = stillObjects.get(i).getY();
                if(x + 24 > wallx && x + 20 < wallx) {
                    if((y >= wally && y < wally + 24) || (y + 32 > wally && y < wally)) {
                        x -= speed;
                        break;
                    }
                }
            }
        }
    }

    public void moveLeft() {
        Sprite sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, time, 30);
        img = sprite.getFxImage();
        x -= speed;
        for(int i = 0; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Wall || stillObjects.get(i) instanceof Brick || stillObjects.get(i) instanceof Bomb || (stillObjects.get(i) instanceof Item && ((Item) stillObjects.get(i)).exploded_count == 0)){
                int wallx = stillObjects.get(i).getX();
                int wally = stillObjects.get(i).getY();
                if(x < wallx + 32 && x > wallx + 28) {
                    if((y >= wally && y < wally + 24) || (y + 32 > wally && y < wally)) {
                        x += speed;
                        break;
                    }
                }
            }
        }
    }

    public boolean death_by_flame() {
        for(int i = 0; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Flame) {
                int fx = stillObjects.get(i).getX();
                int fy = stillObjects.get(i).getY();
                if(!((x >= fx + 32 || x + 32 <= fx) || (y >= fy + 32 || y + 32 <= fy))) {
                    // death_timing = stillObjects.get(i).time;
                    death = true;
                    return true;
                }
            }
        }
        return false;
    }

    public int find_src() {
        int srcx = ((x + 12) / 32) * 32;
        int srcy = ((y + 12) / 32) * 32;

        for(int i = 0; i < stillObjects.size(); i++) {
            int pos_x = stillObjects.get(i).getX();
            int pos_y = stillObjects.get(i).getY();

            if (srcx == pos_x && srcy == pos_y) {
                return i;
            }
        }
        return 1;
    }

    public int find_dest(int dx, int dy) {
        int destx = ((dx + 12) / 32) * 32;
        int desty = ((dy + 12) / 32) * 32;

        for(int i = 0; i < stillObjects.size(); i++) {
            int pos_x = stillObjects.get(i).getX();
            int pos_y = stillObjects.get(i).getY();

            if (destx == pos_x && desty == pos_y) {
                return i;
            }
        }
        return 1;
    }

    public void Graph() {
        for(int i = 0; i < 800; i++) {
            adj.add(new ArrayList<Integer>());
        }
    }

    public void addEdge() {
        for(int i = 0; i < stillObjects.size() - 1; i++) {
            for(int j = 0; j < stillObjects.size() - 1; j++) {
                if(stillObjects.get(i) instanceof Grass && stillObjects.get(i + 1) instanceof Grass &&
                        stillObjects.get(j) instanceof Grass && stillObjects.get(j + 1) instanceof Grass) {
                    int main_x = stillObjects.get(i).getX();
                    int main_y = stillObjects.get(i).getY();
                    int side_x = stillObjects.get(j).getX();
                    int side_y = stillObjects.get(j).getY();

                    if((main_x == side_x && main_y == side_y + 32) ||
                            (main_x == side_x && main_y == side_y - 32) ||
                            (main_y == side_y && main_x == side_x + 32) ||
                            (main_y == side_y && main_x == side_x - 32)) {
                        adj.get(i).add(j);
                        //System.out.println(i + " " + j);
                    }
                }
            }
        }
    }

    public void BFS(int src, int dest, int pred[], int dist[]) {
        LinkedList<Integer> queue = new LinkedList<Integer>();

        boolean visited[] = new boolean[800];

        for(int i = 0; i < 800; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        while(!queue.isEmpty()) {
            int u = queue.remove();
            for(int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    if(adj.get(u).get(i) == dest) {
                        return;
                    }
                }
            }
        }
    }

    public abstract void update();
}
