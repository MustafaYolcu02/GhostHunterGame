package main.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import main.utils.GameConfig;

// Ripper daha hızlı ve daha değerli düşmandır; dikenli geometrik şekillerle çizilir.
public class Ripper extends Entity {
    private final Color normalColor = Color.rgb(130, 40, 190, 0.9);

    public Ripper(double x, double y) {
        // Ripper yakalanınca 20 puan verir ve Ghost'tan daha hızlı hareket eder.
        super(x, y, 20, GameConfig.getDouble("ripper_min_speed"), GameConfig.getDouble("ripper_max_speed"), 0.004);

        // Dikenli dış gövde, Ripper'ın agresif görünmesini sağlar.
        Polygon spikes = new Polygon(
            0.0, -22.0, 7.0, -10.0, 20.0, -14.0, 13.0, 0.0,
            20.0, 14.0, 7.0, 10.0, 0.0, 22.0, -7.0, 10.0,
            -20.0, 14.0, -13.0, 0.0, -20.0, -14.0, -7.0, -10.0
        );
        spikes.setFill(normalColor);

        // Ortadaki daire düşmanın ana gövdesidir.
        Circle core = new Circle(0, 0, 11, Color.rgb(90, 0, 120, 0.95));
        view.getChildren().addAll(spikes, core);
    }

    @Override
    protected Color getNormalColor() {
        return normalColor;
    }
}
