package com.glasiem.controller;

import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.MediaEntity;
import com.glasiem.entity.VTuberEntity;
import com.glasiem.model.Media;
import com.glasiem.model.VTuber;
import com.glasiem.service.MediaService;
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
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @RequestMapping("/media")
    public String getMedia(Model model){
        Iterable<MediaEntity> media = mediaService.getMediaRepo().findAll();
        model.addAttribute("media", media);

        return("media/media");
    }

    @RequestMapping("/media/create")
    public String getCreateMedia(Model model){
        Iterable<MediaEntity> media = mediaService.getMediaRepo().findAll();
        Iterable<VTuberEntity> vtubers = mediaService.getVTuberRepo().findAll();
        model.addAttribute("media", media);
        model.addAttribute("vtubers", vtubers);
        return "/media/media-create";
    }

    @PostMapping("/media/create")
    public String createMedia(@Valid Media media, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");

        if (mediaService.createMediaCheck(media) != null){
            redirectAttributes.addFlashAttribute("wrongData", mediaService.createMediaCheck(media));
            return "redirect:" + referer;
        }

        VTuberEntity vtuberEntity = mediaService.getVTuberRepo().findById(media.getVtuber().getId()).get();
        MediaEntity mediaEntity = new MediaEntity(media.getContents(), vtuberEntity);
        mediaService.getMediaRepo().save(mediaEntity);

        return "redirect:/media";
    }

    @RequestMapping("/media/edit")
    public String getEditMedia(@Valid Media media, Model model){
        Iterable<VTuberEntity> vtubers = mediaService.getVTuberRepo().findAll();
        model.addAttribute("id", media.getId());
        model.addAttribute("contents", media.getContents());
        model.addAttribute("agency", media.getVtuber());
        model.addAttribute("vtubers", vtubers);

        return "/media/media-edit";
    }

    @PostMapping("/media/edit")
    public String editMedia(@Valid Media media, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (mediaService.editMediaCheck(media) != null){
            redirectAttributes.addFlashAttribute("wrongData", mediaService.editMediaCheck(media));
            return "redirect:" + referer;
        }
        VTuberEntity vtuberEntity = mediaService.getVTuberRepo().findById(media.getVtuber().getId()).get();
        MediaEntity mediaEntity = mediaService.getMediaRepo().findById(media.getId()).get();
        MediaEntity returnEntity = new MediaEntity(media.getContents(), vtuberEntity);
        BeanUtils.copyProperties(returnEntity, mediaEntity, "id");
        mediaService.getMediaRepo().save(mediaEntity);

        return "redirect:/media";
    }

    @RequestMapping("/media/delete")
    public String getDeleteBrand(@Valid Media media, Model model){

        model.addAttribute("id", media.getId());
        model.addAttribute("contents", media.getContents());
        model.addAttribute("vtuber", media.getVtuber());

        return "/media/media-delete";
    }

    @Transactional
    @PostMapping("/media/delete")
    public String deleteGeneration(@Valid Media media, Model model, RedirectAttributes redirectAttributes){

        MediaEntity mediaEntity = mediaService.getMediaRepo().findById(media.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Медіа зі змістом" + mediaEntity.getContents() + " було видалено.");
        mediaService.getMediaRepo().deleteById(media.getId());

        return "redirect:/media";
    }

    @RequestMapping("/media/sorted")
    public String getMedia(@Valid VTuber vtuber, Model model){
        Iterable<MediaEntity> media = mediaService.getMediaRepo().findAllByVtuber_Id(vtuber.getId());
        model.addAttribute("media", media);
        model.addAttribute("vtuberName", "Медіа вітюбера " + vtuber.getName());

        return("media/media-sorted");
    }
}
