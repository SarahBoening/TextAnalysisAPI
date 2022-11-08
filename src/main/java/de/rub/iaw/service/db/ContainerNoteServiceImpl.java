package de.rub.iaw.service.db;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.rub.iaw.domain.ContainerNote;
import de.rub.iaw.repository.ContainerCommentRepository;
import de.rub.iaw.repository.ContainerNoteRepository;

@Service
public class ContainerNoteServiceImpl implements ContainerNoteService {

	@Resource
	private ContainerNoteRepository containerNoteRepository;
	
	@Resource
	private ContainerCommentRepository containerCommentRepository;
	
	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerNote create(ContainerNote containerNote) {
		return containerNoteRepository.save(containerNote);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerNote update(ContainerNote containerNote) {
		return containerNoteRepository.save(containerNote);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public ContainerNote findById(Long id) {
		return containerNoteRepository.findOne(id); 
	}

}
