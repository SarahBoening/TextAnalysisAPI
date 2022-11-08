package de.rub.iaw.service.db;

/**
 *Service interface for the TextCodeProb table
 *Part of the JPA Service Facade pattern
 * 
 * @author Sarah Böning
 **/

import de.rub.iaw.domain.TextCodeProb;

public interface TextCodeProbService {

	public TextCodeProb create(TextCodeProb textCodeProb);

	public TextCodeProb update(TextCodeProb textCodeProb);
	
	public TextCodeProb findById(Long id);

	public TextCodeProb findByPostID(Long postID);

	public boolean existsByPostID(Long postID);
}
