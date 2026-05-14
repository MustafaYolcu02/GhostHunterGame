package main.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public class EyeToken extends Token {
    public EyeToken(double x, double y) {
        super(x, y, TokenType.EYE);
        // Sarı bir elmas şekli (Gözü andırması için)
        Circle body = new Circle(0, 0, 14, Color.YELLOW);
        Ellipse eyeball = new Ellipse(0, 0, 12, 7);
        eyeball.setFill(Color.WHITE);
        eyeball.setStroke(Color.BLACK);
        eyeball.setStrokeWidth(2.0);

        Circle eyecolor = new Circle(0, 0, 5);
        eyecolor.setFill( Color.web("#7B9CFB"));

        Circle pupil = new Circle(0, 0, 2.75);
        pupil.setFill(Color.BLACK);
        view.getChildren().addAll(body, eyeball, eyecolor, pupil);
    }
} 