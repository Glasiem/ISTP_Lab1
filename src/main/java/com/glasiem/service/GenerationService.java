package com.glasiem.service;

import com.glasiem.entity.AgencyEntity;
import com.glasiem.entity.GenerationEntity;
import com.glasiem.model.Agency;
import com.glasiem.model.Generation;
import com.glasiem.repository.AgencyRepo;
import com.glasiem.repository.GenerationRepo;
import com.glasiem.repository.MediaRepo;
import com.glasiem.repository.VTuberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerationService {

    @Autowired
    private GenerationRepo generationRepo;

    @Autowired
    private AgencyRepo agencyRepo;

    @Autowired
    private VTuberRepo vtuberRepo;

    @Autowired
    private MediaRepo mediaRepo;

    public GenerationRepo getGenerationRepo(){
        return generationRepo;
    }

    public AgencyRepo getAgencyRepo(){
        return agencyRepo;
    }

    public VTuberRepo getVtuberRepo() {
        return vtuberRepo;
    }

    public MediaRepo getMediaRepo() {
        return mediaRepo;
    }

    public String createGenerationCheck(Generation generation) {
        generation.setName(generation.getName().trim());
        generation.setName(generation.getInfo().trim());
        if (generation.getName().equals("") ||
                generation.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        if (generationRepo.findByName(generation.getName()) != null) {
            return "Неможливо створити ще одне покоління з такою назвою.";
        }
        if (generation.getAgency() == null) {
            return "Неправильний ідентифікатор агенства.";
        }
        return null;
    }

    public String editGenerationCheck(Generation generation) {
        generation.setName(generation.getName().trim());
        generation.setName(generation.getInfo().trim());
        if (generation.getName().equals("") ||
                generation.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        if (generation.getAgency() == null) {
            return "Неправильний ідентифікатор агенства.";
        }
        GenerationEntity temp = generationRepo.findById(generation.getId()).get();
        if (generation.getName().equalsIgnoreCase(temp.getName())) {
            if ((generation.getInfo().equals(temp.getInfo())) &&
                    (generation.getAgency() == temp.getAgency()) &&
                    (generation.getName().equals(temp.getName()))) {
                return "Ви нічого не змінили";
            }
            return null;
        }
        if (generationRepo.findByName(generation.getName()) != null) {
            return "Покоління з такою назвою вже є.";
        }
        return null;
    }
}
