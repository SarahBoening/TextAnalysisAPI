package de.rub.iaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	Group findByNameIgnoreCase(String name);

}
