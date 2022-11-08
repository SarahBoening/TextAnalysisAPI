package de.rub.iaw.service.db;

import de.rub.iaw.domain.ContainerNote;

public interface ContainerNoteService {

	public ContainerNote create(ContainerNote containerNote);
	
	public ContainerNote update(ContainerNote containerNote);
	
	public ContainerNote findById(Long id);
		
}
