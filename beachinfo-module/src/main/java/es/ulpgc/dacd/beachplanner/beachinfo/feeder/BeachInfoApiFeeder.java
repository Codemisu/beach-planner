package es.ulpgc.dacd.beachplanner.beachinfo.feeder;

import es.ulpgc.dacd.beachplanner.beachinfo.client.BeachInfoApiClient;
import es.ulpgc.dacd.beachplanner.beachinfo.mapper.BeachInfoMapper;
import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;

import java.util.List;

public class BeachInfoApiFeeder implements BeachInfoFeeder {

    private final BeachInfoApiClient apiClient;
    private final BeachInfoMapper mapper;

    public BeachInfoApiFeeder(BeachInfoApiClient apiClient, BeachInfoMapper mapper) {
        this.apiClient = apiClient;
        this.mapper = mapper;
    }

    @Override
    public List<BeachInfoRecord> fetch() {
        String json = apiClient.fetchBeachInfoJson();
        return mapper.map(json);
    }
}