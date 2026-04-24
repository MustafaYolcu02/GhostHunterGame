package main.spawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.entities.Entity;
import main.entities.Ghost;
import main.entities.Ripper;
import main.entities.Wisp;
import main.levels.Level;

// Level bilgisine göre düşmanları playable area içinde rastgele oluşturan sınıf.
public class EntitySpawner {
    private final Random random;

    public EntitySpawner(Random random) {
        this.random = random;
    }

    // Config'teki Ghost/Ripper/Wisp sayılarına göre bütün düşmanları üretir.
    public List<Entity> spawnForLevel(Level level) {
        List<Entity> spawned = new ArrayList<>();
        for (int i = 0; i < level.getGhostCount(); i++) {
            spawned.add(new Ghost(randomX(level), randomY(level)));
        }
        for (int i = 0; i < level.getRipperCount(); i++) {
            spawned.add(new Ripper(randomX(level), randomY(level)));
        }
        for (int i = 0; i < level.getWispCount(); i++) {
            spawned.add(new Wisp(randomX(level), randomY(level)));
        }
        return spawned;
    }

    // Düşmanın X koordinatını playable area içinde kalacak şekilde rastgele seçer.
    private double randomX(Level level) {
        return level.getPlayableAreaX() + 30
            + random.nextDouble() * Math.max(1, level.getPlayableAreaWidth() - 60);
    }

    // Düşmanın Y koordinatını playable area içinde kalacak şekilde rastgele seçer.
    private double randomY(Level level) {
        return level.getPlayableAreaY() + 30
            + random.nextDouble() * Math.max(1, level.getPlayableAreaHeight() - 60);
    }
}
