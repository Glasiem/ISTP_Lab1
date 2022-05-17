package com.glasiem.controller;

import com.glasiem.entity.*;
import com.glasiem.export.*;
import com.glasiem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExportController {
    @Autowired
    private AgencyService agencyService;

    @Autowired
    private GenerationService generationService;

    @Autowired
    private VTuberService vtuberService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ManagerService managerService;

    @RequestMapping("/export/agency")
    public void getAgencyData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=AgenciesData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<AgencyEntity> agencies = agencyService.getAgencyRepo().findAll();
        List<AgencyEntity> agenciesList = new ArrayList<>();
        for (AgencyEntity agency : agencies){
            agenciesList.add(agency);
        }
        AgencyToExcel excelExporter = new AgencyToExcel(agenciesList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/generation")
    public void getGenerationData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=GenerationsData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<GenerationEntity> generations = generationService.getGenerationRepo().findAll();
        List<GenerationEntity> generationsList = new ArrayList<>();
        for (GenerationEntity generation : generations){
            generationsList.add(generation);
        }
        GenerationToExcel excelExporter = new GenerationToExcel(generationsList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/vtuber")
    public void getVTuberData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=VTubersData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<VTuberEntity> vtubers = vtuberService.getVTuberRepo().findAll();
        List<VTuberEntity> vtubersList = new ArrayList<>();
        for (VTuberEntity vtuber : vtubers){
            vtubersList.add(vtuber);
        }
        VTuberToExcel excelExporter = new VTuberToExcel(vtubersList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/media")
    public void getMediaData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MediaData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<MediaEntity> mediaIter = mediaService.getMediaRepo().findAll();
        List<MediaEntity> mediaList = new ArrayList<>();
        for (MediaEntity media : mediaIter){
            mediaList.add(media);
        }
        MediaToExcel excelExporter = new MediaToExcel(mediaList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/manager")
    public void getManagerData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ManagersData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<ManagerEntity> managers = managerService.getManagerRepo().findAll();
        List<ManagerEntity> managersList = new ArrayList<>();
        for (ManagerEntity manager : managers){
            managersList.add(manager);
        }
        ManagerToExcel excelExporter = new ManagerToExcel(managersList);
        excelExporter.export(response);
    }
}
