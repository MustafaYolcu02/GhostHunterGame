package main.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Hunter extends GameObject {
    private int maxHealth;
    // Hasar frame bazlı geldiği için canı double tutuyoruz; UI tarafına yuvarlanmış değer gider.
    private double health;
    private double maxVacuumEnergy;
    private double vacuumEnergy;
    // RangeToken alındıkça scanner üçgeninin uzunluğu bu değer üzerinden büyür.
    private double scannerRange = 150;

    private Circle body;
    private Polygon scanner;

    public Hunter(double x, double y, int maxHealth, int maxVacuum) {
        super(x, y);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxVacuumEnergy = maxVacuum;
        this.vacuumEnergy = maxVacuum;

        // Scanner, SPACE basılıyken görünen yarı saydam üçgen alandır.
        scanner = new Polygon(0, 0, scannerRange, -75, scannerRange, 75);
        scanner.setFill(Color.rgb(255, 0, 0, 0.4));
        scanner.setVisible(false);

        // Hunter'ın çarpışmada dikkate alınan ana gövdesi.
        body = new Circle(0, 0, 15, Color.ORANGE);
        view.getChildren().addAll(scanner, body);
    }

    public Polygon getScanner() {
        return scanner;
    }

    // Vacuum enerjisi varsa scanner görünür olur; enerji bitince kapanır.
    public void setScannerActive(boolean active) {
        if (active && vacuumEnergy > 0) {
            scanner.setVisible(true);
        } else {
            scanner.setVisible(false);
        }
    }

    public boolean isScannerActive() {
        return scanner.isVisible();
    }

    // SPACE basılıyken vacuum enerjisini azaltır.
    public void drainVacuum(double amount) {
        vacuumEnergy -= amount;
        if (vacuumEnergy < 0) vacuumEnergy = 0;
    }

    // SPACE bırakıldığında vacuum enerjisini maksimum değere kadar doldurur.
    public void rechargeVacuum(double amount) {
        vacuumEnergy += amount;
        if (vacuumEnergy > maxVacuumEnergy) vacuumEnergy = maxVacuumEnergy;
    }

    public double getVacuumEnergy() {
        return vacuumEnergy;
    }

    public int getHealth() {
        return (int)Math.ceil(health);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    // Düşmanla temas sürdüğü sürece küçük miktarlarda can azaltılır.
    public void damage(double amount) {
        health = Math.max(0, health - amount);
    }

    // HealthToken toplandığında canı artırır, maksimum canı aşmasına izin vermez.
    public void heal(int amount) {
        this.health += amount;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    // RangeToken toplandığında scanner üçgenini config'teki miktar kadar büyütür.
    public void increaseRange(double amount) {
        scannerRange += amount;
        scanner.getPoints().clear();
        scanner.getPoints().addAll(
            0.0, 0.0,
            scannerRange, -(scannerRange / 2),
            scannerRange, scannerRange / 2
        );
    }

    // Düşmanla temas halinde hunter gövdesi kırmızıya döner.
    public void setTakingDamage(boolean takingDamage) {
        body.setFill(takingDamage ? Color.RED : Color.ORANGE);
    }

    @Override
    public void update() {
        updateViewPosition();
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public javafx.geometry.Bounds getBounds() {
        // Çarpışma hesaplarında scanner üçgenini yok sayıp sadece gövdeyi baz alır.
        return view.localToParent(body.getBoundsInParent());
    }
}
