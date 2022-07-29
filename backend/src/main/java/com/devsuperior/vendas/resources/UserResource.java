package com.devsuperior.vendas.resources;

import com.devsuperior.vendas.dto.TeamDTO;
import com.devsuperior.vendas.dto.UserDTO;
import com.devsuperior.vendas.services.TeamService;
import com.devsuperior.vendas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> list = service.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }
}
