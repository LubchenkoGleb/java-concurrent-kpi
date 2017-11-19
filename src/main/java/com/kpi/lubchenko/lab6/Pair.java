package com.kpi.lubchenko.lab6;

import lombok.Data;

@Data
public class Pair {
    private Double key;
    private Double value;

    public Pair(Double key, Double value) {
        this.key = key;
        this.value = value;
    }
}
