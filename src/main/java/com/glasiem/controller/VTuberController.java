package com.glasiem.controller;

import com.glasiem.entity.AgencyEntity;
import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.VTuberEntity;
import com.glasiem.model.Generation;
import com.glasiem.model.VTuber;
import com.glasiem.service.VTuberService;
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
public class VTuberController {

    @Autowired
    private VTuberService vtuberService;

    @RequestMapping("/vtuber")
    public String getVTubers(Model model){
        Iterable<VTuberEntity> vtubers = vtuberService.getVTuberRepo().findAll();
        model.addAttribute("vtubers", vtubers);

        return "/vtubers/vtuber";
    }

    @RequestMapping("/vtuber/create")
    public String getCreateVTuber(Model model){
        Iterable<VTuberEntity> vtubers = vtuberService.getVTuberRepo().findAll();
        Iterable<GenerationEntity> generations = vtuberService.getGenerationRepo().findAll();
        model.addAttribute("vtubers", vtubers);
        model.addAttribute("generations", generations);
        return "/vtubers/vtuber-create";
    }

    @PostMapping("/vtuber/create")
    public String createVTuber(@Valid VTuber vtuber, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");

        if (vtuberService.createVTuberCheck(vtuber) != null){
            redirectAttributes.addFlashAttribute("wrongData", vtuberService.createVTuberCheck(vtuber));
            return "redirect:" + referer;
        }

        GenerationEntity generationEntity = vtuberService.getGenerationRepo().findById(vtuber.getGeneration().getId()).get();
        VTuberEntity vtuberEntity = new VTuberEntity(generationEntity, vtuber.getName(),vtuber.getInfo());
        vtuberService.getVTuberRepo().save(vtuberEntity);

        return "redirect:/vtuber";
    }

    @RequestMapping("/vtuber/edit")
    public String getEditVTuber(@Valid VTuber vtuber, Model model){
        Iterable<GenerationEntity> generations = vtuberService.getGenerationRepo().findAll();
        model.addAttribute("id", vtuber.getId());
        model.addAttribute("name", vtuber.getName());
        model.addAttribute("info", vtuber.getInfo());
        model.addAttribute("agency", vtuber.getGeneration());
        model.addAttribute("generations", generations);

        return "/vtubers/vtuber-edit";
    }

    @PostMapping("/vtuber/edit")
    public String editVTuber(@Valid VTuber vtuber, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (vtuberService.editVTuberCheck(vtuber) != null){
            redirectAttributes.addFlashAttribute("wrongData", vtuberService.editVTuberCheck(vtuber));
            return "redirect:" + referer;
        }
        GenerationEntity generationEntity = vtuberService.getGenerationRepo().findById(vtuber.getGeneration().getId()).get();
        VTuberEntity vtuberEntity = vtuberService.getVTuberRepo().findById(vtuber.getId()).get();
        VTuberEntity returnEntity = new VTuberEntity(generationEntity, vtuber.getName(),vtuber.getInfo());
        BeanUtils.copyProperties(returnEntity, vtuberEntity, "id");
        vtuberService.getVTuberRepo().save(vtuberEntity);

        return "redirect:/vtuber";
    }

    @RequestMapping("/vtuber/delete")
    public String getDeleteBrand(@Valid VTuber vtuber, Model model){

        model.addAttribute("id", vtuber.getId());
        model.addAttribute("name", vtuber.getName());
        model.addAttribute("info", vtuber.getInfo());
        model.addAttribute("generation", vtuber.getGeneration());

        return "/vtubers/vtuber-delete";
    }

    @Transactional
    @PostMapping("/vtuber/delete")
    public String deleteGeneration(@Valid VTuber vtuber, Model model, RedirectAttributes redirectAttributes){

        VTuberEntity generationEntity = vtuberService.getVTuberRepo().findById(vtuber.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Вітюбер з іменем " + generationEntity.getName() + " був видалений.");
        vtuberService.getMediaRepo().deleteAllByVtuber_Id(vtuber.getId());
        vtuberService.getVTuberRepo().deleteById(vtuber.getId());

        return "redirect:/vtuber";
    }

    @RequestMapping("/vtuber/sorted")
    public String getVTubers(@Valid Generation generation, Model model){
        Iterable<VTuberEntity> vtubers = vtuberService.getVTuberRepo().findAllByGeneration_Id(generation.getId());
        model.addAttribute("vtubers", vtubers);
        model.addAttribute("generationName", "Вітюбери покоління " + generation.getName());

        return "/vtubers/vtuber-sorted";
    }
}
