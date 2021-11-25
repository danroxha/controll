package com.start.controll.repositories;

import com.start.controll.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecnologyRepository extends JpaRepository<Technology, Long> {
}
