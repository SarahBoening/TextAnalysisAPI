package de.rub.iaw.repository;

/**
 *Repository for the Liwc table
 *Creates queries
 * @author Sarah Böning
 **/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.rub.iaw.domain.Liwc;
import de.rub.iaw.domain.TextCodeProb;

@Repository
public interface LiwcRepository extends JpaRepository<Liwc, Long> {

	Liwc findByTextCodeProb(TextCodeProb textCodeProb);
}
