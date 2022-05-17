package com.glasiem.service;

import com.glasiem.entity.ManagerEntity;
import com.glasiem.model.Manager;
import com.glasiem.repository.AgencyRepo;
import com.glasiem.repository.ManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepo managerRepo;

    @Autowired
    private AgencyRepo agencyRepo;

    public ManagerRepo getManagerRepo(){
        return managerRepo;
    }

    public AgencyRepo getAgencyRepo(){
        return agencyRepo;
    }

    public String createManagerCheck(Manager manager) {
        manager.setName(manager.getName().trim());
        manager.setName(manager.getInfo().trim());
        if (manager.getName().equals("") ||
                manager.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        ManagerEntity temp = managerRepo.findByAgency_Id(manager.getAgency().getId());
        if (temp != null) {
            return "У цього агенства вже є СЕО.";
        }
        if (manager.getAgency() == null) {
            return "Неправильний ідентифікатор агенства.";
        }
        return null;
    }

    public String editManagerCheck(Manager manager) {
        manager.setName(manager.getName().trim());
        manager.setName(manager.getInfo().trim());
        if (manager.getName().equals("") ||
                manager.getInfo().equals("")){
            return "Не залишайте поля порожніми";
        }
        if (manager.getAgency() == null) {
            return "Неправильний ідентифікатор агенства.";
        }
        Iterable<ManagerEntity> list = managerRepo.findAllByAgency_Id(manager.getAgency().getId());
        for (ManagerEntity entity:list) {
            if (entity.getId() != manager.getId()){
                return "У цього агенства вже є СЕО.";
            }
        }
        ManagerEntity temp = managerRepo.findById(manager.getId()).get();
        if ((manager.getName().equals(temp.getName()))  &&
            (manager.getInfo().equals(temp.getInfo())) &&
                    (manager.getAgency() == temp.getAgency())){
                return "Ви нічого не змінили";

        }
        return null;
    }
}
