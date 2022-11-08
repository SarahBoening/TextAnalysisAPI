package de.rub.iaw.service.db;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.rub.iaw.domain.ContainerAppQuestion;
import de.rub.iaw.domain.ContainerAppScribble;
import de.rub.iaw.domain.User;
import de.rub.iaw.repository.ContainerAppScribbleRepository;

@Service
public class ContainerAppScribbleServiceImpl implements ContainerAppScribbleService{

	@Resource
	private ContainerAppScribbleRepository appScribbleRepository;

	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerAppScribble create(ContainerAppScribble container) {
		return appScribbleRepository.save(container);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerAppScribble update(ContainerAppScribble container) {
		return appScribbleRepository.save(container);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerAppScribble findById(Long id) {
		ContainerAppScribble cas = appScribbleRepository.findOne(id);
		return cas;
	}
	
	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<ContainerAppScribble> findByTimestampIsGreaterThan(Long timestamp) {
		return appScribbleRepository.findByLastModificationTimestampGreaterThanAndStatusLessThan(timestamp, 3);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<ContainerAppScribble> findAll() {
		return appScribbleRepository.findAll();
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<ContainerAppScribble> findByUser(User user) {
		return appScribbleRepository.findByUser(user);
	}

	@Override
	public Iterable<ContainerAppScribble> findByStatus(int status) {
		return appScribbleRepository.findByStatus(status);
	}

	@Override
	public Iterable<ContainerAppScribble> findByQuestion(
			ContainerAppQuestion question) {
		return appScribbleRepository.findByQuestion(question);
	}
	
}
