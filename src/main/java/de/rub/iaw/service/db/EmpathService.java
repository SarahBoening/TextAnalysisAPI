package de.rub.iaw.service.db;

import de.rub.iaw.domain.Empath;
import de.rub.iaw.domain.TextCodeProb;

/**
 * Service interface for the Empath table Part of the JPA Service Facade pattern
 * 
 * @author Sarah Böning
 **/

public interface EmpathService {

	public Empath create(Empath empath);

	public Empath update(Empath empath);

	public Empath findById(Long id);

	public Empath findByTextCodeProb(TextCodeProb textCodeProb);

	public boolean existsByForeignKey(Long foreignKey);
}
