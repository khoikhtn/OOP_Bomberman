package Bomberman.entities.Terrain;

import javafx.scene.image.Image;

public abstract class Item extends Bomberman.entities.Entity {
    public int exploded_count = 0;

    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    public abstract void update();
}
