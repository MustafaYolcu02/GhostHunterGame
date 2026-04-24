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

    public Wisp(double x, double y) {
        // Wisp yakalanınca 30 puan verir; daha büyük olduğu için daha yavaş hareket eder.
        super(x, y, 30, GameConfig.getDouble("wisp_min_speed"), GameConfig.getDouble("wisp_max_speed"), 0.003);

        // İç içe daireler Wisp'in parlama hissini verir.
        Circle outer = new Circle(0, 0, 24, Color.rgb(80, 230, 255, 0.28));
        Circle middle = new Circle(0, 0, 17, Color.rgb(120, 245, 255, 0.45));
        Circle inner = new Circle(0, 0, 9, normalColor);

        Rectangle r1 = new Rectangle(-3, -34, 6, 12);
        Rectangle r2 = new Rectangle(-3, 22, 6, 12);
        Rectangle r3 = new Rectangle(-34, -3, 12, 6);
        Rectangle r4 = new Rectangle(22, -3, 12, 6);
        // Etrafındaki küçük dikdörtgenler her frame döndürülerek animasyon yapılır.
        r1.setFill(Color.rgb(180, 255, 255, 0.75));
        r2.setFill(Color.rgb(180, 255, 255, 0.75));
        r3.setFill(Color.rgb(180, 255, 255, 0.75));
        r4.setFill(Color.rgb(180, 255, 255, 0.75));
        orbit.getChildren().addAll(r1, r2, r3, r4);

        view.getChildren().addAll(outer, middle, inner, orbit);
    }

    @Override
    protected void animate() {
        // Wisp hareket ederken çevresindeki parçalar sürekli döner.
        orbit.setRotate(orbit.getRotate() + 3);
    }

    @Override
    protected Color getNormalColor() {
        return normalColor;
    }
}
