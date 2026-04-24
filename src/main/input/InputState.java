package main.input;

import javafx.scene.Scene;

// Klavye durumunu Main'den ayırır; hareket ve scanner inputlarını tek yerde tutar.
public class InputState {
    private boolean wPressed;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;
    private boolean spacePressed;

    // Scene üzerine key pressed/released eventlerini bağlar.
    public void attachTo(Scene scene, Runnable cheatToggleAction) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W: wPressed = true; break;
                case A: aPressed = true; break;
                case S: sPressed = true; break;
                case D: dPressed = true; break;
                case SPACE: spacePressed = true; break;
                case C: cheatToggleAction.run(); break;
                default: break;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W: wPressed = false; break;
                case A: aPressed = false; break;
                case S: sPressed = false; break;
                case D: dPressed = false; break;
                case SPACE: spacePressed = false; break;
                default: break;
            }
        });
    }

    // Yeni level başlarken önceki tuş durumlarının taşınmasını engeller.
    public void reset() {
        wPressed = false;
        aPressed = false;
        sPressed = false;
        dPressed = false;
        spacePressed = false;
    }

    // A/D tuşlarına göre yatay hareket miktarını hesaplar.
    public double horizontalMovement(double speed) {
        double dx = 0;
        if (aPressed) dx -= speed;
        if (dPressed) dx += speed;
        return dx;
    }

    // W/S tuşlarına göre dikey hareket miktarını hesaplar.
    public double verticalMovement(double speed) {
        double dy = 0;
        if (wPressed) dy -= speed;
        if (sPressed) dy += speed;
        return dy;
    }

    // SPACE basılıysa scanner/vacuum aktif edilebilir.
    public boolean isSpacePressed() {
        return spacePressed;
    }
}
