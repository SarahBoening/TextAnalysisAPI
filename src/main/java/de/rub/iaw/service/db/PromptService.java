package de.rub.iaw.service.db;

import java.util.List;

import de.rub.iaw.domain.Prompt;

public interface PromptService {

	public Prompt create(Prompt prompt);

	public Prompt update(Prompt prompt);

	public Prompt findById(Long id);

	public List<Prompt> findAll();

	public List<Prompt> findByGoal(String goal);

	public List<Prompt> findByLocation(String location);

	public List<Prompt> findByTarget(String target);

	public List<Prompt> findBySetting(String setting);

	public List<Prompt> findByCategory(String category);

	public List<Prompt> findByGoalAndLocation(String goal, String location);

	public List<Prompt> findByGoalAndLocationAndTarget(String goal, String location, String target);

	public List<Prompt> findByGoalAndLocationAndTargetAndInstructionalCharacter(String goal, String location,
			String target, String instructional_character);

	public List<Prompt> findByGoalAndLocationAndTargetAndCategory(String goal, String location, String target,
			String category);

}
