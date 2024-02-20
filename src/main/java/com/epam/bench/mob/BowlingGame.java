package com.epam.bench.mob;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class BowlingGame {

    private static final int PINS_AT_START = 10;

    int score;
    boolean isFirstRoll;
    int firstRollScore;
    int[] multipliers = new int[21];
    int frameCount = 1;
    int rollIndex = 0;

    @PostMapping("/pins")
    public ResponseEntity<Void> pins(@RequestBody Map<String, Object> body) {
        int roll = (Integer) body.get("pins");
        score += roll * multipliers[rollIndex];
        if (isStrike(roll)) {
            increaseMultipliersForNextRoll(2);
            frameCount += 1;
        } else if (isSpare(roll)) {
            increaseMultipliersForNextRoll(1);
            frameCount += 1;
            isFirstRoll = !isFirstRoll;
        } else {
            if (isFirstRoll) {
                firstRollScore = roll;
            } else {
                frameCount += 1;
            }
            isFirstRoll = !isFirstRoll;
        }
        rollIndex++;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 

    @PostMapping("/reset")
    public ResponseEntity<Void> reset() {
        for (int i = 0; i<21;i++) {
            this.multipliers[i] = 1;
        }
        frameCount = 1;
        rollIndex = 0;
        score = 0;
        isFirstRoll = true;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/score")
    public String score() {
        return "{ \"score\":" + score + "}";
    }

    private boolean isStrike(int roll) {
        return isFirstRoll && roll == PINS_AT_START;
    }

    private boolean isSpare(int roll) {
        return firstRollScore + roll == PINS_AT_START && !isFirstRoll;
    }

    private void increaseMultipliersForNextRoll(int numOfRolls) {
        if (frameCount < 10)
            for( int i = 1; i <= numOfRolls; ++i)
                multipliers[rollIndex + i] += 1;
    }
}
