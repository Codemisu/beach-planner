package es.ulpgc.dacd.beachplanner.beachinfo.serializer;

import es.ulpgc.dacd.beachplanner.beachinfo.db.BeachInfoDatabaseManager;
import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;

import java.util.List;

public class SQLiteBeachInfoSerializer implements BeachInfoSerializer {

    private final BeachInfoDatabaseManager databaseManager;

    public SQLiteBeachInfoSerializer(BeachInfoDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void save(List<BeachInfoRecord> records) {
        databaseManager.initializeDatabase();
        System.out.println("Saving " + records.size() + " beach info records...");
    }
}