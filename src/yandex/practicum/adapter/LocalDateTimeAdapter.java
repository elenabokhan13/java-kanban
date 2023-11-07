package yandex.practicum.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static yandex.practicum.tasks.Task.FORMATTER;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(FORMATTER.format(localDateTime));
    }

    @Override
    public LocalDateTime deserialize(final JsonElement json, final Type typeOfT,
                                     final JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), FORMATTER);
    }
}
