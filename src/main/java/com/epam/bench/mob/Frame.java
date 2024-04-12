package com.epam.bench.mob;

import lombok.Data;

@Data
public class Frame {

    private static final int MAX_PINS = 10;

    private Frame previousFrame;
    private Bonus bonus;
    private int order;
    private int rollNumber;
    private int pins;

    private Frame(Frame previousFrame) {
        if (previousFrame != null) {
            this.order = previousFrame.order + 1;
        } else {
            this.order = 0;
        }

        this.rollNumber = 0;
        this.pins = MAX_PINS;
        this.previousFrame = previousFrame;
    }

    public static Frame create(Frame previousFrame) {
        return new Frame(previousFrame);
    }

    public void roll(int value) {
        if ((rollNumber >= 2 && bonus == null) || rollNumber >= 3) {
            return;
        }
        pins -= value;
        if (rollNumber == 1 && pins == 0) {
            bonus = Bonus.SPARE;
        }
        rollNumber++;
        if (previousFrame != null) {
            previousFrame.roll(value);
        }
    }

    public int getScore() {
        return MAX_PINS - pins;
    }

}
