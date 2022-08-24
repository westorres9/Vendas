package com.devsuperior.salesmanager.DTO;

import com.devsuperior.salesmanager.entities.Sale;

import java.io.Serializable;
import java.time.LocalDate;

public class SaleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDate date;
    private Integer deals;
    private Integer visited;
    private Double amount;
    private Long sellerId;
    private Long teamId;
    

    public SaleDTO() {
    }

    public SaleDTO(Long id, LocalDate date, Integer deals, Integer visited, Double amount, Long sellerId, Long teamId) {
        this.id = id;
        this.date = date;
        this.deals = deals;
        this.visited = visited;
        this.amount = amount;
        this.sellerId = sellerId;
        this.teamId = teamId;
    }

    public SaleDTO(Sale entity) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.deals = entity.getDeals();
        this.visited = entity.getVisited();
        this.amount = entity.getAmount();
        this.sellerId = entity.getSeller().getId();
        this.teamId = entity.getTeam().getId();
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

    public Integer getDeals() {
        return deals;
    }

    public void setDeals(Integer deals) {
        this.deals = deals;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
    
    


	
    
}
