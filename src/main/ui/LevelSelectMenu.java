package main.ui;

import java.util.function.IntConsumer;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Select Level ekranını Main'den ayırır; kullanıcı seçimini callback ile bildirir.
public class LevelSelectMenu {
    private final Scene scene;

    // Level butonlarını oluşturur ve seçilen level numarasını onLevelSelected'a gönderir.
    public LevelSelectMenu(double width, double height, IntConsumer onLevelSelected) {
        Group root = new Group();
        scene = new Scene(root, width, height, Color.web("#160022"));

        ImageView background = new ImageView(new Image("file:resources/select_menu.png"));
        background.setFitHeight(height);
        background.setFitWidth(width);

        VBox layout = new VBox(35);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(width, height);

        Text title = new Text("SELECT LEVEL");
        title.setFill(Color.WHITE);
        title.setFont(Font.loadFont("file:resources/Creepster-Regular.ttf", 56));

        HBox levelButtons = new HBox(30);
        levelButtons.setAlignment(Pos.CENTER);
        levelButtons.getChildren().addAll(
            createLevelButton(1, "LEVEL 1", "level1.png", onLevelSelected),
            createLevelButton(2, "LEVEL 2", "level2.png", onLevelSelected),
            createLevelButton(3, "LEVEL 3", "level3.png", onLevelSelected)
        );

        layout.getChildren().addAll(title, levelButtons);
        root.getChildren().addAll(background, layout);
    }

    // Her level için renkli küçük harita önizlemeli bir seçim butonu oluşturur.
    private StackPane createLevelButton(int levelNumber, String text, String map, IntConsumer onLevelSelected) {
        StackPane button = new StackPane();
        Rectangle bg = new Rectangle(260, 180, Color.PURPLE);
        ImageView preview = new ImageView();
        Image img = new Image("file:resources/levelBackgrounds/" + map);
        preview.setImage(img);
        preview.setTranslateY(-15);
        preview.setFitWidth(210);
        preview.setFitHeight(120);
        preview.setPreserveRatio(false);

        Text label = new Text(text);
        label.setFill(Color.WHITE);
        label.setFont(Font.loadFont("file:resources/Creepster-Regular.ttf", 26));
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
