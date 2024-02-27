package com.epam.bench.mob.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BowlingService {

    private static final int PINS_AT_START = 10;

    int score;
    boolean isFirstRoll;
    int firstRollScore;
    int[] multipliers = new int[21];
    int frameCount = 1;
    int rollIndex = 0;

    List<Frame> frames = new ArrayList<>();

    public void pins(Integer roll) {
        Frame frame = getCurrentFrame();
        frame.addRoll(roll);
        
        score += roll * multipliers[rollIndex];
        if (frame.isStrike()) {
            increaseMultipliersForNextRoll(2);
            frameCount += 1;
             frames.add(new Frame());
        } else if (frame.isSpare()) {
            increaseMultipliersForNextRoll(1);
            frameCount += 1;
            frames.add(new Frame());
            isFirstRoll = !isFirstRoll;
        } else {
            if (isFirstRoll) {
                firstRollScore = roll;
            } else {
                frameCount += 1;
                frames.add(new Frame());
            }
            isFirstRoll = !isFirstRoll;
        }
        rollIndex++;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        for (int i = 0; i<21;i++) {
            this.multipliers[i] = 1;
        }
        frameCount = 1;
        rollIndex = 0;
        score = 0;
        isFirstRoll = true;
        frames = List.of(new Frame());
    }

    private Frame getCurrentFrame() {
        return frames.get(frames.size() - 1);
    }

    private void increaseMultipliersForNextRoll(int numOfRolls) {
        if (frameCount < 10)
            for( int i = 1; i <= numOfRolls; ++i)
                multipliers[rollIndex + i] += 1;
    }

    class Frame {
        
        private List<Integer> rolls = new LinkedList<>();

        public void addRoll(int roll) {
            rolls.add(roll);
        }

        public int getScore() {
            return rolls.stream().mapToInt(i -> i).sum();
        }

        public boolean isStrike() {
            return rolls.size() >= 1 && rolls.get(0) == 10;
        }

        public boolean isSpare() {
            return rolls.size() >= 2 && (rolls.get(0) + rolls.get(1)) == 10;
        }

    }
}
