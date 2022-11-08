package de.rub.iaw.service.db;

import de.rub.iaw.domain.Group;

public interface GroupService {

	public Group create(Group group);

	public Group update(Group group);

	// We don't want to delete :)

	public Group findById(Long id);

	public Group findByName(String name);

}
