package es.ulpgc.dacd.beachplanner.beachinfo;

import es.ulpgc.dacd.beachplanner.beachinfo.client.BeachInfoApiClient;
import es.ulpgc.dacd.beachplanner.beachinfo.controller.BeachInfoController;
import es.ulpgc.dacd.beachplanner.beachinfo.db.BeachInfoDatabaseManager;
import es.ulpgc.dacd.beachplanner.beachinfo.feeder.BeachInfoApiFeeder;
import es.ulpgc.dacd.beachplanner.beachinfo.feeder.BeachInfoFeeder;
import es.ulpgc.dacd.beachplanner.beachinfo.mapper.BeachInfoMapper;
import es.ulpgc.dacd.beachplanner.beachinfo.serializer.BeachInfoSerializer;
import es.ulpgc.dacd.beachplanner.beachinfo.serializer.SQLiteBeachInfoSerializer;

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