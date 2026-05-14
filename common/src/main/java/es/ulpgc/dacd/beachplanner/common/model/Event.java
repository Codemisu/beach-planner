package es.ulpgc.dacd.beachplanner.common.model;

public record Event(
        String ts,
        String ss,
        Object payload
) {

}