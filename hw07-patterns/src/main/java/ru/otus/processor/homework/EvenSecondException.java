package ru.otus.processor.homework;

import java.time.LocalDateTime;

public class EvenSecondException extends RuntimeException{
    public EvenSecondException(LocalDateTime dateTime) {
        super("evenSecond exception: " + dateTime.toString());
    }
}
