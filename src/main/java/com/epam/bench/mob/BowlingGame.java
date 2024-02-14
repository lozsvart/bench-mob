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
    int doubleCount;

    @PostMapping("/pins")
    public ResponseEntity<Void> pins(@RequestBody Map<String, Object> body) {
        int roll = (Integer) body.get("pins");
        score += roll;
        if (doubleCount > 0) {
            score += roll;
        }
        doubleCount = Math.max(doubleCount-1, 0);
        if (isStrike(roll)) {
            doubleCount = 2;
        } else if (isSpare(roll)) {
            doubleCount = 1;
            isFirstRoll = !isFirstRoll;
        } else {
            if (isFirstRoll) {
                firstRollScore = roll;
            }
        isFirstRoll = !isFirstRoll;
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 

    @PostMapping("/reset")
    public ResponseEntity<Void> reset() {
        score = 0;
        isFirstRoll = true;
        doubleCount = 0;
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

}
