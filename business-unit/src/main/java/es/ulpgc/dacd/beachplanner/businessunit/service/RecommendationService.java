package es.ulpgc.dacd.beachplanner.businessunit.service;

import es.ulpgc.dacd.beachplanner.businessunit.model.BeachState;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {

    public String recommend(BeachState state) {

        if (state == null) {
            return "No hay datos disponibles para esta playa.";
        }

        List<String> uses = new ArrayList<>();

        if (isGoodForRelax(state)) {
            uses.add("relajarse");
        }

        if (isGoodForSurf(state)) {
            uses.add("surf");
        }

        if (isGoodForFriends(state)) {
            uses.add("ir con amigos");
        }

        if (uses.isEmpty()) {
            uses.add("no recomendada ahora mismo");
        }

        StringBuilder result = new StringBuilder();

        result.append("Apta para: ")
                .append(String.join(", ", uses))
                .append("\n");

        result.append("Nivel de surf: ")
                .append(getSurfLevel(state))
                .append("\n");

        if (isGoodForSurf(state)) {
            result.append("Equipo recomendado: ")
                    .append(getEquipment(state))
                    .append("\n");
        }

        result.append("Datos reales usados:\n");

        result.append("- Temperatura aire: ")
                .append(state.getTemperature())
                .append("ºC\n");

        if (state.getWaterTemperature() > 0) {
            result.append("- Temperatura agua: ")
                    .append(state.getWaterTemperature())
                    .append("ºC\n");
        }

        result.append("- Viento: ")
                .append(state.getWind())
                .append(" km/h\n");

        if (hasWaveData(state)) {
            result.append("- Oleaje: ")
                    .append(state.getWaveState())
                    .append("\n");
        }

        if (state.getSkyState() != null
                && !state.getSkyState().equalsIgnoreCase("no disponible")) {

            result.append("- Estado del cielo: ")
                    .append(state.getSkyState())
                    .append("\n");
        }

        if (state.getUvIndex() > 0) {
            result.append("- Índice UV: ")
                    .append(state.getUvIndex())
                    .append("\n");
        }

        if (state.getOccupancy() > 0) {
            result.append("- Ocupación: ")
                    .append(state.getOccupancy())
                    .append("%\n");
        }

        return result.toString();
    }

    private boolean isGoodForRelax(BeachState state) {
        return state.getTemperature() >= 18
                && state.getWind() < 18
                && !isStrongWaves(state)
                && state.getOccupancy() < 80;
    }

    private boolean isGoodForSurf(BeachState state) {
        return hasWaveData(state)
                && !isCalmSea(state)
                && state.getWind() <= 30;
    }

    private boolean isGoodForFriends(BeachState state) {
        return state.getTemperature() >= 17
                && state.getWind() <= 28
                && state.getOccupancy() < 90;
    }

    private String getSurfLevel(BeachState state) {

        if (!isGoodForSurf(state)) {
            return "No recomendable para surf";
        }

        String waveState = state.getWaveState().toLowerCase();

        if (waveState.contains("débil") || waveState.contains("flojo") || waveState.contains("marejadilla")) {
            return "Principiante";
        }

        if (waveState.contains("moderado") || waveState.contains("marejada")) {
            return "Intermedio";
        }

        if (waveState.contains("fuerte") || waveState.contains("gruesa") || state.getWind() > 25) {
            return "Avanzado";
        }

        return "Intermedio";
    }

    private String getEquipment(BeachState state) {

        if (!isGoodForSurf(state)) {
            return "No hace falta equipo de surf";
        }

        List<String> equipment = new ArrayList<>();
        equipment.add("tabla de surf");
        equipment.add("leash");

        if (state.getWaterTemperature() > 0 && state.getWaterTemperature() < 20) {
            equipment.add("neopreno");
        }

        if (state.getWind() > 25 || getSurfLevel(state).equals("Avanzado")) {
            equipment.add("casco recomendado");
        }

        return String.join(", ", equipment);
    }

    private boolean hasWaveData(BeachState state) {
        return state.getWaveState() != null
                && !state.getWaveState().equalsIgnoreCase("no disponible");
    }

    private boolean isCalmSea(BeachState state) {
        String waveState = state.getWaveState().toLowerCase();
        return waveState.contains("calma")
                || waveState.contains("tranquilo")
                || waveState.contains("sin oleaje");
    }

    private boolean isStrongWaves(BeachState state) {
        if (!hasWaveData(state)) {
            return false;
        }

        String waveState = state.getWaveState().toLowerCase();

        return waveState.contains("fuerte")
                || waveState.contains("gruesa")
                || waveState.contains("muy fuerte");
    }

    private String formatWaterTemperature(BeachState state) {
        if (state.getWaterTemperature() <= 0) {
            return "no disponible";
        }

        return state.getWaterTemperature() + "ºC";
    }

    private String formatUvIndex(BeachState state) {
        if (state.getUvIndex() <= 0) {
            return "no disponible";
        }

        return String.valueOf(state.getUvIndex());
    }

    private String formatOccupancy(BeachState state) {
        if (state.getOccupancy() <= 0) {
            return "no disponible";
        }

        return state.getOccupancy() + "%";
    }
}