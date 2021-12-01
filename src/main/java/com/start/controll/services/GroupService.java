package com.start.controll.services;

import com.start.controll.entities.GroupDaily;
import com.start.controll.entities.RegisterDaily;
import com.start.controll.entities.User;
import com.start.controll.repositories.GroupRepository;
import com.start.controll.repositories.RegisterDailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private RegisterDailyRepository registerDailyRepository;

  public Optional<GroupDaily> createGroup(GroupDaily group) {
    return Optional.ofNullable(groupRepository.save(group));
  }

  public List<GroupDaily> findAllGroups() {
    return groupRepository.findAll(Sort.by("lastModifiedDate").descending());
  }


  public List<GroupDaily> findByScrumMaster(User user) {
    return groupRepository.findByScrumMaster(Sort.by("lastModifiedDate").descending(), user);
  }

  public Optional<GroupDaily> findDailyGroupById(Long id) {
    return groupRepository.findById(id);
  }

  public Optional<RegisterDaily> registerDaily(RegisterDaily register) {
    return Optional.ofNullable(registerDailyRepository.save(register));
  }

  public Optional<GroupDaily> deleteDailyGroupById(Long id) {
    var group = groupRepository.findById(id);

    if(group.isEmpty())
      return Optional.empty();

    groupRepository.deleteById(id);

    return group;
  }
}
