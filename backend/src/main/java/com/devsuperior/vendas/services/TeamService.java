package com.devsuperior.vendas.services;

import com.devsuperior.vendas.dto.TeamDTO;
import com.devsuperior.vendas.entities.Team;
import com.devsuperior.vendas.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository repository;

    @Transactional(readOnly = true)
    public Page<TeamDTO> findAll(Pageable pageable) {
        Page<Team> page = repository.findAll(pageable);
        return page.map(x -> new TeamDTO(x));
    }
}
