package main.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.utils.GameConfig;

// En basit düşman türü: düşük skor verir ve orta hızda hareket eder.
public class Ghost extends Entity {
    private final Color normalColor = Color.rgb(200, 200, 200, 0.8);

    public Ghost(double x, double y) {
        // Ghost yakalanınca 10 puan verir.
        super(x, y, 10, GameConfig.getDouble("ghost_min_speed"), GameConfig.getDouble("ghost_max_speed"), 0.005);
        Circle body = new Circle(0, 0, 12, normalColor);
        view.getChildren().add(body);
    }

    @Override
    protected Color getNormalColor() {
        return normalColor;
    }
}
