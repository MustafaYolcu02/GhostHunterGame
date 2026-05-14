package main.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class RangeToken extends Token {
    public RangeToken(double x, double y) {
        super(x, y, TokenType.RANGE);
        // Mavi bir kare
        Circle body = new Circle(0, 0, 14, Color.YELLOW);
        Polygon vacuum = new Polygon(-10, 0, 9, 9, 9, -9);
        vacuum.setFill(Color.BLACK);
        
        view.getChildren().addAll(body, vacuum);
    }
}