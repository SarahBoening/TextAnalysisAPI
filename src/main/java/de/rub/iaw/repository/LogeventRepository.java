package de.rub.iaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.Logevent;

@Repository
public interface LogeventRepository extends JpaRepository<Logevent, Long> {

}
