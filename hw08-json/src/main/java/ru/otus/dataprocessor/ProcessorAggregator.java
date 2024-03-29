package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data.stream()
                .collect(
                        Collectors.groupingBy(
                                Measurement::getName,
                                () -> new TreeMap<>(String::compareTo),
                                Collectors.summingDouble(
                                        Measurement::getValue)
                        )
                );
    }
}