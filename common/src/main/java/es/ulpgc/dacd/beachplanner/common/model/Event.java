package es.ulpgc.dacd.beachplanner.common.model;

import com.google.gson.JsonObject;

public class Event {
    private String ts;
    private String ss;
    private JsonObject payload;

    public Event(String ts, String ss, JsonObject payload) {
        this.ts = ts;
        this.ss = ss;
        this.payload = payload;
    }

    public String getTs() {
        return ts;
    }

    public String getSs() {
        return ss;
    }

    public JsonObject getPayload() {
        return payload;
    }
}