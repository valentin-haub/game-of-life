package io.github.valentinhaub;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class GameTest {
    private Game game;

    @Test
    void testUnderpopulation() {
        game = new Game(3, 3);
        game.setCell(4); // eine lebende Zelle ohne lebende Nachbarn
        game.doStep();

        assertEquals(0, game.world[4], "Eine Zelle ohne lebende Nachbarn sollte sterben.");
    }

    @Test
    void testReproduction() {
        game = new Game(3, 3);
        game.setCell(1, 3, 5); // drei lebende Zellen um eine tote Zelle
        game.doStep();

        assertEquals(1, game.world[4], "Eine tote Zelle mit drei lebenden Nachbarn sollte geboren werden.");
    }

    @Test
    void testStepPossibleRecognizesStability() {
        game = new Game(3, 3);
        game.setCell(0, 1, 3, 4); // 2x2 Quadrat an lebenden Zellen

        assertFalse(game.stepPossible(), "In einem stabilen Zustand sollte kein weiterer Schritt möglich sein.");
    }

    @Test
    void testBlinkerOscillator() {
        game = new Game(5, 5);
        game.setCell(7, 12, 17); // drei lebende Zellen vertikal in Reihe

        int[] step1 = game.runAlgorithm(game.world);
        assertEquals(1, step1[11]);
        assertEquals(1, step1[12]);
        assertEquals(1, step1[13]);
        assertEquals(0, step1[7]);
        assertEquals(0, step1[17]);

        int[] step2 = game.runAlgorithm(step1);
        assertEquals(1, step2[7]);
        assertEquals(1, step2[12]);
        assertEquals(1, step2[17]);
        assertEquals(0, step2[11]);
        assertEquals(0, step2[13]);

        assertArrayEquals(game.world, step2);
        assertArrayEquals(step1, game.runAlgorithm(step2));
    }

    @Test
    void testPreviewMethod(){
        Random rand = new Random();

        game = new Game(rand.nextInt(2,51), rand.nextInt(2,51));
        for (int i = 0; i < game.world.length; i++)
            game.setCell(rand.nextBoolean(), i);

        int steps = rand.nextInt(1,21);
        int[] startWorld = game.world.clone();

        int[] preview = game.preview(steps);
        assertArrayEquals(startWorld, game.world, "Die Methode 'preview()' sollte die Welt nicht verändern.");

        game.doStep(steps);
        assertArrayEquals(preview, game.world, "Die Vorschau stimmt nicht mit den tatsächlichen Schritten überein.");
    }
}