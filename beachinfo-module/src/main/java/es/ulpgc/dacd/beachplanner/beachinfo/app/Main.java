package es.ulpgc.dacd.beachplanner.beachinfo.app;

import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoApiClient;
import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoDatabaseManager;
import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoApiFeeder;
import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoFeeder;
import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoMapper;
import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoSerializer;
import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.SQLiteBeachInfoSerializer;

public class Main {
    public static void main(String[] args) throws Exception {
        BeachInfoApiClient apiClient = new BeachInfoApiClient();
        BeachInfoMapper mapper = new BeachInfoMapper();
        BeachInfoFeeder feeder = new BeachInfoApiFeeder(apiClient, mapper);

        BeachInfoDatabaseManager databaseManager = new BeachInfoDatabaseManager();
        BeachInfoSerializer serializer = new SQLiteBeachInfoSerializer(databaseManager);

        BeachInfoController controller = new BeachInfoController(feeder, serializer);
        controller.run();
    }
}