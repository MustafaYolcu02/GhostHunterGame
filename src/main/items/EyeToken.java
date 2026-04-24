package main.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class EyeToken extends Token {
    public EyeToken(double x, double y) {
        super(x, y, TokenType.EYE);
        // Sarı bir elmas şekli (Gözü andırması için)
        Polygon body = new Polygon(0, -15, 10, 0, 0, 15, -10, 0);
        body.setFill(Color.YELLOW);
        view.getChildren().add(body);
    }
} 