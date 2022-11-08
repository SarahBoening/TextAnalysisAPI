package de.rub.iaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import de.rub.iaw.domain.TextCodeProb;

/**
 * Repository for the TextCodeProb table
 * Creates queries
 * 
 * @author Sarah Böning
 **/

@Repository
public interface TextCodeProbRepository extends JpaRepository<TextCodeProb, Long> {
	
	TextCodeProb findByPostID(Long postID);
	
	@Query("SELECT COUNT(postID) > 0 from TextCodeProb t where t.postID = :postId")
	boolean existsByPostID(@Param("postId") Long postID);
}