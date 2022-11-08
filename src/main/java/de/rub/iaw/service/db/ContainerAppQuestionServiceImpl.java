package de.rub.iaw.service.db;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.rub.iaw.domain.ContainerAppQuestion;
import de.rub.iaw.domain.Group;
import de.rub.iaw.repository.ContainerAppQuestionRepository;

@Service
public class ContainerAppQuestionServiceImpl implements ContainerAppQuestionService {

	@Resource
	private ContainerAppQuestionRepository appQuestionRepository;
	
	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerAppQuestion create(ContainerAppQuestion container) {
		return appQuestionRepository.save(container);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerAppQuestion update(ContainerAppQuestion container) {
		return appQuestionRepository.save(container);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerAppQuestion findById(Long id) {
		return appQuestionRepository.findOne(id);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<ContainerAppQuestion> findByGroup(Group group) {
		return appQuestionRepository.findByGroup(group);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<ContainerAppQuestion> findAll() {
		return appQuestionRepository.findAll();
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<ContainerAppQuestion> findByTriggerTimestampLessThanEqual(Long timestamp) {
		return appQuestionRepository.findByTriggerTimestampLessThanEqualAndExpiryTimestampGreaterThan(timestamp, timestamp);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<ContainerAppQuestion> findByTriggerTimestampLessThanEqualAndGroup(Long timestamp, Group group) {
		return appQuestionRepository.findByTriggerTimestampLessThanEqualAndExpiryTimestampGreaterThanAndGroup(timestamp, timestamp, group);
	}

}
