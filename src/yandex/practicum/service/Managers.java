package yandex.practicum.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import yandex.practicum.adapter.DurationAdapter;
import yandex.practicum.adapter.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() throws IOException, InterruptedException {
        return new HttpTaskManager("localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter());
        return gsonBuilder.create();
    }
}
