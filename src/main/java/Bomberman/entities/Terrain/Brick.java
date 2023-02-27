package Bomberman.entities.Terrain;

import javafx.scene.image.Image;

import java.io.FileInputStream;

public class Brick extends Bomberman.entities.Entity {
    boolean exploded = false;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        try {
            this.img = new Image(new FileInputStream("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\final_texture\\wood05.jpg"));
        } catch (Exception e) {
            System.out.println("cannot load brick");
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }
}
