package de.rub.iaw.service.db;

import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import de.rub.iaw.domain.ContainerComment;
import de.rub.iaw.repository.ContainerCommentRepository;

@Service
public class ContainerCommentServiceImpl implements ContainerCommentService {

	@Resource
	private ContainerCommentRepository containerCommentRepository;
	
	@Override
	@Transactional
	public ContainerComment create(ContainerComment containerComment) {
		return containerCommentRepository.save(containerComment);
	}

	@Override
	@Transactional
	public ContainerComment update(ContainerComment containerComment) {
		return containerCommentRepository.save(containerComment);
	}

	@Override
	@Transactional
	public ContainerComment findById(Long id) {
		return containerCommentRepository.findOne(id);
	}

	@Override
	public ContainerComment findAllCommentsByContainerId(Long id) {
		
		// create new dummy object and attach children later on
		ContainerComment root = new ContainerComment();
		root.setId(id);
		
		// recursively load all child comments
		return getChildComments(root);
	}
	
	private ContainerComment getChildComments(ContainerComment cc){
		
		Set<ContainerComment> fetched = containerCommentRepository.findByParentId(cc.getId());
		
		if(fetched.isEmpty()) {
			return cc;
		} else {
			cc.setChilds(fetched);
			for (ContainerComment containerComment : cc.getChilds()) {
				getChildComments(containerComment);
			}
			return cc;
		}
	}
	
}
