package com.glasiem.service;

import com.glasiem.entity.AgencyEntity;
import com.glasiem.model.Agency;
import com.glasiem.model.Generation;
import com.glasiem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepo agencyRepo;

    @Autowired
    private GenerationRepo generationRepo;

    @Autowired
    private VTuberRepo vtuberRepo;

    @Autowired
    private MediaRepo mediaRepo;

    @Autowired
    private ManagerRepo managerRepo;

    public AgencyRepo getAgencyRepo() {
        return agencyRepo;
    }

    public GenerationRepo getGenerationRepo() {
        return generationRepo;
    }

    public VTuberRepo getVtuberRepo() {
        return vtuberRepo;
    }

    public MediaRepo getMediaRepo() {
        return mediaRepo;
    }

    public ManagerRepo getManagerRepo() {
        return managerRepo;
    }

    public String createAgencyCheck(Agency agency) {
        agency.setName(agency.getName().trim());
        agency.setName(agency.getInfo().trim());
        if (agency.getName().equals("") ||
                agency.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        if (agencyRepo.findByName(agency.getName()) != null) {
            return "Неможливо створити ще одне агенство з такою назвою.";
        }
        return null;
    }

    public String editAgencyCheck(Agency agency) {
        agency.setName(agency.getName().trim());
        agency.setName(agency.getInfo().trim());
        if (agency.getName().equals("") ||
                agency.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        AgencyEntity temp = agencyRepo.findById(agency.getId()).get();
        if (agency.getName().equalsIgnoreCase(temp.getName())) {
            if (agency.getInfo().equals(temp.getInfo()) &&
                    (agency.getName().equals(temp.getName()))) {
                return "Ви нічого не змінили";
            }
            return null;
        }
        if (agencyRepo.findByName(agency.getName()) != null){
            return "Агенство з такою назвою вже є.";
        }
        return null;
    }
}
