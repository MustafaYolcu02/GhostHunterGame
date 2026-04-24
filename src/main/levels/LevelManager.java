package main.levels;

import main.utils.GameConfig;

// Level oluşturma ve level geçişiyle ilgili kararları yöneten sınıf.
public class LevelManager {
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 3;

    // İstenen level numarasını güvenli aralıkta tutup config değerleriyle Level nesnesi oluşturur.
    public Level loadLevel(int requestedLevel) {
        int levelNumber = Math.max(MIN_LEVEL, Math.min(requestedLevel, MAX_LEVEL));
        return new Level(
            levelNumber,
            GameConfig.getValue(key(levelNumber, "playable_area_x")),
            GameConfig.getValue(key(levelNumber, "playable_area_y")),
            GameConfig.getValue(key(levelNumber, "playable_area_width")),
            GameConfig.getValue(key(levelNumber, "playable_area_height")),
            GameConfig.getValue(key(levelNumber, "time")),
            GameConfig.getValue(key(levelNumber, "ghosts")),
            GameConfig.getValue(key(levelNumber, "rippers")),
            GameConfig.getValue(key(levelNumber, "wisps")),
            backgroundColor(levelNumber)
        );
    }

    // Win panelinde NEXT LEVEL butonunun çıkıp çıkmayacağını belirlemek için kullanılır.
    public boolean hasNextLevel(Level level) {
        return level.getNumber() < MAX_LEVEL;
    }

    // Mevcut leveldan sonra hangi levelın başlayacağını verir.
    public int nextLevelNumber(Level level) {
        return Math.min(level.getNumber() + 1, MAX_LEVEL);
    }

    // Config anahtarlarını tek formatta üretir: level_2_ghosts gibi.
    private String key(int levelNumber, String suffix) {
        return "level_" + levelNumber + "_" + suffix;
    }

    // Her level için farklı tema rengini tek yerden verir.
    private String backgroundColor(int levelNumber) {
        if (levelNumber == 2) return "#07313a";
        if (levelNumber == 3) return "#3a1208";
        return "#2b003a";
    }
}
