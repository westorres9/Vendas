package com.devsuperior.salesmanager.resources;

import com.devsuperior.salesmanager.DTO.TeamDTO;
import com.devsuperior.salesmanager.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/teams")
public class TeamResource {

    @Autowired
    private TeamService service;

    @GetMapping
    public ResponseEntity<Page<TeamDTO>> findAllPaged(Pageable pageable) {
        Page<TeamDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable  Long id) {
        TeamDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> insert(@RequestBody TeamDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TeamDTO> update(@PathVariable Long id, @RequestBody TeamDTO dto) {
        TeamDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
