package de.rub.iaw.web;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rub.iaw.domain.ContainerAppQuestion;
import de.rub.iaw.domain.Group;
import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.User;
import de.rub.iaw.service.db.ContainerAppQuestionService;
import de.rub.iaw.service.db.GroupService;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.UserService;

@RestController
@RequestMapping(value = "/containerappquestion")
public class ContainerAppQuestionController {
	
	@Autowired
	private ContainerAppQuestionService containerAppQuestionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private LogeventService logeventService;

	@RequestMapping(value = "/pull", method = RequestMethod.GET)
	public ArrayList<String> getUpdates(){	
		
		ArrayList<String> list = new ArrayList<String>();
		User currentUser = userService.getCurrentUser();
		Long timestamp = new Date().getTime();
		
		Iterable<Group> groups = currentUser.getGroups();
		
		for(Group group : groups) {
			Iterable<ContainerAppQuestion> iter = containerAppQuestionService.findByTriggerTimestampLessThanEqualAndGroup(timestamp, group);
			for(ContainerAppQuestion s : iter){
				list.add(s.toJson());
			}	
		}
		
		logeventService.create(new Logevent("USER_PULL_QUESTIONS"));
		
		return list;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Long create(
			@RequestParam(value="title") String title,
			@RequestParam(value="triggerTimestamp") long triggerTimestamp,
			@RequestParam(value="expiryTimestamp") long expiryTimestamp,
			@RequestParam(value="groupeId") long groupeId,
			@RequestParam(value="question") String question
			) {
		
		ContainerAppQuestion savedContainer = new ContainerAppQuestion();
		
		Long time = new Date().getTime();
		Group group = groupService.findById(groupeId);
		
		savedContainer.setTriggerTimestamp(time);
		savedContainer.setExpiryTimestamp(time+1000);
		savedContainer.setTitle(title);
		savedContainer.setQuestion(question);
		savedContainer.setGroup(group);
		
		savedContainer = containerAppQuestionService.create(savedContainer);
		
		Logevent logevent = new Logevent("NEW_APP_QUESTION_CREATED");
		logevent.setContainerAppQuestionId(savedContainer.getBackendDatabaseID());
		logeventService.create(logevent);
		
		return savedContainer.getBackendDatabaseID();
	}
	
}
