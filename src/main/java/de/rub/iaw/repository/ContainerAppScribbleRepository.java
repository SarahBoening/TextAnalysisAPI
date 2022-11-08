package de.rub.iaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.ContainerAppQuestion;
import de.rub.iaw.domain.ContainerAppScribble;
import de.rub.iaw.domain.User;

@Repository
public interface ContainerAppScribbleRepository extends JpaRepository<ContainerAppScribble, Long>{
	
	Iterable<ContainerAppScribble> findByQuestion(ContainerAppQuestion question);

	Iterable<ContainerAppScribble> findByUser(User user);

	Iterable<ContainerAppScribble> findByLastModificationTimestampGreaterThanAndStatusLessThan(Long lastModificationTimestamp, int Status);
	
	Iterable<ContainerAppScribble> findByStatus(int status);
}
