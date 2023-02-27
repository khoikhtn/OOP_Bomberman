package Bomberman.entities.Terrain;

import Bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class power_speed_up extends Item{
    public power_speed_up(int x, int y, Image img) {
        super(x, y, img);
    }

    public void change() {
        if(exploded_count > 0) {
            img = Sprite.powerup_speed.getFxImage();
        }
    }

    @Override
    public void update() {
        change();
    }
}
