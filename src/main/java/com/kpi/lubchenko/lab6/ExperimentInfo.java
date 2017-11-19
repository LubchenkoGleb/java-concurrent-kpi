package com.kpi.lubchenko.lab6;

import lombok.Data;

@Data
public class ExperimentInfo {

    private Integer queueLength;
    private Integer failers;
    private Integer aliveProducers;
    private Integer aliveConcumers;
}
