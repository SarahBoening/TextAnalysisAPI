package de.rub.iaw.web;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rub.iaw.domain.ContainerAppScribble;
import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.User;
import de.rub.iaw.exception.UnauthorizedException;
import de.rub.iaw.service.db.ContainerAppQuestionService;
import de.rub.iaw.service.db.ContainerAppScribbleService;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.UserService;

@RestController
@RequestMapping(value = "/containerappscribble")
public class ContainerAppScribbleController {
	
	@Autowired
	private ContainerAppScribbleService containerAppScribbleService;
	
	@Autowired
	private ContainerAppQuestionService containerAppQuestionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LogeventService logeventService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Long create(
			@RequestParam(value="title") String title,
			@RequestParam(value="content") String content,
			@RequestParam(value="correspondingInputType") String inputType,
			@RequestParam(value="status", defaultValue="0") int status,
			@RequestParam(value="questionId") long questionId
			) {
		
		ContainerAppScribble savedContainer = new ContainerAppScribble();
		
		User currentUser = userService.getCurrentUser();
		Long time = new Date().getTime();
		
		savedContainer.setUser(currentUser);
		savedContainer.setQuestion(null);
		savedContainer.setTitle(title);
		savedContainer.setCreatinTimestamp(time);
		savedContainer.setLastModificationTimestamp(time);
		savedContainer.setStatus(status);
		savedContainer.setContent(content);
		savedContainer.setCorrespondingInputType(inputType);
		if(questionId != -1) savedContainer.setQuestion(containerAppQuestionService.findById(questionId));
		
		savedContainer = containerAppScribbleService.create(savedContainer);
		
		Logevent logevent = new Logevent("NEW_APP_SCRIBBLE_CREATED");
		logevent.setContainerAppScribbleId(savedContainer.getBackendDatabaseID());
		logeventService.create(logevent);
		
		return savedContainer.getBackendDatabaseID();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(
			@RequestParam(value="id") Long id,
			@RequestParam(value="title") String title,
			@RequestParam(value="content") String content,
			@RequestParam(value="correspondingInputType") String inputType,
			@RequestParam(value="status", defaultValue="0") int status,
			@RequestParam(value="questionId") long questionId
			){
		
		// get old note from db
		ContainerAppScribble containerAppScribble = containerAppScribbleService.findById(id);
		
		// check if current user is note owner
		User currentUser = userService.getCurrentUser();
		if(!currentUser.getId().equals(containerAppScribble.getUser().getId())) {
			throw new UnauthorizedException("ERR_UNAUTHORIZED_USER_NOT_OWNER");
		}
		
		containerAppScribble.setLastModificationTimestamp(new Date().getTime());
		containerAppScribble.setTitle(title);
		containerAppScribble.setContent(content);
		containerAppScribble.setCorrespondingInputType(inputType);
		containerAppScribble.setStatus(status);
		containerAppScribble.setQuestion(containerAppQuestionService.findById(questionId)); 
		
		containerAppScribble = containerAppScribbleService.update(containerAppScribble);
		
		Logevent logevent = new Logevent("UPDATA_APP_SCRIBBLE");
		logevent.setContainerAppScribbleId(containerAppScribble.getBackendDatabaseID());
		logeventService.create(logevent);
	}
	
	@RequestMapping(value = "/pull/{timestamp}", method = RequestMethod.GET)
	public ArrayList<String> getUpdates(@PathVariable Long timestamp){		
		Iterable<ContainerAppScribble> iter = containerAppScribbleService.findByTimestampIsGreaterThan(timestamp);
		ArrayList<String> list = new ArrayList<String>();
		for(ContainerAppScribble s : iter){
			list.add(s.toJson());
		}
		logeventService.create(new Logevent("USER_PULL_APP_SCRIBBLE"));
		return list;
	}
	
}
