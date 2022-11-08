package de.rub.iaw.repository;

/**
 *Repository for the Empath table
 *Creates queries
 * @author Sarah Böning
 **/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.rub.iaw.domain.Empath;
import de.rub.iaw.domain.TextCodeProb;

@Repository
public interface EmpathRepository extends JpaRepository<Empath, Long> {
	
	Empath findByTextCodeProb(TextCodeProb textCodeProb);
}