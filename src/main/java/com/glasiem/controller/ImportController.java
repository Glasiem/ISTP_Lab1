package com.glasiem.controller;

import com.glasiem.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping("/agency")
    public String uploadAgency(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        if (importService.importAgency(reapExcelDataFile) != null) {
            redirectAttributes.addFlashAttribute("wrongData", importService.importAgency(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/generation")
    public String uploadGeneration(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importGeneration(reapExcelDataFile) != null) {
            redirectAttributes.addFlashAttribute("wrongData", importService.importGeneration(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/vtuber")
    public String uploadVtuber(@RequestParam("file") MultipartFile reapExcelDataFile,
                                   HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importVTuber(reapExcelDataFile) != null) {
            redirectAttributes.addFlashAttribute("wrongData", importService.importVTuber(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/media")
    public String uploadMedia(@RequestParam("file") MultipartFile reapExcelDataFile,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importMedia(reapExcelDataFile) != null) {
            redirectAttributes.addFlashAttribute("wrongData", importService.importMedia(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/manager")
    public String uploadManager(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importManager(reapExcelDataFile) != null) {
            redirectAttributes.addFlashAttribute("wrongData", importService.importManager(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }
}

