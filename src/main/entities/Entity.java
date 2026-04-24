package main.entities;

import java.util.Random;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

// Tüm düşman türlerinin ortak hareket, görünürlük, skor ve vakumlanma davranışlarını tutar.
public abstract class Entity extends GameObject {
    private static final Random RANDOM = new Random();

    protected double speedX;
    protected double speedY;
    protected boolean dead = false;
    protected double shrinkRate;
    protected int scoreValue;

    public Entity(double x, double y, int scoreValue, double minSpeed, double maxSpeed, double shrinkRate) {
        super(x, y);
        this.scoreValue = scoreValue;
        this.shrinkRate = shrinkRate;
        // Her düşman level başında rastgele bir yöne ve hıza sahip olur.
        this.speedX = randomSignedSpeed(minSpeed, maxSpeed);
        this.speedY = randomSignedSpeed(minSpeed, maxSpeed);
        // Düşmanlar başlangıçta görünmez; scanner veya EyeToken ile görünür hale gelir.
        view.setVisible(false);
    }

    // Hızın yönünü de rastgele seçerek düşmanın farklı yöne hareket etmesini sağlar.
    private double randomSignedSpeed(double minSpeed, double maxSpeed) {
        double speed = minSpeed + RANDOM.nextDouble() * (maxSpeed - minSpeed);
        return RANDOM.nextBoolean() ? speed : -speed;
    }

    // Düşmanı hareket ettirir; oynanabilir alan sınırına çarpınca ters yöne sektirir.
    public void update(double minX, double minY, double maxX, double maxY) {
        if (dead) return;

        x += speedX;
        y += speedY;

        if (x <= minX || x >= maxX) {
            speedX *= -1;
            x = Math.max(minX, Math.min(x, maxX));
        }
        if (y <= minY || y >= maxY) {
            speedY *= -1;
            y = Math.max(minY, Math.min(y, maxY));
        }

        animate();
        updateViewPosition();
    }

    // Scanner alanında kaldığı sürece düşmanı küçültür; yeterince küçülünce yakalanmış sayılır.
    public void shrink() {
        double nextScale = Math.max(0, view.getScaleX() - shrinkRate);
        view.setScaleX(nextScale);
        view.setScaleY(nextScale);
        if (nextScale <= 0.08) {
            dead = true;
        }
    }

    // Scanner veya EyeToken durumuna göre düşmanın görünür olup olmayacağını belirler.
    public void setRevealed(boolean revealed, boolean scanned) {
        view.setVisible(revealed);
        setScannedColor(scanned);
    }

    // Scanner içindeki düşmanlar şartnameye uygun şekilde gri/beyaz tona döner.
    protected void setScannedColor(boolean scanned) {
        Color color = scanned ? Color.rgb(225, 225, 225, 0.85) : getNormalColor();
        for (Node node : view.getChildren()) {
            if (node instanceof Shape shape) {
                shape.setFill(color);
            }
        }
    }

    // Wisp gibi özel düşmanlar kendi animasyonlarını bu metodu ezerek çalıştırır.
    protected void animate() {
    }

    // Her düşman türü kendi normal rengini döndürür; scanner bitince bu renge geri döner.
    protected abstract Color getNormalColor();

    public boolean isDead() {
        return dead;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    @Override
    public void update() {
    }
}
