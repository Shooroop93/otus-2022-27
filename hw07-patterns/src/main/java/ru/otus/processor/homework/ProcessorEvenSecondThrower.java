package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorEvenSecondThrower implements Processor {

    public ProcessorEvenSecondThrower(TimeService ts) {
        this.ts = ts;
    }

    private final TimeService ts;

    @Override
    public Message process(Message message) {
        LocalDateTime now = LocalDateTime.now();

        if (ts.getDate().getSecond() % 2 == 0) {
            throw new EvenSecondException(now);
        }
        return message;
    }
}
