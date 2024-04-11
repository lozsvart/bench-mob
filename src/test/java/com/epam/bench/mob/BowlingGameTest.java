package com.epam.bench.mob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    private BowlingGame game;

    @BeforeEach
    void setUp() {
        game = new BowlingGame();
    }

    @Test
    public void gutterGameShouldScoreZero() {
        rollMany(game, 0, 20);

        assertEquals(0, game.getScore());
    }

    @Test
    public void allOnesShouldScore20() {
        rollMany(game, 1, 20);

        assertEquals(20, game.getScore());
    }

    private static void rollMany(BowlingGame game, int rollValue, int times) {
        for (int i = 0; i < times; i++) {
            game.roll(rollValue);
        }
    }

}
