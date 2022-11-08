package de.rub.iaw.service.db;

import de.rub.iaw.domain.ContainerAppQuestion;
import de.rub.iaw.domain.ContainerAppScribble;
import de.rub.iaw.domain.User;

public interface ContainerAppScribbleService {

	public ContainerAppScribble create(ContainerAppScribble container);
	
	public ContainerAppScribble update(ContainerAppScribble container);
	
	public ContainerAppScribble findById(Long id);
	
	public Iterable<ContainerAppScribble> findByUser(User user);
	
	public Iterable<ContainerAppScribble> findByQuestion(ContainerAppQuestion question);
	
	public Iterable<ContainerAppScribble> findAll();
	
	public Iterable<ContainerAppScribble> findByStatus(int status);

	public Iterable<ContainerAppScribble> findByTimestampIsGreaterThan(Long timestamp);
	
}
