package Bomberman.entities.Terrain;

import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Flame extends Bomberman.entities.Entity {
    String direction;

    public Flame(int x, int y, Image img, String direction) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.direction = direction;
    }

    public boolean checkCollision() {
        for(int i = 1; i < stillObjects.size(); i++) {
            if(stillObjects.get(i) instanceof Item) {
                int terrain_x = stillObjects.get(i).getX();
                int terrain_y = stillObjects.get(i).getY();

                if (this.x == terrain_x && this.y == terrain_y) {
                    Item item = (Item) stillObjects.get(i);
                    if (item.exploded_count == 0) {
                        item.exploded_count ++;
                        return true;
                    }
                    return false;
                }
            }

            if(stillObjects.get(i) instanceof Brick) {
                int terrain_x = stillObjects.get(i).getX();
                int terrain_y = stillObjects.get(i).getY();

                if(this.x == terrain_x && this.y == terrain_y) {
                    Brick brick = (Brick) stillObjects.get(i);
                    brick.exploded = true;
                    return true;
                }
            }

            if(stillObjects.get(i) instanceof Bomb) {
                int terrain_x = stillObjects.get(i).getX();
                int terrain_y = stillObjects.get(i).getY();

                if(this.x == terrain_x && this.y == terrain_y) {
                    return false;
                }
            }

            if(!(stillObjects.get(i) instanceof Grass || stillObjects.get(i) instanceof Sand || stillObjects.get(i) instanceof Water)) {
                int terrain_x = stillObjects.get(i).getX();
                int terrain_y = stillObjects.get(i).getY();

                if(this.x == terrain_x && this.y == terrain_y) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update() {

    }
}
