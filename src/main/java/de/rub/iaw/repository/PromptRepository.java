package de.rub.iaw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.Prompt;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {

	List<Prompt> findByGoalIgnoreCase(String goal);

	List<Prompt> findByLocationIgnoreCase(String location);

	List<Prompt> findByTargetIgnoreCase(String target);

	List<Prompt> findBySettingIgnoreCase(String setting);

	List<Prompt> findByCategoryIgnoreCase(String category);

	List<Prompt> findByGoalAndLocationIgnoreCase(String goal, String location);

	List<Prompt> findByGoalAndLocationAndTargetIgnoreCase(String goal, String location, String target);

	List<Prompt> findByGoalAndLocationAndTargetAndInstructionalCharacterIgnoreCase(String goal, String location,
			String target, String instructional_character);

	List<Prompt> findByGoalAndLocationAndTargetAndCategoryIgnoreCase(String goal, String location, String target,
			String category);

}