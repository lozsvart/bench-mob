
package com.epam.bench.mob.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BowlingService {

    private static final int FRAMES_IN_GAME = 10;

    private static final int PINS_AT_START = 10;

    private List<Frame> frames = new ArrayList<>();

    public void pins(Integer roll) {
        Frame frame = getCurrentFrame();
        frame.addRoll(roll);
        
        if (frames.size() < FRAMES_IN_GAME && frame.isFrameFull()) {
            frames.add(new Frame(frame));
        }
    }

    public int getScore() {
        return frames.stream()
            .mapToInt(Frame::getScore)
            .sum();
    }

    public void reset() {
        frames = new ArrayList<>(List.of(new Frame(null)));
    }

    private Frame getCurrentFrame() {
        return frames.get(frames.size() - 1);
    }

    class Frame {
        
        private List<Integer> rolls = new LinkedList<>();
        private Frame previous = null;

        public Frame(Frame previousFrame) {
            previous = previousFrame;
        }

        public void addRoll(int roll) {
            rolls.add(roll);
            addBonusRollToPrevious(roll);
        }

        public void addBonusRoll(int roll) {
            if (rolls.size() < 3 && (isStrike() || isSpare())) {
                rolls.add(roll);
            }
            addBonusRollToPrevious(roll);
        }

        private void addBonusRollToPrevious(int roll) {
            if (previous != null) {
                previous.addBonusRoll(roll);
            }
        }

        public int getScore() {
            return rolls.stream().mapToInt(i -> i).sum();
        }

        public boolean isStrike() {
            return rolls.size() >= 1 && rolls.get(0) == PINS_AT_START;
        }

        public boolean isSpare() {
            return rolls.size() >= 2 && (rolls.get(0) + rolls.get(1)) == PINS_AT_START;
        }

        public boolean isFrameFull() {
            return isStrike() || rolls.size() > 1; 
        }

    }
}
