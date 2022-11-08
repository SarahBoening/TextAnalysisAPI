package de.rub.iaw.service.db;

/**
 *Service interface implementation for the TextCodeProb table
 * @author Sarah Böning
 **/

import de.rub.iaw.domain.TextCodeProb;
import de.rub.iaw.repository.TextCodeProbRepository;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TextCodeProbServiceImpl implements TextCodeProbService {

	@Resource
	TextCodeProbRepository textCodeProbRepository;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public TextCodeProb create(TextCodeProb textCodeProb) {
		return textCodeProbRepository.save(textCodeProb);
	}

	@Override
	public TextCodeProb update(TextCodeProb textCodeProb) {
		return textCodeProbRepository.save(textCodeProb);
	}

	@Override
	public TextCodeProb findById(Long id){
		return textCodeProbRepository.findOne(id);
	}
	@Override
	public TextCodeProb findByPostID(Long postID) {
		return textCodeProbRepository.findByPostID(postID);
	}

	@Override
	public boolean existsByPostID(Long postID) {
		return textCodeProbRepository.existsByPostID(postID);
	}
}