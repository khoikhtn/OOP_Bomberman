package Bomberman.entities;

import Bomberman.BombermanGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import Bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    protected int x;
    protected int y;
    public Image img;

    public int time = 0;
    public static List<Key> listEvent = new ArrayList<>();
    public List<Entity> stillObjects = BombermanGame.stillObjects;
    public List<Entity> characters = BombermanGame.characters;
    public static enum Key {
        RIGHT,
        LEFT,
        UP,
        DOWN,
        W,
        A,
        S,
        D
    }

    public Entity() {}

    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();
}