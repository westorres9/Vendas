package com.devsuperior.salesmanager.DTO;

import com.devsuperior.salesmanager.entities.Team;
import com.devsuperior.salesmanager.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TeamDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long managerId;

    public TeamDTO() {
    }

    public TeamDTO(Long id, String name, Long managerId) {
        this.id = id;
        this.name = name;
        this.managerId= managerId;
    }

    public TeamDTO(Team entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.managerId = entity.getManager().getId();
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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

}
