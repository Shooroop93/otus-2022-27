package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        Gson gson = new Gson();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             Reader reader = new InputStreamReader(inputStream)) {
            Type collectionType = new TypeToken<ArrayList<Measurement>>() {}.getType();
            return gson.fromJson(reader, collectionType);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }
}