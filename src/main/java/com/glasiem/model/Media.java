package com.glasiem.model;

import com.glasiem.entity.MediaEntity;
import com.glasiem.entity.VTuberEntity;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class Media {

    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private String contents;

    @NotNull
    @NotEmpty
    private VTuberEntity vtuber;

    public Media(@NotNull @NotEmpty String contents,
                 @NotNull @NotEmpty VTuberEntity vtuber) {
        this.contents = contents;
        this.vtuber = vtuber;
    }

    public Media() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public VTuberEntity getVtuber() {
        return vtuber;
    }

    public void setVtuber(VTuberEntity vtuber) {
        this.vtuber = vtuber;
    }
}
