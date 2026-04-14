package es.ulpgc.dacd.beachplanner.beachinfo.infrastructure;

import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;

import java.util.List;

public interface BeachInfoSerializer {
    void save(List<BeachInfoRecord> records) throws Exception;
}