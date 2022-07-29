package com.devsuperior.vendas.services;

import com.devsuperior.vendas.dto.UserDTO;
import com.devsuperior.vendas.entities.User;
import com.devsuperior.vendas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> page = repository.findAll(pageable);
        return page.map(x -> new UserDTO(x));
    }
}
