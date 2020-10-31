package io.may4th.minorplanet.entity;

public class GameEntity {

    public static class Name {
        public static final String BACKGROUND = "background";
        public static final String PLAYER = "player";
        public static final String ASTEROID = "asteroid";
        public static final String BULLET = "bullet";
        public static final String EXPLOSION = "explosion";
        public static final String SCORE_TEXT = "scoreText";
    }

    public enum Type {
        PLAYER, BULLET, ASTEROID;
    }
}
