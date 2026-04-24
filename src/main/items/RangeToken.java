package main.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RangeToken extends Token {
    public RangeToken(double x, double y) {
        super(x, y, TokenType.RANGE);
        // Mavi bir kare
        Rectangle body = new Rectangle(-10, -10, 20, 20);
        body.setFill(Color.DEEPSKYBLUE);
        view.getChildren().add(body);
    }
}