package com.epam.bench.mob.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BowlingService {

    private static final int FRAMES_IN_GAME = 10;

    private static final int PINS_AT_START = 10;

    int score;
    int[] multipliers = new int[21];
    int rollIndex = 0;

    List<Frame> frames = new ArrayList<>();

    public void pins(Integer roll) {
        Frame frame = getCurrentFrame();
        frame.addRoll(roll);
        
        score += roll * multipliers[rollIndex];
        if (frame.isStrike()) {
            increaseMultipliersForNextRoll(2);
        } else if (frame.isSpare()) {
            increaseMultipliersForNextRoll(1);
        } 

        if (frame.isFrameFull()) {
            frames.add(new Frame(frame));
        }
        rollIndex++;
    }

    public int getScore() {
        return score;
        /*return frames.stream()
            .mapToInt(frame -> frame.getScore())
            .sum();*/
    }

    public void reset() {
        for (int i = 0; i<21;i++) {
            this.multipliers[i] = 1;
        }
        rollIndex = 0;
        score = 0;
        frames = new ArrayList<>(List.of(new Frame(null)));
    }

    private Frame getCurrentFrame() {
        return frames.get(frames.size() - 1);
    }

    private void increaseMultipliersForNextRoll(int numOfRolls) {
        if (frames.size() < FRAMES_IN_GAME)
            for( int i = 1; i <= numOfRolls; ++i)
                multipliers[rollIndex + i] += 1;
    }

    class Frame {
        
        private List<Integer> rolls = new LinkedList<>();
        private Frame previous = null;

        public Frame(Frame previousFrame) {
            previous = previousFrame;
        }

        public void addRoll(int roll) {
            rolls.add(roll);
            if (previous != null) {
                previous.addBonusRoll(roll);
            }
        }

        public void addBonusRoll(int roll) {
            if (rolls.size() < 3 && (isStrike() || isSpare())) {
                if (previous != null) {
                    previous.addBonusRoll(roll);
                }
                rolls.add(roll);
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
