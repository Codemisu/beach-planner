package es.ulpgc.dacd.beachplanner.beachinfo.feeder;

import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;
import java.util.List;

public interface BeachInfoFeeder {
    List<BeachInfoRecord> fetch() throws Exception;
}