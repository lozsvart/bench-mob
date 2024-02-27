package com.epam.bench.mob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.epam.bench.mob.service.BowlingService;

@RestController
public class BowlingGame {

    @Autowired
    private BowlingService bowlingService;

    @PostMapping("/pins")
    public ResponseEntity<Void> pins(@RequestBody Map<String, Object> body) {
        int roll = (Integer) body.get("pins");
        bowlingService.pins(roll);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 

    @PostMapping("/reset")
    public ResponseEntity<Void> reset() {
        bowlingService.reset();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/score")
    public String score() {
        return "{ \"score\":" + bowlingService.getScore() + "}";
    }
}
