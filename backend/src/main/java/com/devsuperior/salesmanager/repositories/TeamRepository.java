package com.devsuperior.salesmanager.repositories;

import com.devsuperior.salesmanager.entities.Team;
import com.devsuperior.salesmanager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByManager(User manager, Pageable pageable);
}
