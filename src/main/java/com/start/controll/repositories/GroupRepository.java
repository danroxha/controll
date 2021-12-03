package com.start.controll.repositories;

import com.start.controll.entities.GroupDaily;
import com.start.controll.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupDaily, Long> {

  List<GroupDaily> findByScrumMaster(User user);
  List<GroupDaily> findByScrumMaster(Sort sort, User user);
}
