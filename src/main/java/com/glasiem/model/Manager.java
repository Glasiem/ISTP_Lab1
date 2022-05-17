package com.glasiem.model;

import com.glasiem.entity.AgencyEntity;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Manager {

    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private AgencyEntity agency;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String info;

    public Manager(@NotNull @NotEmpty AgencyEntity agency,
                   @NotNull @NotEmpty String name,
                   @NotNull @NotEmpty String info) {
        this.agency = agency;
        this.name = name;
        this.info = info;
    }

    public Manager(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgencyEntity getAgency() {
        return agency;
    }

    public void setAgency(AgencyEntity agency) {
        this.agency = agency;
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
