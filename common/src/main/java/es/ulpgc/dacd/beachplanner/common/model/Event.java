package es.ulpgc.dacd.beachplanner.common.model;

public class Event {

    private String ts;
    private String ss;
    private Object payload;

    public Event(String ts, String ss, Object payload) {
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

    public Object getPayload() {
        return payload;
    }
}