package es.ulpgc.beachplanner.weather.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class WeatherDatabase {

    private static final String URL = "jdbc:sqlite:weather-module/weather.db"; ///creo weather.db

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS weather_records (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    beach_name TEXT NOT NULL,
                    forecast_time TEXT NOT NULL,
                    temperature REAL,
                    wind_speed REAL,
                    captured_at TEXT NOT NULL
                );
                """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }
}