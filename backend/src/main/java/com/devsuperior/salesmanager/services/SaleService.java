package com.devsuperior.salesmanager.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.salesmanager.DTO.SaleDTO;
import com.devsuperior.salesmanager.entities.Sale;
import com.devsuperior.salesmanager.entities.Team;
import com.devsuperior.salesmanager.entities.User;
import com.devsuperior.salesmanager.repositories.SaleRepository;
import com.devsuperior.salesmanager.repositories.TeamRepository;
import com.devsuperior.salesmanager.repositories.UserRepository;
import com.devsuperior.salesmanager.services.exceptions.DatabaseException;
import com.devsuperior.salesmanager.services.exceptions.ResourceNotFoundException;
import com.devsuperior.salesmanager.services.exceptions.UnauthorizedException;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private AuthService authService;

	@Transactional(readOnly = true)
	public Page<SaleDTO> findAllPaged(Pageable pageable) {
		User user = authService.authenticated();
		if (user.hasRole("ROLE_SELLER")) {
			Page<Sale> page = repository.findBySeller(user, pageable);
			return page.map(x -> new SaleDTO(x));
		} else if (user.hasRole("ROLE_MANAGER")) {		
			Page<Sale> page = repository.findByTeam(user.getTeam(), pageable);
			return page.map(x -> new SaleDTO(x));
		} else {
			Page<Sale> page = repository.findAll(pageable);
			return page.map(x -> new SaleDTO(x));
		}
	}

	@Transactional(readOnly = true)
	public SaleDTO findById(Long id) {
		User user = authService.authenticated();
		if (user.hasRole("ROLE_SELLER")) {
			Optional<Sale> obj = repository.findById(id);
			Sale entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
			if (user.getId().equals(entity.getSeller().getId())) {
				return new SaleDTO(entity);
			} else {
				throw new UnauthorizedException("Unauthorized");
			}
		} else {
			Optional<Sale> obj = repository.findById(id);
			Sale entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
			return new SaleDTO(entity);
		}
	}

	@Transactional
	public SaleDTO insert(SaleDTO dto) {
		User user = authService.authenticated();
		if (user.hasRole("ROLE_SELLER")) {
			Sale entity = new Sale();
			entity.setDate(dto.getDate());
			entity.setVisited(dto.getVisited());
			entity.setDeals(dto.getDeals());
			entity.setAmount(dto.getAmount());
			entity.setSeller(user);
			entity = repository.save(entity);
			return new SaleDTO(entity);
		} else {
			throw new UnauthorizedException("Unauthorized, Not a seller");
		}

	}

	@Transactional
	public SaleDTO update(Long id, SaleDTO dto) {
		User user = authService.authenticated();
		if (user.hasRole("ROLE_SELLER")) {
			try {
				Sale entity = repository.getOne(id);
				entity.setDate(dto.getDate());
				entity.setVisited(dto.getVisited());
				entity.setDeals(dto.getDeals());
				entity.setAmount(dto.getAmount());
				entity.setSeller(user);
				entity = repository.save(entity);
				return new SaleDTO(entity);
			} catch (EntityNotFoundException e) {
				throw new ResourceNotFoundException("Entity not found");
			}
		} else {
			throw new UnauthorizedException("Unauthorized, Not a seller");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
