package de.rub.iaw.service.db;

/**
 *Service interface for the Liwc table
 *Part of the JPA Service Facade pattern
 * @author Sarah Böning
 **/

import de.rub.iaw.domain.Liwc;
import de.rub.iaw.domain.TextCodeProb;

public interface LiwcService {

	public Liwc create(Liwc liwc);

	public Liwc update(Liwc liwc);

	public Liwc findById(Long id);

	public Liwc findByTextCodeProb(TextCodeProb textCodeProb);

	public boolean existsByForeignKey(Long postID);
}
