package de.rub.iaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.ContainerNote;

@Repository
public interface ContainerNoteRepository extends JpaRepository<ContainerNote, Long> {

}