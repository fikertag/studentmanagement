package core;

import com.google.gson.*;
import java.lang.reflect.Type;

public class StudentDeserializer implements JsonDeserializer<Student> {

    @Override
    public Student deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("major")) {
            return context.deserialize(json, RegularStudent.class);
        } else if (jsonObject.has("homeCountry")) {
            return context.deserialize(json, ExchangeStudent.class);
        } else {
            throw new JsonParseException("Unknown student type.");
        }
    }
}
