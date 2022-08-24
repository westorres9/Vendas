package com.devsuperior.salesmanager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_team")
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private User manager;
    
    @OneToMany(mappedBy = "team")
    private List<Sale> sales = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tb_team_seller",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id"))
    private Set<User> sellers = new HashSet<>();

    public Team() {
    }

    public Team(Long id, String name, User manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Set<User> getSellers() {
        return sellers;
    }

    public List<Sale> getSales() {
		return sales;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
