package main.ui;

import java.util.function.IntConsumer;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// Select Level ekranını Main'den ayırır; kullanıcı seçimini callback ile bildirir.
public class LevelSelectMenu {
    private final Scene scene;

    // Level butonlarını oluşturur ve seçilen level numarasını onLevelSelected'a gönderir.
    public LevelSelectMenu(double width, double height, IntConsumer onLevelSelected) {
        Group root = new Group();
        scene = new Scene(root, width, height, Color.web("#160022"));

        VBox layout = new VBox(35);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(width, height);

        Text title = new Text("SELECT LEVEL");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Impact", FontWeight.BOLD, 56));

        HBox levelButtons = new HBox(30);
        levelButtons.setAlignment(Pos.CENTER);
        levelButtons.getChildren().addAll(
            createLevelButton(1, "LEVEL 1", "#2b003a", onLevelSelected),
            createLevelButton(2, "LEVEL 2", "#07313a", onLevelSelected),
            createLevelButton(3, "LEVEL 3", "#3a1208", onLevelSelected)
        );

        layout.getChildren().addAll(title, levelButtons);
        root.getChildren().add(layout);
    }

    // Her level için renkli küçük harita önizlemeli bir seçim butonu oluşturur.
    private StackPane createLevelButton(int levelNumber, String text, String mapColor, IntConsumer onLevelSelected) {
        StackPane button = new StackPane();
        Rectangle bg = new Rectangle(260, 180, Color.PURPLE);
        Rectangle preview = new Rectangle(210, 120, Color.web(mapColor));
        preview.setTranslateY(-15);

        Text label = new Text(text);
        label.setFill(Color.WHITE);
        label.setFont(Font.font("Impact", FontWeight.BOLD, 26));
        label.setTranslateY(65);

        button.getChildren().addAll(bg, preview, label);
        button.setOnMouseEntered(e -> bg.setFill(Color.MEDIUMPURPLE));
        button.setOnMouseExited(e -> bg.setFill(Color.PURPLE));
        button.setOnMousePressed(e -> bg.setFill(Color.WHITE));
        button.setOnMouseReleased(e -> onLevelSelected.accept(levelNumber));
        return button;
    }

    // Main bu scene'i stage üzerine koyarak level seçim ekranını gösterir.
    public Scene getScene() {
        return scene;
    }
}
