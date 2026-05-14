package main.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.utils.GameConfig;

// En basit düşman türü: düşük skor verir ve orta hızda hareket eder.
public class Ghost extends Entity {
    private final Color normalColor = Color.rgb(200, 200, 200, 0.8);
    private Circle head, eye1, eye2, pupil1, pupil2;
    private Rectangle body;



    public Ghost(double x, double y) {
        // Ghost yakalanınca 10 puan verir.
        super(x, y, 10, GameConfig.getDouble("ghost_min_speed"), GameConfig.getDouble("ghost_max_speed"), 0.005);
        head = new Circle(0, 0, 12, normalColor);

        body = new Rectangle(-12, 0, 24 , 12);
        body.setFill(normalColor);

        eye1 = new Circle(-4, -3, 3, Color.WHITE);
        eye2 = new Circle(4, -3, 3, Color.WHITE);

        pupil1 = new Circle(-4, -2, 1.25, Color.BLACK);
        pupil2 = new Circle(4, -2, 1.25, Color.BLACK);

        

        view.getChildren().addAll(head, body, eye1, eye2, pupil1, pupil2);

    }

    @Override
    protected void setNormal() {
        head.setFill(normalColor);
        body.setFill(normalColor);
        eye1.setFill(Color.WHITE);
        eye2.setFill(Color.WHITE);
        pupil1.setFill(Color.BLACK);
        pupil2.setFill(Color.BLACK);
    }


    @Override
    protected Color getNormalColor() {
        return normalColor;
    }
}
