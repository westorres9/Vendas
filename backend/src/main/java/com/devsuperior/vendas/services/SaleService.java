package com.devsuperior.vendas.services;

import com.devsuperior.vendas.dto.SaleDTO;
import com.devsuperior.vendas.entities.Sale;
import com.devsuperior.vendas.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    @Transactional(readOnly = true)
    public Page<SaleDTO> findAll(Pageable pageable) {
        Page<Sale> page = repository.findAll(pageable);
            return page.map(x -> new SaleDTO(x));
        }
    }

