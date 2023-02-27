package Bomberman.entities.CharacterSubClass;

import Bomberman.BombermanGame;
import Bomberman.entities.Entity;
import Bomberman.entities.CharacterSubClass.Enemy;
import Bomberman.entities.Terrain.*;
import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Bomber extends Bomberman.entities.Character {
    public int bomb_numbers = 1;
    private int back = 0;
    private boolean checkSpeedUp = false;
    public boolean auto = false;
    private boolean bomb_planted = false;
    private boolean thinking = true;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        direction = "right";
    }

    @Override
    public void move() {
        if(death == true) {
            Sprite sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, death_timing, 30);
            img = sprite.getFxImage();
            return;
        }
        if(Entity.listEvent.contains(Entity.Key.UP)) {
            moveUp();
            direction = "Up";
        }
        if(Entity.listEvent.contains(Entity.Key.DOWN)) {
            moveDown();
            direction = "Down";
        }
        if(Entity.listEvent.contains(Entity.Key.RIGHT)) {
            moveRight();
            direction = "Right";
        }
        if(Entity.listEvent.contains(Entity.Key.LEFT)) {
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

    public boolean death_by_enemy() {
        for(int i = 0; i < characters.size(); i++) {
            if(characters.get(i) instanceof Enemy) {
                int fx = characters.get(i).getX();
                int fy = characters.get(i).getY();
                if(!((x >= fx + 30 || x + 30 <= fx) || (y >= fy + 30 || y + 30 <= fy))) {
                    death = true;
                    return true;
                }
            }
        }
        return false;
    }

    public void item_pick_up() {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof power_speed_up) {
                int item_x = stillObjects.get(i).getX();
                int item_y = stillObjects.get(i).getY();
                if (!((x >= item_x + 32 || x + 32 <= item_x) || (y >= item_y + 32 || y + 32 <= item_y)) && (((power_speed_up) stillObjects.get(i)).exploded_count > 0)) {
                    stillObjects.remove(i);
                    speed = 3;
                    checkSpeedUp = true;
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

    public void portal_enter() {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Portal) {
                int item_x = stillObjects.get(i).getX();
                int item_y = stillObjects.get(i).getY();
                if (!((x >= item_x + 32 || x + 32 <= item_x) || (y >= item_y + 32 || y + 32 <= item_y))) {
                    BombermanGame.level++;
                    speed = 2;
                    bomb_numbers = 1;
                    checkSpeedUp = false;
                    try {
                        BombermanGame.createMap(BombermanGame.level);
                    } catch (Exception e) {
                        System.out.println("Cannot create map");
                    }
                }
            }
        }
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
                    if (checkSpeedUp == false) {
                        speed = 2;
                    } else {
                        speed = 3;
                    }
                }
            }
        }
    }

    private int portal_pos() {
        int j = 0;
        for(int i = 0; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Portal) {
                j = i;
            }
        }

        for(int i = 0; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Grass) {
                if(stillObjects.get(i).getX() == stillObjects.get(j).getX() &&
                        stillObjects.get(i).getY() == stillObjects.get(j).getY()) {
                    return i;
                }
            }
        }

        return 1;
    }

    public int enemy_index() {
        if (BombermanGame.level == 1) {
            for(int i = 0; i < characters.size(); i++) {
                if (characters.get(i) instanceof Enemy) {
                    return i;
                }
            }
        } else if (BombermanGame.level == 2) {
            for(int i = characters.size() - 1; i >= 0; i--) {
                if (characters.get(i) instanceof Enemy) {
                    return i;
                }
            }
        } else if (BombermanGame.level == 3) {
            for(int i = characters.size() - 1; i >= 0; i--) {
                if (characters.get(i) instanceof Enemy) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int destination() {
        if (enemy_index() == -1) {
            return portal_pos();
        } else {
            int ex = characters.get(enemy_index()).getX();
            int ey = characters.get(enemy_index()).getY();

            return find_dest(ex, ey);
        }
    }

    public void auto_pilot(int src, int dest) {
        int pred[] = new int[1000];
        int dist[] = new int[1000];

        BFS(src, dest, pred, dist);

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);

        // trace back the path
        while(pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        if(path.size() <= 4 && bomb_planted == false && enemy_index() != -1) {
            bomb_planted = true;
            Bomb bomb = new Bomb(x, y, Sprite.bomb.getFxImage());

            int x_bomb = (bomb.getX() + 12) / 32;
            int y_bomb = (bomb.getY() + 12) / 32;
            bomb.setX(x_bomb * 32);
            bomb.setY(y_bomb * 32);

            stillObjects.add(bomb);

            return;
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
        adj.clear();
        Graph();
        addEdge();

        if(auto == true) {
            speed = 2;
            bomb_numbers = 1;
            if (x % 32 == 0 && y % 32 == 0 && thinking == true) {
                if (bomb_planted == false) {
                    auto_pilot(find_src(), destination());
                } else {
                    if(BombermanGame.level == 1) {
                        auto_pilot(find_src(), find_dest(32, 32));
                    } else if(BombermanGame.level == 2) {
                        auto_pilot(find_src(), find_dest(904, 224));
                    } else if(BombermanGame.level == 3) {
                        auto_pilot(find_src(), find_dest(32, 500));
                    }
                    back++;
                    if (back == 4) {
                        back = 0;
                        bomb_planted = false;
                    }
                }
            } else if (direction == "Right") {
                moveRight();
                thinking = true;
            } else if (direction == "Left") {
                moveLeft();
                thinking = true;
            } else if (direction == "Up") {
                moveUp();
                thinking = true;
            } else if (direction == "Down") {
                moveDown();
                thinking = true;
            }
        } else {
            move();
        }

        grass_enter();
        sand_enter();
        water_enter();

        if(enemy_index() == -1) {
            portal_enter();
        }
        item_pick_up();
        time++;
    }

    public void setCheckSpeedUp(boolean checkSpeedUp) {
        this.checkSpeedUp = checkSpeedUp;
    }
}