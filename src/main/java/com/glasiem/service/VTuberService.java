package com.glasiem.service;

import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.VTuberEntity;
import com.glasiem.model.Generation;
import com.glasiem.model.VTuber;
import com.glasiem.repository.GenerationRepo;
import com.glasiem.repository.MediaRepo;
import com.glasiem.repository.VTuberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VTuberService {
    @Autowired
    private VTuberRepo vtuberRepo;

    @Autowired
    private GenerationRepo generationRepo;

    @Autowired
    private MediaRepo mediaRepo;

    public VTuberRepo getVTuberRepo(){
        return vtuberRepo;
    }

    public GenerationRepo getGenerationRepo(){
        return generationRepo;
    }

    public MediaRepo getMediaRepo() {
        return mediaRepo;
    }

    public String createVTuberCheck(VTuber vtuber) {
        vtuber.setName(vtuber.getName().trim());
        vtuber.setName(vtuber.getInfo().trim());
        if (vtuber.getName().equals("") ||
                vtuber.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        if (vtuberRepo.findByName(vtuber.getName()) != null) {
            return "Неможливо створити ще одного вітюбера із таким іменем.";
        }
        if (vtuber.getGeneration() == null) {
            return "Неправильний ідентифікатор покоління.";
        }
        return null;
    }

    public String editVTuberCheck(VTuber vtuber) {
        vtuber.setName(vtuber.getName().trim());
        vtuber.setName(vtuber.getInfo().trim());
        if (vtuber.getName().equals("") ||
                vtuber.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        if (vtuber.getGeneration() == null) {
            return "Неправильний ідентифікатор покоління.";
        }
        VTuberEntity temp = vtuberRepo.findById(vtuber.getId()).get();
        if (vtuber.getName().equalsIgnoreCase(temp.getName())) {
            if ((vtuber.getInfo().equals(temp.getInfo())) &&
                (vtuber.getGeneration() == temp.getGeneration()) &&
                    (vtuber.getName().equals(temp.getName()))) {
                return "Ви нічого не змінили";
            }
            return null;
        }
        if (vtuberRepo.findByName(vtuber.getName()) != null) {
            return "Вітюбер із таким іменем уже є.";
        }
        return null;
    }
}
