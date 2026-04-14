package es.ulpgc.beachplanner.weather.repository;

import es.ulpgc.beachplanner.weather.db.WeatherDatabase;
import es.ulpgc.beachplanner.weather.domain.WeatherRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQLiteWeatherRepository implements WeatherRepository {

    private final WeatherDatabase database;

    public SQLiteWeatherRepository() {
        this.database = new WeatherDatabase();
        this.database.initialize();
    }

    @Override
    public void saveAll(List<WeatherRecord> records) {
        String sql = """
                INSERT INTO weather_records (
                    beach_name,
                    forecast_time,
                    temperature,
                    wind_speed,
                    captured_at
                ) VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = database.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (WeatherRecord record : records) {
                ///inserta en los ? de values arriba cada cosa que queremos añadir
                statement.setString(1, record.getBeachName()); ///mete el nombre de la playa en el primer ?
                statement.setString(2, record.getForecastTime());
                statement.setDouble(3, record.getTemperature());
                statement.setDouble(4, record.getWindSpeed());
                statement.setString(5, record.getCapturedAt());
                statement.addBatch(); ///no guarda en base de datos aun, le dice que lo guarde luego
            }

            statement.executeBatch(); ///aqui se insertan todos los registros de golpe
            System.out.println("Saved " + records.size() + " records into SQLite");

        } catch (SQLException e) {
            throw new RuntimeException("Error saving weather records", e);
        }
    }
}