package com.start.controll.repositories;

import com.start.controll.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByEmail(String email);

  @Query("SELECT u FROM User u WHERE roles LIKE '%SCRUM_MASTER%'")
  List<User> findAllScrumMasterUser();
}
