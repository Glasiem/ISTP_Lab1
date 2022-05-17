package com.glasiem.controller;

import com.glasiem.entity.AgencyEntity;
import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.ManagerEntity;
import com.glasiem.entity.VTuberEntity;
import com.glasiem.model.*;
import com.glasiem.service.AgencyService;
import com.glasiem.service.GoogleChartsService;
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
public class AgencyController {
    @Autowired
    private AgencyService agencyService;

    @Autowired
    private GoogleChartsService gcs;

    @RequestMapping("/agency")
    public String getAgencies(Model model){
        Iterable<AgencyEntity> agencies = agencyService.getAgencyRepo().findAll();
        model.addAttribute("agencies", agencies);
        model.addAttribute("agenciesData", gcs.getVTuberAgencyMap(agencies));

        return "agencies/agency";
    }

    @RequestMapping("/agency/create")
    public String getCreateAgency(Model model){
        return "/agencies/agency-create";
    }

    @PostMapping("/agency/create")
    public String createAgency(@Valid Agency agency, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (agencyService.createAgencyCheck(agency) != null){
            redirectAttributes.addFlashAttribute("wrongData", agencyService.createAgencyCheck(agency));
            return "redirect:" + referer;
        }
        AgencyEntity agencyEntity = new AgencyEntity(agency.getName(), agency.getInfo());
        agencyService.getAgencyRepo().save(agencyEntity);

        return "redirect:/agency";
    }

    @RequestMapping("/agency/edit")
    public String getEditAgency(@Valid Agency agency, Model model){
        model.addAttribute("id", agency.getId());
        model.addAttribute("name", agency.getName());
        model.addAttribute("info", agency.getInfo());

        return "/agencies/agency-edit";
    }

    @PostMapping("/agency/edit")
    public String editAgency(@Valid Agency agency, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (agencyService.editAgencyCheck(agency) != null){
            redirectAttributes.addFlashAttribute("wrongData", agencyService.editAgencyCheck(agency));
            return "redirect:" + referer;
        }
        AgencyEntity agencyEntity = agencyService.getAgencyRepo().findById(agency.getId()).get();
        AgencyEntity returnEntity = new AgencyEntity(agency.getName(),agency.getInfo());
        BeanUtils.copyProperties(returnEntity, agencyEntity, "id");
        agencyService.getAgencyRepo().save(agencyEntity);

        return "redirect:/agency";
    }

    @RequestMapping("/agency/delete")
    public String getDeleteBrand(@Valid Agency agency, Model model){

        model.addAttribute("id", agency.getId());
        model.addAttribute("name", agency.getName());
        model.addAttribute("info", agency.getInfo());

        return "/agencies/agency-delete";
    }

    @Transactional
    @PostMapping("/agency/delete")
    public String deleteAgency(@Valid Agency agency, Model model, RedirectAttributes redirectAttributes){

        AgencyEntity brandEntity = agencyService.getAgencyRepo().findById(agency.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Агенство з назвою " + brandEntity.getName() + " було видалено.");
        agencyService.getManagerRepo().deleteByAgency_Id(agency.getId());
        Iterable<GenerationEntity> generations = agencyService.getGenerationRepo().findAllByAgency_Id(agency.getId());
        for(GenerationEntity generation : generations){
            Iterable<VTuberEntity> vtubers = agencyService.getVtuberRepo().findAllByGeneration_Id(generation.getId());
            for (VTuberEntity vtuber: vtubers) {
                agencyService.getMediaRepo().deleteAllByVtuber_Id(vtuber.getId());
            }
            agencyService.getVtuberRepo().deleteAllByGeneration_Id(generation.getId());
        }
        agencyService.getGenerationRepo().deleteAllByAgency_Id(agency.getId());
        agencyService.getAgencyRepo().deleteById(agency.getId());

        return "redirect:/agency";
    }
}
