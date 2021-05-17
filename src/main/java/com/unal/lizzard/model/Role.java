package com.unal.lizzard.model;

import javax.persistence.*;


@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    public Role(String name) {
        super();
        this.name = name;
    }

    //Sin esto no funciona el login y esto no lo dice la guia
    public Role() {
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
}
