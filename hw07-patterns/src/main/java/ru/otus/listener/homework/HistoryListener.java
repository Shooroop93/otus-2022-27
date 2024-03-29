package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> messageMap;

    public HistoryListener() {
        this.messageMap = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        Message messageDeque = messageMap.put(msg.getId(), msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageMap.get(id));
    }
}
