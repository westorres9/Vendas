package com.devsuperior.vendas.repositories;

import com.devsuperior.vendas.entities.Role;
import com.devsuperior.vendas.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
