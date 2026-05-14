package main.entities;

import javafx.scene.Group;

public abstract class GameObject {
    protected double x, y;
    protected Group view; // Nesnenin ekrandaki görselini tutacak kapsayıcı

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.view = new Group();
        updateViewPosition();
    }

    public Group getView() {
        return view;
    }

    // Her nesnenin kendine has bir güncellenme mantığı olacak (hareket vs.)
    public abstract void update();

    // Görseli x ve y koordinatlarına taşır
    protected void updateViewPosition() {
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    
    // Çarpışma algılaması için nesnenin ekrandaki sınırlarını verir
    public javafx.geometry.Bounds getBounds() {
        return view.getBoundsInParent();
    }
}