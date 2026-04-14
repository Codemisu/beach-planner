package es.ulpgc.dacd.beachplanner.beachinfo.infrastructure;

import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SQLiteBeachInfoSerializer implements BeachInfoSerializer {

    private final BeachInfoDatabaseManager databaseManager;

    public SQLiteBeachInfoSerializer(BeachInfoDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void save(List<BeachInfoRecord> records) {
        databaseManager.initializeDatabase();

        try (Connection conn = databaseManager.connect()) {

            String sql = """
            INSERT INTO beach_info 
            (beach_name, prediction_time, sky, wind, wave, max_temp, captured_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);

            for (BeachInfoRecord record : records) {
                stmt.setString(1, record.getBeachName());
                stmt.setString(2, record.getPredictionTime());
                stmt.setString(3, record.getSkyState());
                stmt.setString(4, record.getWindState());
                stmt.setString(5, record.getWaveState());
                stmt.setString(6, record.getMaxTemperature());
                stmt.setString(7, record.getCapturedAt());

                stmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}