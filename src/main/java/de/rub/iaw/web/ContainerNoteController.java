package de.rub.iaw.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rub.iaw.domain.ContainerComment;
import de.rub.iaw.domain.ContainerNote;
import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.User;
import de.rub.iaw.exception.BadParameterException;
import de.rub.iaw.exception.UnauthorizedException;
import de.rub.iaw.service.db.ContainerCommentService;
import de.rub.iaw.service.db.ContainerNoteService;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.UserService;
import de.rub.iaw.util.InputValidator;

@RestController
@RequestMapping(value = "/containernote")
public class ContainerNoteController {
	
	@Autowired
	private ContainerNoteService containerNoteService;
	
	@Autowired
	private ContainerCommentService containerCommentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LogeventService logeventService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(@ModelAttribute ContainerNote param) {
		
		checkInput(param);
		
		param.setTimestamp(new Date());
		param.setNumberOfChanges(0);
		
		User currentUser = userService.getCurrentUser();
		param.setUser(currentUser);
		
		System.out.println(param.toString());
		
		// containerNoteService returns the saved containerNote, WITH ID, thus save the value, get the id and use it for logging!
		ContainerNote savedContainer = containerNoteService.create(param);
			
		Logevent logevent = new Logevent("NEW_NOTE_CREATED");
		logevent.setContainerNoteId(savedContainer.getId());
		logeventService.create(logevent);
	}
	
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(@ModelAttribute ContainerNote param){
		
		checkInput(param);
		
		// get old note from db
		ContainerNote currentNote = containerNoteService.findById(param.getId());
		
		// check if current user is note owner
		User currentUser = userService.getCurrentUser();
		if(!currentUser.getId().equals(currentNote.getUser().getId())) {
			throw new UnauthorizedException("ERR_UNAUTHORIZED_USER_NOT_OWNER");
		}
		
		// update new values
		currentNote.setTitle(param.getTitle());
		currentNote.setContent(param.getContent());
		
		// track number of changes and last edit timestamp
		int oldNumberOfChanges = currentNote.getNumberOfChanges();
		currentNote.setNumberOfChanges(oldNumberOfChanges+1);
		currentNote.setLastEdit(new Date());
		
		// save
		currentNote = containerNoteService.update(currentNote);
		
		// log
		Logevent logevent = new Logevent("NOTE_UPDATED");
		logevent.setContainerNoteId(currentNote.getId());
		logeventService.create(logevent);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ContainerNote findById(@PathVariable Long id){
		return containerNoteService.findById(id);
	}
	
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public ModelAndView findById(@PathVariable Long id){
//		ModelAndView mav = new ModelAndView();
//		mav.addObject(containerNoteService.findById(id));
//		return mav;
//	}
	
	@RequestMapping(value = "/{id}/getcomments", method = RequestMethod.GET)
	public ContainerNote getComments(@PathVariable Long id){
		
		// first get Note
		ContainerNote note = containerNoteService.findById(id);
		
		// Then get all comments attached to comment-dummy
		ContainerComment comments = containerCommentService.findAllCommentsByContainerId(id);
		
		// transfer comments
		note.setComments(comments.getChilds());
		
		// return stuff
		return note;
	}
	
	public static Boolean checkInput(ContainerNote param) {
		
		if(!InputValidator.ValidateString(param.getTitle())) {
			throw new BadParameterException("ERR_BAD_PARAM_NOTE_TITLE");
		}
		
		if(!InputValidator.ValidateString(param.getContent())) {
			throw new BadParameterException("ERR_BAD_PARAM_NOTE_CONTENT");
		}
		
		return true;
	}

}
