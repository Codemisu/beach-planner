package es.ulpgc.dacd.beachplanner.beachinfo.mapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BeachInfoMapper {

    public List<BeachInfoRecord> map(String json) {
        List<BeachInfoRecord> records = new ArrayList<>();

        JsonArray rootArray = JsonParser.parseString(json).getAsJsonArray();
        if (rootArray.isEmpty()) {
            return records;
        }

        JsonObject beachObject = rootArray.get(0).getAsJsonObject();

        String beachName = getString(beachObject, "nombre");
        String predictionTime = getString(beachObject, "elaborado");

        JsonObject prediccion = beachObject.getAsJsonObject("prediccion");
        JsonArray dias = prediccion.getAsJsonArray("dia");

        if (dias == null || dias.isEmpty()) {
            return records;
        }

        JsonObject firstDay = dias.get(0).getAsJsonObject();

        String skyState = getNestedString(firstDay, "estadoCielo", "descripcion1");
        String windState = getNestedString(firstDay, "viento", "descripcion1");
        String waveState = getNestedString(firstDay, "oleaje", "descripcion1");
        String maxTemperature = getNestedString(firstDay, "tMaxima", "valor1");

        String capturedAt = LocalDateTime.now().toString();

        BeachInfoRecord record = new BeachInfoRecord(
                beachName,
                predictionTime,
                skyState,
                windState,
                waveState,
                maxTemperature,
                capturedAt
        );

        records.add(record);
        return records;
    }

    private String getString(JsonObject object, String fieldName) {
        if (object == null || !object.has(fieldName) || object.get(fieldName).isJsonNull()) {
            return "unknown";
        }
        return object.get(fieldName).getAsString();
    }

    private String getNestedString(JsonObject parent, String objectName, String fieldName) {
        if (parent == null || !parent.has(objectName) || parent.get(objectName).isJsonNull()) {
            return "unknown";
        }

        JsonObject nestedObject = parent.getAsJsonObject(objectName);

        if (!nestedObject.has(fieldName) || nestedObject.get(fieldName).isJsonNull()) {
            return "unknown";
        }

        return nestedObject.get(fieldName).getAsString();
    }
}