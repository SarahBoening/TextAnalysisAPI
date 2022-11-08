package de.rub.iaw.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.ContainerComment;

@Repository
public interface ContainerCommentRepository  extends JpaRepository<ContainerComment, Long> {

	Set<ContainerComment> findByParentId(Long parentID);

}
