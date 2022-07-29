package com.devsuperior.vendas.resources;

import com.devsuperior.vendas.dto.SaleDTO;
import com.devsuperior.vendas.dto.TeamDTO;
import com.devsuperior.vendas.services.SaleService;
import com.devsuperior.vendas.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/teams")
public class TeamResource {

    @Autowired
    private TeamService service;

    @GetMapping
    public ResponseEntity<Page<TeamDTO>> findAll(Pageable pageable) {
        Page<TeamDTO> list = service.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }
}
