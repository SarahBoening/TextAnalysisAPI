package de.rub.iaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.ContainerAppQuestion;
import de.rub.iaw.domain.Group;

@Repository
public interface ContainerAppQuestionRepository extends JpaRepository<ContainerAppQuestion, Long> {
	
	Iterable<ContainerAppQuestion> findByGroup(Group group);
	
	Iterable<ContainerAppQuestion> findByTriggerTimestampLessThanEqualAndExpiryTimestampGreaterThan(Long trigger,Long expiry);
	
	Iterable<ContainerAppQuestion> findByTriggerTimestampLessThanEqualAndExpiryTimestampGreaterThanAndGroup(Long trigger, Long expiry, Group group);

}
