package com.glasiem.model;

import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.MediaEntity;
import com.glasiem.entity.VTuberEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class VTuber {

    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private GenerationEntity generation;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String info;

    public VTuber(@NotNull @NotEmpty GenerationEntity generation,
                  @NotNull @NotEmpty String name,
                  @NotNull @NotEmpty String info) {
        this.generation = generation;
        this.name = name;
        this.info = info;
    }

    public VTuber() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenerationEntity getGeneration() {
        return generation;
    }

    public void setGeneration(GenerationEntity generation) {
        this.generation = generation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
