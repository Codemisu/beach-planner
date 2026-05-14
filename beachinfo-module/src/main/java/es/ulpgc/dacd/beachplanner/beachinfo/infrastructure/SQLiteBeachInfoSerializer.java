package es.ulpgc.dacd.beachplanner.beachinfo.infrastructure;

import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SQLiteBeachInfoSerializer implements BeachInfoSerializer {

    private final BeachInfoDatabaseManager databaseManager;
    private static final String INSERT_RECORD = """
            INSERT INTO beach_info 
            (beach_name, prediction_time, sky, wind, wave, max_temp, captured_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
    public SQLiteBeachInfoSerializer(BeachInfoDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void save(List<BeachInfoRecord> records) {
        databaseManager.initializeDatabase();

        try (Connection conn = databaseManager.connect()) {

            PreparedStatement stmt = conn.prepareStatement(INSERT_RECORD);

            for (BeachInfoRecord record : records) {
                stmt.setString(1, record.beachName());
                stmt.setString(2, record.predictionTime());
                stmt.setString(3, record.skyState());
                stmt.setString(4, record.windState());
                stmt.setString(5, record.waveState());
                stmt.setString(6, record.maxTemperature());
                stmt.setString(7, record.capturedAt());

                stmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}