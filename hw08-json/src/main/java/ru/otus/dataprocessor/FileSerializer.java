package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (FileWriter fileWriter = new FileWriter(fileName)){
            var gson = new Gson();
            gson.toJson(data, fileWriter);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }

    }
}