package de.rub.iaw.service.db;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.rub.iaw.domain.Prompt;
import de.rub.iaw.repository.PromptRepository;

@Service
public class PromptServiceImpl implements PromptService {

	@Resource
	PromptRepository promptRepository;

	@Override
	public Prompt create(Prompt prompt) {
		return promptRepository.save(prompt);
	}

	@Override
	public Prompt update(Prompt prompt) {
		return promptRepository.save(prompt);
	}

	@Override
	public Prompt findById(Long id) {
		return promptRepository.findOne(id);
	}

	@Override
	public List<Prompt> findAll() {
		return promptRepository.findAll();
	}

	@Override
	public List<Prompt> findByGoal(String goal) {
		return promptRepository.findByGoalIgnoreCase(goal);
	}

	@Override
	public List<Prompt> findByLocation(String location) {
		return promptRepository.findByLocationIgnoreCase(location);
	}

	@Override
	public List<Prompt> findByTarget(String target) {
		return promptRepository.findByTargetIgnoreCase(target);
	}

	@Override
	public List<Prompt> findBySetting(String setting) {
		return promptRepository.findBySettingIgnoreCase(setting);
	}

	@Override
	public List<Prompt> findByCategory(String category) {
		return promptRepository.findByCategoryIgnoreCase(category);
	}

	@Override
	public List<Prompt> findByGoalAndLocation(String goal, String location) {
		return promptRepository.findByGoalAndLocationIgnoreCase(goal, location);
	}

	@Override
	public List<Prompt> findByGoalAndLocationAndTarget(String goal, String location, String target) {
		return promptRepository.findByGoalAndLocationAndTargetIgnoreCase(goal, location, target);
	}

	@Override
	public List<Prompt> findByGoalAndLocationAndTargetAndInstructionalCharacter(String goal, String location,
			String target, String instructional_character) {
		return promptRepository.findByGoalAndLocationAndTargetAndInstructionalCharacterIgnoreCase(goal, location,
				target, instructional_character);
	}

	@Override
	public List<Prompt> findByGoalAndLocationAndTargetAndCategory(String goal, String location, String target,
			String category) {
		return promptRepository.findByGoalAndLocationAndTargetAndCategoryIgnoreCase(goal, location, target, category);
	}

}