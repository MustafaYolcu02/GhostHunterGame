package main.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class HealthToken extends Token {
    public HealthToken(double x, double y) {
        super(x, y, TokenType.HEALTH);

        // Videodaki gibi sarı zemin üzerinde kırmızı artı şeklinde health token.
        Circle body = new Circle(0, 0, 14, Color.YELLOW);
        Rectangle vertical = new Rectangle(-3, -9, 6, 18);
        Rectangle horizontal = new Rectangle(-9, -3, 18, 6);
        vertical.setFill(Color.RED);
        horizontal.setFill(Color.RED);

        view.getChildren().addAll(body, vertical, horizontal);
    }
}
