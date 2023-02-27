package Bomberman.entities.Terrain;

import javafx.scene.image.Image;

import java.io.FileInputStream;

public class Sand extends Bomberman.entities.Entity {
    public Sand(int x, int y, Image img) {
        super(x, y, img);
        try {
            this.img = new Image(new FileInputStream("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\final_texture\\SAND.png"));
        } catch (Exception e) {
            System.out.println("cannot load sand");
        }
    }

    @Override
    public void update() {

    }
}