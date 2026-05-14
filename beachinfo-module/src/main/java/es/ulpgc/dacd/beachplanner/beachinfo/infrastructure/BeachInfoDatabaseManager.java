package es.ulpgc.dacd.beachplanner.beachinfo.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BeachInfoDatabaseManager {

    private static final String URL = "jdbc:sqlite:beachinfo.db";
    private static final String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS beach_info (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    beach_name TEXT,
                    prediction_time TEXT,
                    sky TEXT,
                    wind TEXT,
                    wave TEXT,
                    max_temp TEXT,
                    captured_at TEXT
                )
            """;
    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(CREATE_TABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection connect() throws Exception {
        return DriverManager.getConnection(URL);
    }
}