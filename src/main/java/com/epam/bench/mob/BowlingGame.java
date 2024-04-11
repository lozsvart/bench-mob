package com.epam.bench.mob;

import java.util.LinkedList;
import java.util.List;

public class BowlingGame {

    List<Frame> frames = new LinkedList<>();
    private int score;


    public void roll(int roll) {
        score += roll;

    }

    public int getScore() {
       /* return frames.stream()
                .mapToInt(Frame::getScore)
                .sum();*/
        return score;
    }
}
