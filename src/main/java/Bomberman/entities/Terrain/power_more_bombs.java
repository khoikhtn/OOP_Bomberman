package Bomberman.entities.Terrain;

import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class power_more_bombs extends Item{
    public power_more_bombs(int x, int y, Image img) {
        super(x, y, img);
    }

    public void change() {
        if(exploded_count > 0) {
            img = Sprite.powerup_bombs.getFxImage();
        }
    }

    @Override
    public void update() {
        change();
    }
}
