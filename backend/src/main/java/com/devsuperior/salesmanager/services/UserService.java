package com.devsuperior.salesmanager.services;

import com.devsuperior.salesmanager.DTO.RoleDTO;
import com.devsuperior.salesmanager.DTO.UserDTO;
import com.devsuperior.salesmanager.DTO.UserInsertDTO;
import com.devsuperior.salesmanager.DTO.UserUpdateDTO;
import com.devsuperior.salesmanager.entities.Role;
import com.devsuperior.salesmanager.entities.User;
import com.devsuperior.salesmanager.repositories.RoleRepository;
import com.devsuperior.salesmanager.repositories.UserRepository;
import com.devsuperior.salesmanager.services.exceptions.DatabaseException;
import com.devsuperior.salesmanager.services.exceptions.ResourceNotFoundException;
import com.devsuperior.salesmanager.services.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable, Long id) {
        authService.validateAdmin(id);
        Page<User> page = repository.findAll(pageable);
        return page.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        authService.validateSelfOrAdmin(id);
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
        return new UserDTO(entity);
    }

    public UserDTO insert(UserInsertDTO dto) {
        User user = authService.authenticated();
        if (user.hasRole("ROLE_ADMIN") || user.hasRole("ROLE_MANAGER")) {
            User entity = new User();
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } else {
            throw new UnauthorizedException("Unauthorized user");
        }

    }

    public UserDTO update(Long id, UserUpdateDTO dto) {
        User user = authService.authenticated();
        if (user.hasRole("ROLE_ADMIN") || user.hasRole("ROLE_MANAGER")) {
            try {
                User entity = repository.getOne(id);
                copyDtoToEntity(dto, entity);
                entity = repository.save(entity);
                return new UserDTO(entity);
            } catch (EntityNotFoundException e) {
                throw new ResourceNotFoundException("Id Not Found " + id);
            }
        } else {
            throw new UnauthorizedException("Unauthorized user");
        }

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id Not Found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    public void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getOne(roleDto.getId());
            entity.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByEmail(username);
        if (user == null) {
            logger.error("User not found " + username);
            throw new UsernameNotFoundException("Email Not found");
        }
        logger.info("User found " + username);
        return user;
    }
}