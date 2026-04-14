package es.ulpgc.dacd.beachplanner.beachinfo.app;

import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoFeeder;
import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;
import es.ulpgc.dacd.beachplanner.beachinfo.infrastructure.BeachInfoSerializer;

import java.util.List;

public class BeachInfoController {

    private final BeachInfoFeeder feeder;
    private final BeachInfoSerializer serializer;

    public BeachInfoController(BeachInfoFeeder feeder, BeachInfoSerializer serializer) {
        this.feeder = feeder;
        this.serializer = serializer;
    }

    public void run() throws Exception {
        List<BeachInfoRecord> records = feeder.fetch();
        serializer.save(records);
        System.out.println("BeachInfo module executed successfully.");
    }
}