package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import main.entities.Entity;
import main.entities.Hunter;
import main.input.InputState;
import main.items.Token;
import main.levels.Level;
import main.levels.LevelManager;
import main.spawn.EntitySpawner;
import main.spawn.TokenSpawner;
import main.ui.GameUI;
import main.ui.LevelSelectMenu;
import main.ui.MainMenu;
import main.utils.CollisionManager;
import main.utils.GameConfig;

public class Main extends Application {

    private static final double WINDOW_WIDTH = 1400;
    private static final double WINDOW_HEIGHT = 800;

    private Pane root;
    private Scene scene;
    private GameUI gameUI;
    private Rectangle playableAreaBorder;

    private boolean cheatMode;
    private boolean gameEnded;

    private int score = 0;
    private int timeRemaining;
    private long lastTimerUpdate = 0;
    private long lastTokenSpawn = 0;
    private boolean eyeEffectActive = false;
    private long eyeEffectEndTime = 0;

    private Level currentLevel;
    private Hunter hunter;
    private final List<Entity> entities = new ArrayList<>();
    private final List<Token> tokens = new ArrayList<>();

    private final Random random = new Random();
    private final CollisionManager collisionManager = new CollisionManager();
    private final LevelManager levelManager = new LevelManager();
    private final EntitySpawner entitySpawner = new EntitySpawner(random);
    private final TokenSpawner tokenSpawner = new TokenSpawner(random);
    private final InputState inputState = new InputState();

    private AnimationTimer gameLoop;

    @Override
    public void init() {
        GameConfig.loadConfig("resources/config.txt");
    }

    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu(WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle("Ghost Hunter Inc.");
        primaryStage.setScene(mainMenu.getScene());
        primaryStage.show();

        mainMenu.getStartBtn().setOnMouseClicked(e -> startGame(primaryStage, 1));
        mainMenu.getSelectLevelBtn().setOnMouseClicked(e -> showLevelSelect(primaryStage));
        mainMenu.getExitBtn().setOnMouseClicked(e -> primaryStage.close());
    }

    private void showLevelSelect(Stage primaryStage) {
        LevelSelectMenu levelSelectMenu = new LevelSelectMenu(
            WINDOW_WIDTH,
            WINDOW_HEIGHT,
            levelNumber -> startGame(primaryStage, levelNumber)
        );
        primaryStage.setScene(levelSelectMenu.getScene());
    }

    private void startGame(Stage primaryStage, int levelNumber) {
        currentLevel = levelManager.loadLevel(levelNumber);
        resetGameState();

        root = new Pane();
        root.setStyle("-fx-background-color: " + currentLevel.getBackgroundColor() + ";");

        playableAreaBorder = new Rectangle(
            currentLevel.getPlayableAreaX(),
            currentLevel.getPlayableAreaY(),
            currentLevel.getPlayableAreaWidth(),
            currentLevel.getPlayableAreaHeight()
        );
        playableAreaBorder.setFill(Color.TRANSPARENT);
        playableAreaBorder.setStroke(Color.WHITE);
        playableAreaBorder.setStrokeWidth(2);
        playableAreaBorder.setVisible(false);
        root.getChildren().add(playableAreaBorder);

        hunter = new Hunter(
            currentLevel.getPlayableAreaX() + (currentLevel.getPlayableAreaWidth() / 2),
            currentLevel.getPlayableAreaY() + (currentLevel.getPlayableAreaHeight() / 2),
            GameConfig.getValue("maximum_health"),
            GameConfig.getValue("maximum_vacuum")
        );
        root.getChildren().add(hunter.getView());

        spawnEntities();

        timeRemaining = currentLevel.getTimeLimit();
        gameUI = new GameUI(WINDOW_WIDTH);
        gameUI.updateTime(timeRemaining);
        root.getChildren().add(gameUI.getView());

        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        inputState.attachTo(scene, this::toggleCheatMode);
        primaryStage.setScene(scene);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now, primaryStage);
            }
        };
        gameLoop.start();
    }

    private void resetGameState() {
        entities.clear();
        tokens.clear();
        score = 0;
        lastTimerUpdate = 0;
        lastTokenSpawn = 0;
        eyeEffectActive = false;
        cheatMode = false;
        gameEnded = false;
        inputState.reset();
    }

    private void spawnEntities() {
        for (Entity entity : entitySpawner.spawnForLevel(currentLevel)) {
            entities.add(entity);
            root.getChildren().add(entity.getView());
        }
    }

    private void toggleCheatMode() {
        cheatMode = !cheatMode;
        if (playableAreaBorder != null) {
            playableAreaBorder.setVisible(cheatMode);
        }
    }

    private void update(long now, Stage stage) {
        if (gameEnded) return;

        if (lastTimerUpdate == 0) {
            lastTimerUpdate = now;
        }

        updateTimer(now, stage);
        updateHunter();
        updateScanner();
        updateTokens(now);
        updateEntities(stage);
        updateUi();
    }

    private void updateTimer(long now, Stage stage) {
        if (now - lastTimerUpdate < 1_000_000_000) return;

        timeRemaining--;
        gameUI.updateTime(timeRemaining);
        lastTimerUpdate = now;

        if (timeRemaining <= 0) {
            endGame(false, stage);
        }
    }

    private void updateHunter() {
        double speed = GameConfig.getDouble("hunter_speed");
        double dx = inputState.horizontalMovement(speed);
        double dy = inputState.verticalMovement(speed);

        double nextX = hunter.getX() + dx;
        double nextY = hunter.getY() + dy;

        if (nextX > currentLevel.getPlayableAreaX()
                && nextX < currentLevel.getPlayableAreaX() + currentLevel.getPlayableAreaWidth()) {
            hunter.move(dx, 0);
        }
        if (nextY > currentLevel.getPlayableAreaY()
                && nextY < currentLevel.getPlayableAreaY() + currentLevel.getPlayableAreaHeight()) {
            hunter.move(0, dy);
        }
        hunter.update();
    }

    private void updateScanner() {
        double drainRate = GameConfig.getValue("vacuum_decrease") / 60.0;
        double rechargeRate = GameConfig.getValue("vacuum_increase") / 60.0;

        if (inputState.isSpacePressed() && hunter.getVacuumEnergy() > 0) {
            hunter.setScannerActive(true);
            hunter.drainVacuum(drainRate);
        } else {
            hunter.setScannerActive(false);
            hunter.rechargeVacuum(rechargeRate);
        }
    }

    private void updateTokens(long now) {
        if (lastTokenSpawn == 0) {
            lastTokenSpawn = now;
        }

        long spawnInterval = (long)GameConfig.getValue("token_spawn_seconds") * 1_000_000_000L;
        int maxTokens = GameConfig.getValue("max_tokens");
        if (now - lastTokenSpawn >= spawnInterval && tokens.size() < maxTokens) {
            Token token = tokenSpawner.createRandomToken(currentLevel);
            tokens.add(token);
            root.getChildren().add(token.getView());
            lastTokenSpawn = now;
        }

        List<Token> collectedTokens = new ArrayList<>();
        for (Token token : tokens) {
            if (collisionManager.hunterTouchesToken(hunter, token)) {
                collectedTokens.add(token);
                if (token.getType() == Token.TokenType.EYE) {
                    eyeEffectActive = true;
                    eyeEffectEndTime = now + (long)GameConfig.getValue("eye_token_duration") * 1_000_000_000L;
                } else {
                    collisionManager.applyTokenEffect(token, hunter);
                }
            }
        }

        for (Token token : collectedTokens) {
            root.getChildren().remove(token.getView());
            tokens.remove(token);
        }

        if (eyeEffectActive && now > eyeEffectEndTime) {
            eyeEffectActive = false;
        }
    }

    private void updateEntities(Stage stage) {
        List<Entity> defeatedEntities = new ArrayList<>();
        boolean takingDamage = false;

        for (Entity entity : entities) {
            entity.update(
                currentLevel.getPlayableAreaX(),
                currentLevel.getPlayableAreaY(),
                currentLevel.getPlayableAreaX() + currentLevel.getPlayableAreaWidth(),
                currentLevel.getPlayableAreaY() + currentLevel.getPlayableAreaHeight()
            );

            boolean scanned = collisionManager.scannerTouchesEntity(hunter, entity);
            entity.setRevealed(cheatMode || eyeEffectActive || scanned, scanned);

            if (scanned) {
                entity.shrink();
                if (entity.isDead()) {
                    defeatedEntities.add(entity);
                    score += entity.getScoreValue();
                }
            }

            if (collisionManager.hunterTouchesEntity(hunter, entity)) {
                takingDamage = true;
                hunter.damage(GameConfig.getValue("entity_damage") / 60.0);
            }
        }

        hunter.setTakingDamage(takingDamage);
        for (Entity entity : defeatedEntities) {
            root.getChildren().remove(entity.getView());
            entities.remove(entity);
        }

        if (hunter.getHealth() <= 0) {
            endGame(false, stage);
        } else if (entities.isEmpty()) {
            endGame(true, stage);
        }
    }

    private void updateUi() {
        gameUI.updateVacuum(hunter.getVacuumEnergy(), GameConfig.getValue("maximum_vacuum"));
        gameUI.updateHealth(hunter.getHealth(), GameConfig.getValue("maximum_health"));
        gameUI.updateScore(score);
    }

    private void endGame(boolean won, Stage stage) {
        if (gameEnded) return;
        gameEnded = true;

        if (gameLoop != null) {
            gameLoop.stop();
        }

        if (won && levelManager.hasNextLevel(currentLevel)) {
            gameUI.showResultPanel(
                true,
                score,
                "NEXT LEVEL",
                e -> startGame(stage, levelManager.nextLevelNumber(currentLevel)),
                "MAIN MENU",
                e -> start(stage)
            );
        } else if (!won) {
            gameUI.showResultPanel(
                false,
                score,
                "RETRY",
                e -> startGame(stage, currentLevel.getNumber()),
                "MAIN MENU",
                e -> start(stage)
            );
        } else {
            gameUI.showResultPanel(true, score, "MAIN MENU", e -> start(stage), null, null);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
