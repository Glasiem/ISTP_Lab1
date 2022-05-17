package com.glasiem.controller;

import com.glasiem.entity.AgencyEntity;
import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.VTuberEntity;
import com.glasiem.model.Agency;
import com.glasiem.model.Generation;
import com.glasiem.service.GenerationService;
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
public class GenerationController {
    @Autowired
    private GenerationService generationService;

    @RequestMapping("/generation")
    public String getGenerations(Model model){
        Iterable <GenerationEntity> generations = generationService.getGenerationRepo().findAll();
        model.addAttribute("generations", generations);

        return "generations/generation";
    }

    @RequestMapping("/generation/create")
    public String getCreateGeneration(Model model){
        Iterable<GenerationEntity> generations = generationService.getGenerationRepo().findAll();
        Iterable<AgencyEntity> agencies = generationService.getAgencyRepo().findAll();
        model.addAttribute("generations", generations);
        model.addAttribute("agencies", agencies);
        return "/generations/generation-create";
    }

    @PostMapping("/generation/create")
    public String createGeneration(@Valid Generation generation, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");

        if (generationService.createGenerationCheck(generation) != null){
            redirectAttributes.addFlashAttribute("wrongData", generationService.createGenerationCheck(generation));
            return "redirect:" + referer;
        }

        AgencyEntity agencyEntity = generationService.getAgencyRepo().findById(generation.getAgency().getId()).get();
        GenerationEntity generationEntity = new GenerationEntity(agencyEntity, generation.getName(),generation.getInfo());
        generationService.getGenerationRepo().save(generationEntity);

        return "redirect:/generation";
    }

    @RequestMapping("/generation/edit")
    public String getEditGeneration(@Valid Generation generation, Model model){
        Iterable<AgencyEntity> agencies = generationService.getAgencyRepo().findAll();
        model.addAttribute("id", generation.getId());
        model.addAttribute("name", generation.getName());
        model.addAttribute("info", generation.getInfo());
        model.addAttribute("agency", generation.getAgency());
        model.addAttribute("agencies", agencies);

        return "/generations/generation-edit";
    }

    @PostMapping("/generation/edit")
    public String editGeneration(@Valid Generation generation, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (generationService.editGenerationCheck(generation) != null){
            redirectAttributes.addFlashAttribute("wrongData", generationService.editGenerationCheck(generation));
            return "redirect:" + referer;
        }
        AgencyEntity agencyEntity = generationService.getAgencyRepo().findById(generation.getAgency().getId()).get();
        GenerationEntity generationEntity = generationService.getGenerationRepo().findById(generation.getId()).get();
        GenerationEntity returnEntity = new GenerationEntity(agencyEntity, generation.getName(),generation.getInfo());
        BeanUtils.copyProperties(returnEntity, generationEntity, "id");
        generationService.getGenerationRepo().save(generationEntity);

        return "redirect:/generation";
    }

    @RequestMapping("/generation/delete")
    public String getDeleteBrand(@Valid Generation generation, Model model){

        model.addAttribute("id", generation.getId());
        model.addAttribute("name", generation.getName());
        model.addAttribute("info", generation.getInfo());
        model.addAttribute("agency", generation.getAgency());

        return "/generations/generation-delete";
    }

    @Transactional
    @PostMapping("/generation/delete")
    public String deleteGeneration(@Valid Generation generation, Model model, RedirectAttributes redirectAttributes){

        GenerationEntity generationEntity = generationService.getGenerationRepo().findById(generation.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Покоління з назвою " + generationEntity.getName() + " було видалено.");
        Iterable<VTuberEntity> vtubers = generationService.getVtuberRepo().findAllByGeneration_Id(generation.getId());
        for (VTuberEntity vtuber: vtubers) {
            generationService.getMediaRepo().deleteAllByVtuber_Id(vtuber.getId());
        }
        generationService.getVtuberRepo().deleteAllByGeneration_Id(generation.getId());
        generationService.getGenerationRepo().deleteById(generation.getId());

        return "redirect:/generation";
    }

    @RequestMapping("/generation/sorted")
    public String getGenerationsSorted(@Valid Agency agency, Model model){
        Iterable <GenerationEntity> generations = generationService.getGenerationRepo().findAllByAgency_Id(agency.getId());
        model.addAttribute("generations", generations);
        model.addAttribute("agencyName", "Покоління агенства " + agency.getName());

        return "generations/generation-sorted";
    }

}
