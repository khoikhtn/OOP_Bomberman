module com.example.oop_bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens Bomberman to javafx.fxml;
    exports Bomberman;
}