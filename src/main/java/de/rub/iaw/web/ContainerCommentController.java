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
import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.User;
import de.rub.iaw.exception.BadParameterException;
import de.rub.iaw.exception.UnauthorizedException;
import de.rub.iaw.service.db.ContainerCommentService;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.UserService;
import de.rub.iaw.util.InputValidator;

@RestController
@RequestMapping(value = "/comment")
public class ContainerCommentController {

	 @Autowired
	 private ContainerCommentService containerCommentService;
	 
	 @Autowired
	 private LogeventService logeventService;
	 
	 @Autowired
	 private UserService userService;
	 
	 @RequestMapping(method = RequestMethod.POST)
	 @ResponseBody
	 public void create(@ModelAttribute ContainerComment param) {
		 
		 // validate input
		 checkInput(param);
		 
		 // TODO check that parentID is valid
		 
		 // set missing values
		 param.setTimestamp(new Date());
		 param.setNumberOfChanges(0);
		 param.setUser(userService.getCurrentUser());
		 
		 // save
		 ContainerComment savedContainer = containerCommentService.create(param);
		 
		 // log
		 Logevent logevent = new Logevent("NEW_COMMENT_CREATED");
		 logevent.setContainerCommentId(savedContainer.getId());
		 logeventService.create(logevent);
	 }
	 
	 @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	 @ResponseBody
	 public void update(@ModelAttribute ContainerComment param) {
		 
		 checkInput(param);
		 
		 // get old comment from db
		 ContainerComment old = containerCommentService.findById(param.getId());
		 
		 // Check whether current user is owner
		 User currentUser = userService.getCurrentUser();
		 if(!currentUser.getId().equals(old.getUser().getId())) {
			 throw new UnauthorizedException("ERR_UNAUTHORIZED_USER_NOT_OWNER");
		 }
		 
		 // update values
		 old.setContent(param.getContent());
		 old.setLastEdit(new Date());
		 old.setNumberOfChanges(old.getNumberOfChanges() + 1);
		 
		 // save
		 containerCommentService.update(old);
		 
		 // log
		 Logevent logevent = new Logevent("COMMENT_UPDATED");
		 logevent.setContainerCommentId(old.getId());
		 logeventService.create(logevent);
	 }
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 @ResponseBody
	 public ContainerComment getById(@PathVariable Long id) {
		 return containerCommentService.findById(id);
	 }
	
	 public static Boolean checkInput(ContainerComment param) {
		 if(!InputValidator.ValidateString(param.getContent())) {
			throw new BadParameterException("ERR_BAD_PARAM_COMMENT_CONTENT");
		 }
		 
		 if(param.getParentId().equals(null)) {
			throw new BadParameterException("ERR_BAD_PARAM_COMMENT_PARENTID"); 
		 }
		 		 
		 return true;
	 }
	 
}
