package com.devsuperior.vendas.dto;

import com.devsuperior.vendas.entities.Sale;

import java.io.Serializable;
import java.time.LocalDate;

public class SaleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDate date;
    private Integer deals;
    private Integer visited;
    private Double amount;

    public SaleDTO() {
    }

    public SaleDTO(Long id, LocalDate date, Integer deals, Integer visited, Double amount) {
        this.id = id;
        this.date = date;
        this.deals = deals;
        this.visited = visited;
        this.amount = amount;
    }

    public SaleDTO(Sale entity) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.deals = entity.getDeals();
        this.visited = entity.getVisited();
        this.amount = entity.getAmount();
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


}
