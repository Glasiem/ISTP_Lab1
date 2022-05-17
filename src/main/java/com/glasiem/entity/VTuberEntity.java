package com.glasiem.entity;

import com.glasiem.model.VTuber;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
public class VTuberEntity extends VTuber {

    public VTuberEntity(@NotNull @NotEmpty GenerationEntity generation,
                        @NotNull @NotEmpty String name,
                        @NotNull @NotEmpty String info) {
        super(generation, name, info);
    }

    public VTuberEntity() {
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
    public GenerationEntity getGeneration() {
        return super.getGeneration();
    }

    @Override
    public void setGeneration(GenerationEntity generation) {
        super.setGeneration(generation);
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

    @Column(length = 256, nullable = false, unique = false)
    @Override
    public String getInfo() {
        return super.getInfo();
    }

    @Override
    public void setInfo(String info) {
        super.setInfo(info);
    }
}
