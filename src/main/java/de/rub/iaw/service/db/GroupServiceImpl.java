package de.rub.iaw.service.db;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.rub.iaw.domain.Group;
import de.rub.iaw.repository.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService {

	@Resource
	private GroupRepository groupRepository;

	@Override
	@Transactional("contentdbTransactionManager")
	public Group create(Group group) {
		return groupRepository.save(group);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Group update(Group group) {
		return groupRepository.save(group);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Group findById(Long id) {
		return groupRepository.findOne(id);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Group findByName(String name) {
		return groupRepository.findByNameIgnoreCase(name);
	}
}
