package com.glasiem.service;

import com.glasiem.entity.AgencyEntity;
import com.glasiem.model.VTuber;
import com.glasiem.repository.GenerationRepo;
import com.glasiem.repository.VTuberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class GoogleChartsService {
    @Autowired
    private VTuberRepo vTuberRepo;

    @Autowired
    private GenerationRepo generationRepo;

    public Map<String, Integer> getVTuberAgencyMap(Iterable<AgencyEntity> agencies) {
        Map<String, Integer> agenciesMap = new TreeMap<>();
        for (AgencyEntity agency : agencies) {
            agenciesMap.put(agency.getName(), vTuberRepo.countAllByGenerationIn(generationRepo.findAllByAgency_Id(agency.getId())));
        }
        return agenciesMap;
    }
}
