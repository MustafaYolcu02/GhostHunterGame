package main.entities;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.utils.GameConfig;

// Wisp en değerli düşmandır; büyük, parlayan ve dönen parçalı bir tasarıma sahiptir.
public class Wisp extends Entity {
    private final Color normalColor = Color.rgb(80, 230, 255, 0.65);
    private final Group orbit = new Group();
    private Circle body, eye1, eye2;
    private Rectangle r1, r2, r3, r4;

    public Wisp(double x, double y) {
        // Wisp yakalanınca 30 puan verir; daha büyük olduğu için daha yavaş hareket eder.
        super(x, y, 30, GameConfig.getDouble("wisp_min_speed"), GameConfig.getDouble("wisp_max_speed"), 0.003);

        // İç içe daireler Wisp'in parlama hissini verir.
   
        body = new Circle(0, 0, 18, Color.RED);

        eye1 = new Circle(-6, -4, 3);
        eye2 = new Circle(6, -4, 3);

        r1 = new Rectangle(-2, -33, 4, 12);
        r2 = new Rectangle(-2, 21, 4, 12);
        r3 = new Rectangle(-33, -2, 12, 4);
        r4 = new Rectangle(21, -2, 12, 4);
        // Etrafındaki küçük dikdörtgenler her frame döndürülerek animasyon yapılır.
        r1.setFill(Color.rgb(180, 255, 255, 0.75));
        r2.setFill(Color.rgb(180, 255, 255, 0.75));
        r3.setFill(Color.rgb(180, 255, 255, 0.75));
        r4.setFill(Color.rgb(180, 255, 255, 0.75));
        orbit.getChildren().addAll(r1, r2, r3, r4);

        view.getChildren().addAll(body, eye1, eye2, orbit);
    }

    @Override
    protected void animate() {
        // Wisp hareket ederken çevresindeki parçalar sürekli döner.
        orbit.setRotate(orbit.getRotate() + 3);
    }

    @Override
    protected void setNormal() {
        body.setFill(Color.RED);
        eye1.setFill(Color.YELLOW);
        eye2.setFill(Color.YELLOW);
        r1.setFill(Color.RED);
        r2.setFill(Color.RED);
        r3.setFill(Color.RED);
        r4.setFill(Color.RED);
    }

    @Override
    protected Color getNormalColor() {
        return normalColor;
    }
}
