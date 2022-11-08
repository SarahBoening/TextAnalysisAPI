package de.rub.iaw.service.db;

import de.rub.iaw.domain.ContainerComment;

public interface ContainerCommentService {

	public ContainerComment create(ContainerComment containerComment);
	
	public ContainerComment update(ContainerComment containerComment);
	
	public ContainerComment findById(Long id);

	public ContainerComment findAllCommentsByContainerId(Long id);
	
}
