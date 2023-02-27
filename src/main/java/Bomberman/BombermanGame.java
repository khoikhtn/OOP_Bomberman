package Bomberman;

import Bomberman.entities.*;
import Bomberman.entities.Character;
import Bomberman.entities.CharacterSubClass.*;
import Bomberman.entities.Terrain.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Bomberman.graphics.Sprite;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 19;
    public static Stage mainStage;
    public static int level = 1;

    public static boolean checkMenu = true;
    public static boolean checkPause = false;

    public static char matrix[][] = null;

    private GraphicsContext gc;
    private Canvas canvas;
    public static Menu menu;

    private Gameover gameover;
    private Win win;
    private Pause pause;

    public static List<Entity> characters = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static Group root = new Group();
    public static Scene ingame = new Scene(root);

    static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    static Player player = new Player(29, 1, Sprite.player_left.getFxImage());

    public static int bomberman_x;
    public static int bomberman_y;

    private static void createMapFromFile(int level) throws IOException {
        characters.clear();
        stillObjects.clear();
        BufferedReader bufferedReader = null;
        if (level == 4) {
            bomberman.auto = false;
            return;
        }
        try {
            Reader reader = new FileReader("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\levels\\Level" + ((Integer) level).toString() +".txt");
            bufferedReader = new BufferedReader(reader);

            String firstLine = bufferedReader.readLine();
            System.out.println(firstLine);

            int row = 0;
            int column = 0;

            String[] tokens = firstLine.split(" ");

            row = Integer.parseInt(tokens[1]);
            column = Integer.parseInt(tokens[2]);

            matrix = new char[row][column];

            for (int i = 0; i < row; i++) {
                String line = bufferedReader.readLine();
                for (int j = 0; j < column; j++) {
                    char character = line.charAt(j);
                    matrix[i][j] = character;
                }
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    char character = matrix[i][j];

                    Entity object1 = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object1);

                    switch (character) {
                        case '#': {
                            Entity object = new Wall(j, i, Sprite.wall.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
                        case '*' : {
                            Entity object2 = new Brick(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(object2);
                            break;
                        }
                        case 'o' : {
                            Oneal oneal = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                            characters.add(oneal);
                            break;
                        }
                        case 'b' : {
                            Balloon balloon = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                            characters.add(balloon);
                            break;
                        }
                        case 'd' : {
                            Doll doll = new Doll(j, i, Sprite.doll_left1.getFxImage());
                            characters.add(doll);
                            break;
                        }
                        case 'r' : {
                            Kondoria kondoria = new Kondoria(j, i, Sprite.kondoria_right1.getFxImage());
                            characters.add(kondoria);
                            break;
                        }
                        case '1' : {
                            Item sp = new power_speed_up(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(sp);
                            try {
                                sp.img = new Image(new FileInputStream("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\final_texture\\wood05.jpg"));
                            } catch (Exception e) {
                                System.out.println("cannot load brick");
                                e.printStackTrace();
                            }
                            break;
                        }
                        case '2' : {
                            Item mb = new power_more_bombs(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(mb);
                            try {
                                mb.img = new Image(new FileInputStream("D:\\22.06.04 UET\\Code\\Java\\OOP_Bomberman\\src\\main\\resources\\final_texture\\wood05.jpg"));
                            } catch (Exception e) {
                                System.out.println("cannot load brick");
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 'k' : {
                            bomberman.setX(j * Sprite.SCALED_SIZE);
                            bomberman.setY(i * Sprite.SCALED_SIZE);
                            bomberman.img = Sprite.player_right.getFxImage();
                            characters.add(bomberman);
                            break;
                        }
                        case 'l' : {
                            characters.add(player);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            }

            for(int i = 0; i < row; i++) {
                for(int j = 0; j < column; j++) {
                    char character = matrix[i][j];
                    switch (character) {
                        case 'p' : {
                            Portal portal = new Portal(j, i, Sprite.portal.getFxImage());
                            stillObjects.add(portal);
                            break;
                        }
                        case 's' : {
                            Entity object3 = new Sand(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object3);
                            break;
                        }
                        case 'w' : {
                            Entity object4 = new Water(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object4);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi create map");
        } finally {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tao canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root.getChildren().add(canvas);

        ingame.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        if (!Entity.listEvent.contains(Entity.Key.UP)) {
                            Entity.listEvent.add(Entity.Key.UP);
                        }
                        break;
                    case DOWN:
                        if (!Entity.listEvent.contains(Entity.Key.DOWN)) {
                            Entity.listEvent.add(Entity.Key.DOWN);
                        }
                        break;
                    case RIGHT:
                        if (!Entity.listEvent.contains(Entity.Key.RIGHT)) {
                            Entity.listEvent.add(Entity.Key.RIGHT);
                        }
                        break;
                    case LEFT:
                        if (!Entity.listEvent.contains(Entity.Key.LEFT)) {
                            Entity.listEvent.add(Entity.Key.LEFT);
                        }
                        break;
                    case X:
                        Bomb bomb = new Bomb(bomberman.getX(), bomberman.getY(), Sprite.bomb.getFxImage());

                        int x_bomb = (bomb.getX() + 12) / 32;
                        int y_bomb = (bomb.getY() + 12) / 32;
                        bomb.setX(x_bomb * 32);
                        bomb.setY(y_bomb * 32);

                        int count = 0;
                        for(int i = 0; i < stillObjects.size(); i++) {
                            if(stillObjects.get(i) instanceof Bomb) {
                                count++;
                            }
                        }

                        if (count < bomberman.bomb_numbers) {
                            stillObjects.add(bomb);
                        }
                        break;
                    case Z:
                        if (bomberman.auto == true) {
                            bomberman.auto = false;
                        } else {
                            bomberman.auto = true;
                            bomberman.setX(((bomberman.getX() + 12) / 32) * 32);
                            bomberman.setY(((bomberman.getY() + 12) / 32) * 32);
                        }
                        break;
                    case W:
                        if (!Entity.listEvent.contains(Entity.Key.W)) {
                            Entity.listEvent.add(Entity.Key.W);
                        }
                        break;
                    case S:
                        if (!Entity.listEvent.contains(Entity.Key.S)) {
                            Entity.listEvent.add(Entity.Key.S);
                        }
                        break;
                    case D:
                        if (!Entity.listEvent.contains(Entity.Key.D)) {
                            Entity.listEvent.add(Entity.Key.D);
                        }
                        break;
                    case A:
                        if (!Entity.listEvent.contains(Entity.Key.A)) {
                            Entity.listEvent.add(Entity.Key.A);
                        }
                        break;
                    case Q:
                        Bomb bomb1 = new Bomb(player.getX(), player.getY(), Sprite.bomb.getFxImage());

                        int x_bomb1 = (bomb1.getX() + 12) / 32;
                        int y_bomb1 = (bomb1.getY() + 12) / 32;
                        bomb1.setX(x_bomb1 * 32);
                        bomb1.setY(y_bomb1 * 32);

                        int count1 = 0;
                        for(int i = 0; i < stillObjects.size(); i++) {
                            if(stillObjects.get(i) instanceof Bomb) {
                                count1++;
                            }
                        }

                        if (count1 < bomberman.bomb_numbers && level == 5) {
                            stillObjects.add(bomb1);
                        }
                        break;
                    case ESCAPE:
                        checkPause = true;
                        mainStage.setScene(pause.getPauseScene());
                }
            }
        });

        ingame.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        if (Entity.listEvent.contains(Entity.Key.UP)) {
                            Entity.listEvent.remove(Entity.Key.UP);
                        }
                        break;
                    case DOWN:
                        if (Entity.listEvent.contains(Entity.Key.DOWN)) {
                            Entity.listEvent.remove(Entity.Key.DOWN);
                        }
                        break;
                    case RIGHT:
                        if (Entity.listEvent.contains(Entity.Key.RIGHT)) {
                            Entity.listEvent.remove(Entity.Key.RIGHT);
                        }
                        break;
                    case LEFT:
                        if (Entity.listEvent.contains(Entity.Key.LEFT)) {
                            Entity.listEvent.remove(Entity.Key.LEFT);
                        }
                    case W:
                        if (Entity.listEvent.contains(Entity.Key.W)) {
                            Entity.listEvent.remove(Entity.Key.W);
                        }
                        break;
                    case S:
                        if (Entity.listEvent.contains(Entity.Key.S)) {
                            Entity.listEvent.remove(Entity.Key.S);
                        }
                        break;
                    case D:
                        if (Entity.listEvent.contains(Entity.Key.D)) {
                            Entity.listEvent.remove(Entity.Key.D);
                        }
                        break;
                    case A:
                        if (Entity.listEvent.contains(Entity.Key.A)) {
                            Entity.listEvent.remove(Entity.Key.A);
                        }
                        break;
                }
            }
        });

        bomberman.death = true;
        mainStage = new Stage();
        menu = new Menu();
        gameover = new Gameover();
        win = new Win();
        pause = new Pause();
        mainStage.setScene(menu.menuScene);
        mainStage.setWidth(Sprite.SCALED_SIZE * WIDTH);
        mainStage.setHeight(Sprite.SCALED_SIZE * HEIGHT + 28);
        stage = mainStage;
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                if (!checkPause) {
                    update();
                }
                if ((bomberman.death || player.death) && level == 5 && !checkMenu && !checkPause) {
                    mainStage.setScene(win.getEndingScene());
                } else if (level == 4 && !checkMenu && !checkPause) {
                    mainStage.setScene(win.getEndingScene());
                } else if (bomberman.death && !checkMenu && bomberman.death_timing >= 28 && !checkPause) {
                    mainStage.setScene(gameover.getEndingScene());
                } else if (!bomberman.death && !checkMenu && !checkPause) {
                    mainStage.setScene(ingame);
                }
            }
        };
        menu.getOne_player().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    menu.getOne_player().setButtonPressedStyle();
                    mainStage.setScene(ingame);
                    bomberman.death = false;
                    player.death = false;
                    level = 1;
                    try {
                        createMap(level);
                        bomberman.setX(32);
                        bomberman.setY(32);
                        bomberman.speed = 2;
                        bomberman.bomb_numbers = 1;
                        bomberman.setCheckSpeedUp(false);
                        Entity.listEvent.clear();
                        checkMenu = false;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    menu.getOne_player().setButtonReleasedStyle();
                }
            }
        });

        menu.getTwo_players().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    menu.getTwo_players().setButtonPressedStyle();
                    mainStage.setScene(ingame);
                    bomberman.death = false;
                    player.death = false;
                    level = 5;
                    try {
                        createMap(level);
                        bomberman.setX(Sprite.SCALED_SIZE);
                        bomberman.setY(Sprite.SCALED_SIZE);
                        bomberman.speed = 2;
                        bomberman.bomb_numbers = 1;
                        bomberman.setCheckSpeedUp(false);

                        player.setX(Sprite.SCALED_SIZE * 28);
                        player.setY(Sprite.SCALED_SIZE * 15);
                        player.speed = 2;
                        player.bomb_numbers = 1;
                        player.setCheckSpeedUp(false);
                        Entity.listEvent.clear();
                        checkMenu = false;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    menu.getTwo_players().setButtonReleasedStyle();
                }
            }
        });

        timer.start();
        createMap(level);
    }


    public static void createMap(int level) throws IOException {
        createMapFromFile(level);
    }

    public void update() {
        try {
            bomberman_x = bomberman.getX();
            bomberman_y = bomberman.getY();

            characters.forEach(Entity::update);
            stillObjects.forEach(Entity::update);

            for (int i = 0; i < stillObjects.size(); i++) {
                if (stillObjects.get(i) instanceof Bomb) {
                    if (((Bomb) stillObjects.get(i)).exploded()) {
                        stillObjects.remove(i);
                        i--;
                    }
                }
                for (int j = 0; j < stillObjects.size(); j++) {
                    if (BombermanGame.stillObjects.get(j) instanceof Flame) {
                        if (BombermanGame.stillObjects.get(j).time == 29) {
                            BombermanGame.stillObjects.remove(j);
                            j--;
                        }
                    } else if (BombermanGame.stillObjects.get(j) instanceof Brick) {
                        if (BombermanGame.stillObjects.get(j).time == 29) {
                            BombermanGame.stillObjects.remove(j);
                            j--;
                        }
                    }
                }
            }

            // Death handling
            for (int i = 0; i < characters.size(); i++) {
                if (characters.get(i) instanceof Bomber) {
                    Bomber bomber = (Bomber) characters.get(i);
                    if (bomber.death == true) {
                        bomber.death_timing++;
                        if (bomber.death_timing >= 28) {
//                            characters.remove(i);
//                            i--;
                        }

                    }

                    if (bomber.death_by_enemy() || bomber.death_by_flame()) {
                        bomber.move();
                    }
                } else if (characters.get(i) instanceof Player) {
                    Player player = (Player) characters.get(i);
                    if (player.death == true) {
                        player.death_timing++;
                        if (player.death_timing >= 28) {
//                            characters.remove(i);
//                            i--;
                        }

                    }

                    if (player.death_by_flame()) {
                        player.move();
                    }
                } else if (characters.get(i) instanceof Enemy) {
                    Enemy enemy = (Enemy) characters.get(i);
                    if (enemy.death == true) {
                        enemy.death_timing++;
                        if (enemy.death_timing >= 28) {
                            characters.remove(i);
                            i--;
                        }
                    }

                    if (enemy.death_by_flame())
                        enemy.move();
                }

            }
        } catch (ConcurrentModificationException e) {
            System.out.println("level up successful!");
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        characters.forEach(g -> g.render(gc));
    }
}