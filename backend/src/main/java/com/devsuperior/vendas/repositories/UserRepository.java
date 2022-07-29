package com.devsuperior.vendas.repositories;

import com.devsuperior.vendas.entities.Sale;
import com.devsuperior.vendas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
