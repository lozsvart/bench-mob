package com.epam.bench.mob;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Bonus {
    STRIKE(2), SPARE(1);

    private final int bonusRollCount;
}
