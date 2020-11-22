package io.may4th.minorplanet.entity;

public class GameEntity {

    public static class Name {
        public static final String PLAYER = "player";
        public static final String HUD = "hud";
    }

    public enum Type {
        PLAYER, WALL;
    }
}
