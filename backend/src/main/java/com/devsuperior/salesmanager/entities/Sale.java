package com.devsuperior.salesmanager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
@Entity
@Table(name = "tb_sale")
public class Sale implements Serializable {
private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Integer deals;
    private Integer visited;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "userSellerId")
    private User seller;
    
    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;
    
    
    public Sale() {
    }

    public Sale(Long id, LocalDate date, Integer visited,Integer deals, Double amount, User seller, Team team) {
        this.id = id;
        this.date = date;
        this.visited = visited;
        this.deals = deals;
        this.amount = amount;
        this.seller = seller;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    public Integer getDeals() {
        return deals;
    }

    public void setDeals(Integer deals) {
        this.deals = deals;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
    
    


	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
