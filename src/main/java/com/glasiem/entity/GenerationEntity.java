package com.glasiem.entity;

import com.glasiem.model.Generation;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class GenerationEntity extends Generation {

    public GenerationEntity(@NotNull @NotEmpty AgencyEntity agency,
                            @NotNull @NotEmpty String name,
                            @NotNull @NotEmpty String info) {
        super(agency, name, info);
    }

    public GenerationEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @ManyToOne
    @Override
    public AgencyEntity getAgency() {
        return super.getAgency();
    }

    @Override
    public void setAgency(AgencyEntity agency) {
        super.setAgency(agency);
    }

    @Column(length = 50, nullable = false, unique = true)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Column(length = 500, nullable = false, unique = true)
    @Override
    public String getInfo() {
        return super.getInfo();
    }

    @Override
    public void setInfo(String info) {
        super.setInfo(info);
    }
}
