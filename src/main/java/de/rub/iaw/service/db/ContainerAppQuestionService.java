package de.rub.iaw.service.db;

import de.rub.iaw.domain.ContainerAppQuestion;
import de.rub.iaw.domain.Group;

public interface ContainerAppQuestionService {
	
	public ContainerAppQuestion create(ContainerAppQuestion container);
	
	public ContainerAppQuestion update(ContainerAppQuestion container);
	
	public ContainerAppQuestion findById(Long id);
	
	public Iterable<ContainerAppQuestion> findByGroup(Group group);
	
	public Iterable<ContainerAppQuestion> findAll();

	public Iterable<ContainerAppQuestion> findByTriggerTimestampLessThanEqual(Long timestamp);
	
	public Iterable<ContainerAppQuestion> findByTriggerTimestampLessThanEqualAndGroup(Long timestamp, Group group);
	
}
