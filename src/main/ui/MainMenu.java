package main.ui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenu {
    private Scene scene;

    // Main sınıfı bu butonlara event bağlar.
    private StackPane startBtn;
    private StackPane selectLevelBtn;
    private StackPane exitBtn;

    public MainMenu(double width, double height) {
        Group root = new Group();
        scene = new Scene(root, width, height, Color.web("#1a002a"));

        VBox menuBox = new VBox(20);
        menuBox.setPrefWidth(400);
        menuBox.setLayoutX((width / 2) - 200);
        menuBox.setLayoutY((height / 2) - 150);
        menuBox.setAlignment(Pos.CENTER);

        Text title = new Text("GHOST HUNTER INC.");
        title.setFont(Font.font("Impact", FontWeight.BOLD, 60));
        title.setFill(Color.MEDIUMPURPLE);
        title.setTranslateY(-40);

        startBtn = createButton("START GAME");
        selectLevelBtn = createButton("SELECT LEVEL");
        exitBtn = createButton("EXIT");

        menuBox.getChildren().addAll(title, startBtn, selectLevelBtn, exitBtn);
        root.getChildren().add(menuBox);
    }

    private StackPane createButton(String text) {
        StackPane btn = new StackPane();

        Rectangle bg = new Rectangle(250, 50, Color.PURPLE);
        Text label = new Text(text);
        label.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        label.setFill(Color.WHITE);

        btn.getChildren().addAll(bg, label);

        btn.setOnMousePressed(e -> {
            bg.setFill(Color.WHITE);
            label.setFill(Color.RED);
        });
        btn.setOnMouseReleased(e -> {
            bg.setFill(Color.PURPLE);
            label.setFill(Color.WHITE);
        });
        btn.setOnMouseEntered(e -> btn.setCursor(Cursor.HAND));
        btn.setOnMouseExited(e -> btn.setCursor(Cursor.DEFAULT));

        return btn;
    }

    public Scene getScene() {
        return scene;
    }

    public StackPane getStartBtn() {
        return startBtn;
    }

    public StackPane getSelectLevelBtn() {
        return selectLevelBtn;
    }

    public StackPane getExitBtn() {
        return exitBtn;
    }
}
