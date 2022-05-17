package com.glasiem.service;

import com.glasiem.entity.MediaEntity;
import com.glasiem.entity.VTuberEntity;
import com.glasiem.model.Media;
import com.glasiem.model.VTuber;
import com.glasiem.repository.MediaRepo;
import com.glasiem.repository.VTuberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    private VTuberRepo vtuberRepo;

    @Autowired
    private MediaRepo mediaRepo;

    public MediaRepo getMediaRepo(){
        return mediaRepo;
    }

    public VTuberRepo getVTuberRepo(){
        return vtuberRepo;
    }

    public String createMediaCheck(Media media) {
        media.setContents(media.getContents().trim());
        if (media.getContents().equals("")){
            return "Не залишайте поля порожніми";
        }
        if (media.getVtuber() == null) {
            return "Неправильний ідентифікатор вітюбера.";
        }
        return null;
    }

    public String editMediaCheck(Media media) {
        if (media.getVtuber() == null) {
            return "Неправильний ідентифікатор вітюбера.";
        }
        MediaEntity temp = mediaRepo.findById(media.getId()).get();
        if ((media.getContents().equals(temp.getContents())) &&
                (media.getVtuber() == temp.getVtuber())) {
            return "Ви нічого не змінили";

        }
        return null;
    }
}
