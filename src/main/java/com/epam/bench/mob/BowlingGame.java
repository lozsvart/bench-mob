package com.epam.bench.mob;

import java.util.LinkedList;
import java.util.List;

public class BowlingGame {

    private final List<Frame> frames = new LinkedList<>();
    private int rollIndex = 0;

    public BowlingGame() {
        Frame previousFrame = null;
        for (int i = 0; i < 10; i++) {
            Frame frame = Frame.create(previousFrame);
            frames.add(frame);
            previousFrame = frame;
        }
    }

    public void roll(int roll) {
        getCurrentFrame().roll(roll);
        rollIndex++;
    }

    private Frame getCurrentFrame() {
        return frames.get(rollIndex / 2);
    }

    public int getScore() {
       return frames.stream()
                .mapToInt(Frame::getScore)
                .sum();
    }
}
