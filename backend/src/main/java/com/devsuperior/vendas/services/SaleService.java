package com.devsuperior.vendas.services;

import com.devsuperior.vendas.dto.SaleDTO;
import com.devsuperior.vendas.entities.Sale;
import com.devsuperior.vendas.entities.User;
import com.devsuperior.vendas.repositories.SaleRepository;
import com.devsuperior.vendas.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;
    
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<SaleDTO> findAll(Pageable pageable) {
        Page<Sale> page = repository.findAll(pageable);
        return page.map(x -> new SaleDTO(x));
    }

    @Transactional(readOnly = true)
    public SaleDTO findById(Long id) {
        Optional<Sale> obj = repository.findById(id);
        Sale entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not Found"));
        return new SaleDTO(entity);
    }
    
    @Transactional
    public SaleDTO insert(SaleDTO dto) {
    	Optional<User> obj = userRepository.findById(dto.getUserSellerId());
    	User user = obj.orElseThrow(() -> new EntityNotFoundException("Entity not Found"));
    	Sale entity = new Sale();
    	entity.setDate(dto.getDate());
    	entity.setVisited(dto.getVisited());
    	entity.setDeals(dto.getVisited());
    	entity.setAmount(dto.getAmount());
    	entity.getSeller().setId(user.getId());
    	return new SaleDTO(entity);
    }

}

