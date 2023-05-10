package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.homework.ProcessorEvenSecondThrower;
import ru.otus.processor.homework.ProcessorSwap11and12Fields;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    public static void main(String[] args) {

        var processors = List.of(new ProcessorEvenSecondThrower(LocalDateTime::now), new LoggerProcessor(new ProcessorSwap11and12Fields()));

        var complexProcessor = new ComplexProcessor(processors, Throwable::printStackTrace);

        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var ofm = new ObjectForMessage();
        ofm.setData(List.of("TestString"));

        long id = 1L;

        var message = new Message.Builder(id)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(ofm)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        message.getField13().setData(new ArrayList<>());
        result.getField13().setData(new ArrayList<>());

        result = complexProcessor.handle(result);
        result.getField13().setData(List.of("a111", "b222", "c333"));


        result = complexProcessor.handle(result);

        var message2 = new Message.Builder(id)
                .field1("11field1")
                .field2("11field2")
                .field3("11field3")
                .field6("11field6")
                .field10("11field10")
                .field11("11field11")
                .field12("11field12")
                .field13(null)
                .build();

        result = complexProcessor.handle(message2);

        var historyResult = historyListener.findMessageById(id);
        System.out.println("historyResult 1 = " + historyListener.findMessageById(id));

        historyResult = historyListener.findMessageById(id);
        System.out.println("historyResult 2 = " + historyResult);

        historyResult = historyListener.findMessageById(id);
        System.out.println("historyResult 3 = " + historyResult);

        historyResult = historyListener.findMessageById(id);
        System.out.println("historyResult 4 = " + historyResult);

        historyResult = historyListener.findMessageById(id);
        System.out.println("historyResult 5 = " + historyResult);

        complexProcessor.removeListener(historyListener);

    }
}