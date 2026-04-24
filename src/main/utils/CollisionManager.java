package main.utils;

import javafx.geometry.Bounds;

import main.entities.Entity;
import main.entities.Hunter;
import main.items.Token;

// Oyundaki çarpışma kontrollerini tek yerde toplar.
public class CollisionManager {
    // Hunter gövdesi bir düşmana değerse hasar alma kontrolü buradan yapılır.
    public boolean hunterTouchesEntity(Hunter hunter, Entity entity) {
        return hunter.getBounds().intersects(entity.getBounds());
    }

    // Hunter token'a değerse token toplanmış kabul edilir.
    public boolean hunterTouchesToken(Hunter hunter, Token token) {
        return hunter.getBounds().intersects(token.getBounds());
    }

    // Scanner üçgeni bir düşmanı kapsıyorsa düşman görünür olur ve vakumlanmaya başlar.
    public boolean scannerTouchesEntity(Hunter hunter, Entity entity) {
        if (!hunter.isScannerActive()) return false;

        // Düşmanın koordinatını hunter'ın yerel koordinatına çevirerek doğru kesişim hesabı yapılır.
        Bounds entityInHunterLocal = hunter.getView().sceneToLocal(entity.getBounds());
        return hunter.getScanner().getBoundsInParent().intersects(entityInHunterLocal);
    }

    // Health ve Range token etkileri hunter üzerinde uygulanır.
    public void applyTokenEffect(Token token, Hunter hunter) {
        if (token.getType() == Token.TokenType.HEALTH) {
            hunter.heal(GameConfig.getValue("health_token_increase"));
        } else if (token.getType() == Token.TokenType.RANGE) {
            hunter.increaseRange(GameConfig.getValue("vacuum_token_increase"));
        }
    }
}
