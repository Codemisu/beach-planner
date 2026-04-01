package es.ulpgc.dacd.beachplanner.beachinfo.serializer;

import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;
import java.util.List;

public interface BeachInfoSerializer {
    void save(List<BeachInfoRecord> records) throws Exception;
}