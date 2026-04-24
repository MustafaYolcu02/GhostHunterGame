package main.levels;

// Bir level'ın config'ten gelen bütün sabit bilgilerini tek nesnede tutar.
public class Level {
    private final int number;
    private final double playableAreaX;
    private final double playableAreaY;
    private final double playableAreaWidth;
    private final double playableAreaHeight;
    private final int timeLimit;
    private final int ghostCount;
    private final int ripperCount;
    private final int wispCount;
    private final String backgroundColor;

    // LevelManager config.txt dosyasını okuyup bu constructor ile Level nesnesi oluşturur.
    public Level(
            int number,
            double playableAreaX,
            double playableAreaY,
            double playableAreaWidth,
            double playableAreaHeight,
            int timeLimit,
            int ghostCount,
            int ripperCount,
            int wispCount,
            String backgroundColor) {
        this.number = number;
        this.playableAreaX = playableAreaX;
        this.playableAreaY = playableAreaY;
        this.playableAreaWidth = playableAreaWidth;
        this.playableAreaHeight = playableAreaHeight;
        this.timeLimit = timeLimit;
        this.ghostCount = ghostCount;
        this.ripperCount = ripperCount;
        this.wispCount = wispCount;
        this.backgroundColor = backgroundColor;
    }

    public int getNumber() {
        return number;
    }

    // Playable area değerleri hunter, düşman ve tokenların sınır içinde kalması için kullanılır.
    public double getPlayableAreaX() {
        return playableAreaX;
    }

    public double getPlayableAreaY() {
        return playableAreaY;
    }

    public double getPlayableAreaWidth() {
        return playableAreaWidth;
    }

    public double getPlayableAreaHeight() {
        return playableAreaHeight;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    // Düşman sayıları EntitySpawner tarafından spawn sırasında kullanılır.
    public int getGhostCount() {
        return ghostCount;
    }

    public int getRipperCount() {
        return ripperCount;
    }

    public int getWispCount() {
        return wispCount;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
}
