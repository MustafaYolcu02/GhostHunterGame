package main.items;

import main.entities.GameObject;

public abstract class Token extends GameObject {
    public enum TokenType { HEALTH, RANGE, EYE }
    private TokenType type;

    public Token(double x, double y, TokenType type) {
        super(x, y);
        this.type = type;
    }

    public TokenType getType() { return type; }

    @Override
    public void update() {
        updateViewPosition();
    }
}