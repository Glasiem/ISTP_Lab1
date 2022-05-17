package com.glasiem.entity;

import com.glasiem.model.Agency;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class AgencyEntity extends Agency {

    public AgencyEntity(@NotNull @NotEmpty String name,
                        @NotNull @NotEmpty String info) {
        super(name, info);
    }

    public AgencyEntity() {
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
