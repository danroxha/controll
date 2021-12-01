package com.start.controll.repositories;

import com.start.controll.entities.Starter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarterRepository extends JpaRepository<Starter, Long> {
  Optional<Starter> findByCode(String code);
}
