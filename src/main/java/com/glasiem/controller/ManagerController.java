package com.glasiem.controller;

import com.glasiem.entity.AgencyEntity;
import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.ManagerEntity;
import com.glasiem.entity.MediaEntity;
import com.glasiem.model.Agency;
import com.glasiem.model.Generation;
import com.glasiem.model.Manager;
import com.glasiem.model.Media;
import com.glasiem.service.ManagerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @RequestMapping("/manager")
    public String getManagers(Model model){
        Iterable<ManagerEntity> managers = managerService.getManagerRepo().findAll();
        model.addAttribute("managers", managers);

        return "managers/manager";
    }

    @RequestMapping("/manager/create")
    public String getCreateManager(Model model){
        Iterable<ManagerEntity> managers = managerService.getManagerRepo().findAll();
        Iterable<AgencyEntity> agencies = managerService.getAgencyRepo().findAll();
        model.addAttribute("managers", managers);
        model.addAttribute("agencies", agencies);
        return "/managers/manager-create";
    }

    @PostMapping("/manager/create")
    public String createManager(@Valid Manager manager, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");

        if (managerService.createManagerCheck(manager) != null){
            redirectAttributes.addFlashAttribute("wrongData", managerService.createManagerCheck(manager));
            return "redirect:" + referer;
        }

        AgencyEntity agencyEntity = managerService.getAgencyRepo().findById(manager.getAgency().getId()).get();
        ManagerEntity managerEntity = new ManagerEntity(agencyEntity, manager.getName(), manager.getInfo());
        managerService.getManagerRepo().save(managerEntity);

        return "redirect:/manager";
    }

    @RequestMapping("/manager/edit")
    public String getEditManager(@Valid Manager manager, Model model){
        Iterable<AgencyEntity> agencies = managerService.getAgencyRepo().findAll();
        model.addAttribute("id", manager.getId());
        model.addAttribute("name", manager.getName());
        model.addAttribute("info", manager.getInfo());
        model.addAttribute("agency", manager.getAgency());
        model.addAttribute("agencies", agencies);

        return "/managers/manager-edit";
    }

    @PostMapping("/manager/edit")
    public String editManager(@Valid Manager manager, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (managerService.editManagerCheck(manager) != null){
            redirectAttributes.addFlashAttribute("wrongData", managerService.editManagerCheck(manager));
            return "redirect:" + referer;
        }
        AgencyEntity agencyEntity = managerService.getAgencyRepo().findById(manager.getAgency().getId()).get();
        ManagerEntity generationEntity = managerService.getManagerRepo().findById(manager.getId()).get();
        ManagerEntity returnEntity = new ManagerEntity(agencyEntity, manager.getName(),manager.getInfo());
        BeanUtils.copyProperties(returnEntity, generationEntity, "id");
        managerService.getManagerRepo().save(generationEntity);

        return "redirect:/manager";
    }

    @RequestMapping("/manager/delete")
    public String getDeleteBrand(@Valid Manager manager, Model model){

        model.addAttribute("id", manager.getId());
        model.addAttribute("name", manager.getName());
        model.addAttribute("info", manager.getInfo());
        model.addAttribute("agency", manager.getAgency());

        return "/managers/manager-delete";
    }

    @Transactional
    @PostMapping("/manager/delete")
    public String deleteGeneration(@Valid Manager manager, Model model, RedirectAttributes redirectAttributes){

        ManagerEntity managerEntity = managerService.getManagerRepo().findById(manager.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "СЕО зі змістом" + managerEntity.getName() + " було видалено.");
        managerService.getManagerRepo().deleteById(manager.getId());

        return "redirect:/manager";
    }

    @RequestMapping("/manager/sorted")
    public String getGenerationsSorted(@Valid Agency agency, Model model){
        Iterable<ManagerEntity> managers = managerService.getManagerRepo().findAllByAgency_Id(agency.getId());
        model.addAttribute("managers", managers);
        model.addAttribute("agencyName", "CEO агенства " + agency.getName());

        return "managers/manager-sorted";
    }
}
