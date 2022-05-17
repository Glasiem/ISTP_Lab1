package com.glasiem.entity;

import com.glasiem.model.Media;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Entity
public class MediaEntity extends Media {

    public MediaEntity(@NotNull @NotEmpty String contents,
                       @NotNull @NotEmpty VTuberEntity vtuber) {
        super(contents, vtuber);
    }

    public MediaEntity() {
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

    @Column(length = 500, nullable = false, unique = true)
    @Override
    public String getContents() {
        return super.getContents();
    }

    @Override
    public void setContents(String contents) {
        super.setContents(contents);
    }

    @ManyToOne
    @Override
    public VTuberEntity getVtuber() {
        return super.getVtuber();
    }

    @Override
    public void setVtuber(VTuberEntity vtuber) {
        super.setVtuber(vtuber);
    }
}
