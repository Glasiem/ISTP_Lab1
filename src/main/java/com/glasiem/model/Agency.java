package com.glasiem.model;


import javax.validation.constraints.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Agency {

    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String info;

    public Agency(@NotNull @NotEmpty String name,
                  @NotNull @NotEmpty String info) {
        this.name = name;
        this.info = info;
    }

    public Agency() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
