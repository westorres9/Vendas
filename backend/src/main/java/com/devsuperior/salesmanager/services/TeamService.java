package com.devsuperior.salesmanager.services;

import com.devsuperior.salesmanager.DTO.TeamDTO;
import com.devsuperior.salesmanager.entities.Team;
import com.devsuperior.salesmanager.entities.User;
import com.devsuperior.salesmanager.repositories.TeamRepository;
import com.devsuperior.salesmanager.repositories.UserRepository;
import com.devsuperior.salesmanager.services.exceptions.DatabaseException;
import com.devsuperior.salesmanager.services.exceptions.ResourceNotFoundException;
import com.devsuperior.salesmanager.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public Page<TeamDTO> findAllPaged(Pageable pageable) {
            User user = authService.authenticated();
            if (user.hasRole("ROLE_MANAGER")) {
                Page<Team> page = repository.findByManager(user, pageable);
                return page.map(x -> new TeamDTO(x));
            }
            else if (user.hasRole("ROLE_ADMIN")) {
                Page<Team> page = repository.findAll(pageable);
                return page.map(x -> new TeamDTO(x));
            }
        throw new UnauthorizedException("Unauthorized Exception");
    }

    @Transactional(readOnly = true)
    public TeamDTO findById(Long id) {
        User user = authService.authenticated();
        if (user.hasRole("ROLE_ADMIN")){
            Optional<Team> obj = repository.findById(id);
        Team entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new TeamDTO(entity);
        } else if (user.hasRole("ROLE_MANAGER")) {
            Optional<Team> obj = repository.findById(id);
            Team entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
            if(entity.getManager().getId().equals(user.getId())) {
                return new TeamDTO(entity);
            }
            throw new UnauthorizedException("Unauthorized");
        }
        throw new UnauthorizedException("Unauthorized");
    }


    @Transactional
    public TeamDTO insert(TeamDTO dto) {
        User user = authService.authenticated();
        if (user.hasRole("ROLE_ADMIN")) {
            Team entity = new Team();
            entity.setName(dto.getName());
            entity.setManager(userRepository.getOne(dto.getManagerId()));
            entity = repository.save(entity);
            return new TeamDTO(entity);
        }
        throw new UnauthorizedException("Unauthorized");
    }

    @Transactional
    public TeamDTO update(Long id, TeamDTO dto) {
        User user = authService.authenticated();
        if (user.hasRole("ROLE_ADMIN")) {
            try {
                Team entity = repository.getOne(id);
                entity.setName(dto.getName());
                entity.setManager(userRepository.getOne(dto.getManagerId()));
                entity = repository.save(entity);
                return new TeamDTO(entity);
            } catch (EntityNotFoundException e) {
                throw new ResourceNotFoundException("Entity not found");
            }
        }
        if (user.hasRole("ROLE_ADMIN")) {
            try {
                Team entity = repository.getOne(id);
                entity.setName(dto.getName());
                entity.setManager(user);
                entity = repository.save(entity);
            }
            catch (EntityNotFoundException e) {
                throw new ResourceNotFoundException("Entity not found");
            }
        }
        throw new UnauthorizedException("Unauthorized");
    }

    public void delete(Long id) {
        User user = authService.authenticated();
        if (user.hasRole("ROLE_ADMIN")) {
            try {
                repository.deleteById(id);
            } catch (EmptyResultDataAccessException e) {
                throw new ResourceNotFoundException("Id not found" + id);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Integrity violation");
            }
        }
        throw new UnauthorizedException("Unauthorized");
    }

}
