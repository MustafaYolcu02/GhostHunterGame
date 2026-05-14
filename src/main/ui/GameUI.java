package main.ui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameUI {
    private Group view;

    // Ekrandaki sağlık, vacuum, skor ve süre elemanları.
    private Rectangle healthFill;
    private Rectangle vacuumFill;
    private Text scoreText;
    private Text timeText;

    private final double BAR_WIDTH = 40;
    private final double BAR_HEIGHT = 200;

    public GameUI(double windowWidth) {
        view = new Group();

        Font titleFont = Font.loadFont("file:resources/Creepster-Regular.ttf", 24);
        Font textFont = Font.loadFont("file:resources/Creepster-Regular.ttf", 20);

        double vacX = 50;
        double vacY = 80;

        Text vacLabel = new Text(vacX - 20, vacY - 20, "VACUUM:");
        vacLabel.setFont(titleFont);
        vacLabel.setFill(Color.WHITE);


        Rectangle vacOutline = new Rectangle(vacX, vacY, BAR_WIDTH, BAR_HEIGHT);
        vacOutline.setFill(Color.BLACK);
        vacOutline.setStroke(Color.BLACK);
        vacOutline.setStrokeWidth(15);
        vacOutline.setStrokeType(StrokeType.OUTSIDE);


        vacuumFill = new Rectangle(vacX, vacY, BAR_WIDTH, BAR_HEIGHT);
        vacuumFill.setStroke(Color.PURPLE);
        vacuumFill.setFill(Color.PURPLE);


        double healthX = windowWidth - 90;
        double healthY = 80;

        Text healthLabel = new Text(healthX - 20, healthY - 20, "HEALTH:");
        healthLabel.setFont(titleFont);
        healthLabel.setFill(Color.WHITE);

        Rectangle healthOutline = new Rectangle(healthX, healthY, BAR_WIDTH, BAR_HEIGHT);
        healthOutline.setFill(Color.BLACK);
        healthOutline.setStroke(Color.BLACK);
        healthOutline.setStrokeWidth(15);
        healthOutline.setStrokeType(StrokeType.OUTSIDE);

        healthFill = new Rectangle(healthX, healthY, BAR_WIDTH, BAR_HEIGHT);
        healthFill.setStroke(Color.RED);
        healthFill.setFill(Color.RED);

        scoreText = new Text("SCORE: 0");
        scoreText.setFont(titleFont);
        scoreText.setFill(Color.WHITE);
        scoreText.setX((windowWidth / 2) - 40);
        scoreText.setY(40);

        timeText = new Text("0:00");
        timeText.setFont(textFont);
        timeText.setFill(Color.WHITE);
        timeText.setX((windowWidth / 2) - 15);
        timeText.setY(70);
        timeText.setTextAlignment(TextAlignment.CENTER);

        view.getChildren().addAll(vacOutline, vacuumFill, vacLabel,
                                healthOutline, healthFill, healthLabel,
                                  scoreText, timeText);
    }

    public Group getView() {
        return view;
    }

    // Vacuum bar alttan üste doğru dolu kalacak şekilde güncellenir.
    public void updateVacuum(double current, double max) {
        double percentage = Math.max(0, Math.min(1, current / max));
        double newHeight = BAR_HEIGHT * percentage;
        vacuumFill.setHeight(newHeight);
        vacuumFill.setY(80);
    }

    // Sağlık barı alttan üste doğru dolu kalacak şekilde güncellenir.
    public void updateHealth(double current, double max) {
        double percentage = Math.max(0, Math.min(1, current / max));
        double newHeight = BAR_HEIGHT * percentage;
        healthFill.setHeight(newHeight);
        healthFill.setY(80);
    }

    public void updateScore(int score) {
        scoreText.setText("SCORE: " + score);
    }

    public void updateTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        timeText.setText(String.format("%d:%02d", minutes, seconds));
    }

    public void showResultPanel(boolean won, int finalScore,
            javafx.event.EventHandler<javafx.scene.input.MouseEvent> onMenuClick) {
        showResultPanel(won, finalScore, "BACK TO MENU", onMenuClick, null, null);
    }

    public void showResultPanel(
            boolean won,
            int finalScore,
            String primaryButtonText,
            javafx.event.EventHandler<javafx.scene.input.MouseEvent> onPrimaryClick,
            String secondaryButtonText,
            javafx.event.EventHandler<javafx.scene.input.MouseEvent> onSecondaryClick) {
        Rectangle overlay = new Rectangle(0, 0, 1400, 800);
        overlay.setFill(won ? Color.rgb(0, 0, 0, 0.7) : Color.WHITE);
            
        VBox panel = new VBox(18);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefSize(460, 330);
        panel.setLayoutX(470);
        panel.setLayoutY(235);
        panel.setStyle(won
            ? "-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 25;"
            : "-fx-background-color: #ffffff; -fx-background-radius: 25;");

        Text statusText = new Text(won ? "YOU WIN!" : "GAME OVER");
        statusText.setFont(Font.loadFont("file:resources/Creepster-Regular.ttf", 40));
        statusText.setFill(won ? Color.LIMEGREEN : Color.RED);

        Text subText = new Text(won ? "You've vacuumed all the ghosts!" : "Retry the level or return to menu.");
        subText.setFont(Font.loadFont("file:resources/Creepster-Regular.ttf", 20));
        subText.setFill(won ? Color.WHITE : Color.BLACK);

        Text scoreTotal = new Text("FINAL SCORE: " + finalScore);
        scoreTotal.setFont(Font.loadFont("file:resources/Creepster-Regular.ttf", 25));
        scoreTotal.setFill(won ? Color.WHITE : Color.BLACK);

        panel.getChildren().addAll(statusText, subText, scoreTotal, createPanelButton(primaryButtonText, onPrimaryClick, won));
        if (secondaryButtonText != null && onSecondaryClick != null) {
            panel.getChildren().add(createPanelButton(secondaryButtonText, onSecondaryClick, won));
        }

        view.getChildren().addAll(overlay, panel);
        view.toFront();
    }

    private StackPane createPanelButton(String text, javafx.event.EventHandler<javafx.scene.input.MouseEvent> onClick, boolean won) {
        StackPane button = new StackPane();
        Rectangle bg = new Rectangle(210, 48, won ? Color.PURPLE : Color.GRAY);
        Text label = new Text(text);
        label.setFill(Color.WHITE);
        label.setFont(Font.loadFont("file:resources/Creepster-Regular.ttf", 20));

        button.getChildren().addAll(bg, label);
        button.setOnMousePressed(e ->  {bg.setFill(won ? Color.WHITE : Color.RED);
            label.setFill(won ? Color.RED : Color.WHITE);
        });
        button.setOnMouseReleased(e -> {bg.setFill(won ? Color.PURPLE : Color.GRAY);
            label.setFill(Color.WHITE);
        });
        button.setOnMouseClicked(onClick);
        return button;
    }
}
