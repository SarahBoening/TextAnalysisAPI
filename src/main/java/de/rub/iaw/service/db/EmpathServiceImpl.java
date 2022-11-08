package de.rub.iaw.service.db;

import javax.annotation.Resource;

/**
 *Service interface implementation for the Empath table
 * @author Sarah Böning
 **/

import org.springframework.stereotype.Service;

import de.rub.iaw.domain.Empath;
import de.rub.iaw.domain.TextCodeProb;
import de.rub.iaw.repository.EmpathRepository;

@Service
public class EmpathServiceImpl implements EmpathService {
	@Resource
	EmpathRepository empathRepository;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Empath create(Empath empath) {
		return empathRepository.save(empath);
	}

	@Override
	public Empath update(Empath empath) {
		return empathRepository.save(empath);
	}

	@Override
	public Empath findById(Long id) {
		return empathRepository.findOne(id);
	}

	@Override
	public Empath findByTextCodeProb(TextCodeProb textCodeProb) {
		return empathRepository.findByTextCodeProb(textCodeProb);
	}

	@Override
	public boolean existsByForeignKey(Long foreignKey) {
		return empathRepository.exists(foreignKey);
	}
}