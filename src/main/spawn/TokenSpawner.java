package main.spawn;

import java.util.Random;

import main.items.EyeToken;
import main.items.HealthToken;
import main.items.RangeToken;
import main.items.Token;
import main.levels.Level;

// Token türü ve konumunu rastgele seçip yeni token oluşturan sınıf.
public class TokenSpawner {
    private final Random random;

    public TokenSpawner(Random random) {
        this.random = random;
    }

    // Health, Range veya Eye tokenlarından birini playable area içinde rastgele üretir.
    public Token createRandomToken(Level level) {
        double x = randomX(level);
        double y = randomY(level);
        int type = random.nextInt(3);

        if (type == 0) return new HealthToken(x, y);
        if (type == 1) return new RangeToken(x, y);
        return new EyeToken(x, y);
    }

    // Token'ın X koordinatını playable area içinde kalacak şekilde rastgele seçer.
    private double randomX(Level level) {
        return level.getPlayableAreaX() + 30
            + random.nextDouble() * Math.max(1, level.getPlayableAreaWidth() - 60);
    }

    // Token'ın Y koordinatını playable area içinde kalacak şekilde rastgele seçer.
    private double randomY(Level level) {
        return level.getPlayableAreaY() + 30
            + random.nextDouble() * Math.max(1, level.getPlayableAreaHeight() - 60);
    }
}
