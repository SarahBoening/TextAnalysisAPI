package de.rub.iaw.service.db;

/**
 *Service interface implementation for the Liwc table
 * @author Sarah Böning
 **/

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import de.rub.iaw.domain.Liwc;
import de.rub.iaw.domain.TextCodeProb;
import de.rub.iaw.repository.LiwcRepository;

@Service
public class LiwcServiceImpl implements LiwcService {

	@Resource
	LiwcRepository liwcRepository;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Liwc create(Liwc liwc) {
		return liwcRepository.save(liwc);
	}

	@Override
	public Liwc update(Liwc liwc) {
		return liwcRepository.save(liwc);
	}

	@Override
	public Liwc findById(Long id) {
		return liwcRepository.findOne(id);
	}

	@Override
	public Liwc findByTextCodeProb(TextCodeProb textCodeProb) {
		return liwcRepository.findByTextCodeProb(textCodeProb);
	}

	@Override
	public boolean existsByForeignKey(Long postID) {
		return liwcRepository.exists(postID);
	}
}
