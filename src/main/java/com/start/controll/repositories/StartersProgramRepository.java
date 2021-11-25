package com.start.controll.repositories;

import com.start.controll.entities.StartersProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartersProgramRepository extends JpaRepository<StartersProgram, Long> {
}
