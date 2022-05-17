package com.glasiem.entity;

import com.glasiem.model.Manager;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class ManagerEntity extends Manager {

    public ManagerEntity(@NotNull @NotEmpty AgencyEntity agency,
                         @NotNull @NotEmpty String name,
                         @NotNull @NotEmpty String info) {
        super(agency, name, info);
    }

    public ManagerEntity() {
        super();
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

    @OneToOne
    @Override
    public AgencyEntity getAgency() {
        return super.getAgency();
    }

    @Override
    public void setAgency(AgencyEntity agency) {
        super.setAgency(agency);
    }

    @Column(length = 50, nullable = false, unique = false)
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
