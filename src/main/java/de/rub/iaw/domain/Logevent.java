package de.rub.iaw.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "logevent", catalog = "rotterdam")
public class Logevent implements java.io.Serializable {

	private static final long serialVersionUID = -2101286632956017259L;
	private Long id;
	private Date timestamp;
	private String description;
	private String sessionId; // user session
	private String instance; // system which send the query

	private User user;
	private Group group;

	private Long ContainerAppQuestionId;
	private Long ContainerAppScribbleId;
	private Long containerCommentId;
	private Long containerNoteId;
	private Long promptId;
	private Long textCodeProbId;
	private Long liwcId;
	private Long empathId;

	public Logevent() {
	}

	public Logevent(String description) {
		this.description = description;
	}

	public Logevent(User user, String description) {
		this.user = user;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP", nullable = false, length = 19)
	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = true)
	@JsonBackReference
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "GROUP_ID", nullable = true) // only set group when
													// reasonable
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "CONTAINERAPPQUESTION_ID")
	public Long getContainerAppQuestionId() {
		return ContainerAppQuestionId;
	}

	public void setContainerAppQuestionId(Long containerAppQuestionId) {
		ContainerAppQuestionId = containerAppQuestionId;
	}

	@Column(name = "CONTAINERAPPSCRIBBLE_ID")
	public Long getContainerAppScribbleId() {
		return ContainerAppScribbleId;
	}

	public void setContainerAppScribbleId(Long containerAppScribbleId) {
		ContainerAppScribbleId = containerAppScribbleId;
	}

	@Column(name = "CONTAINERCOMMENT_ID")
	public Long getContainerCommentId() {
		return containerCommentId;
	}

	public void setContainerCommentId(Long containerCommentId) {
		this.containerCommentId = containerCommentId;
	}

	@Column(name = "CONTAINERNOTE_ID")
	public Long getContainerNoteId() {
		return containerNoteId;
	}

	public void setContainerNoteId(Long containerNoteId) {
		this.containerNoteId = containerNoteId;
	}

	@Column(name = "PROMPT_ID", nullable = true)
	public Long getPromptId() {
		return this.promptId;
	}

	public void setPromptId(Long promptId) {
		this.promptId = promptId;
	}

	@Column(name = "SESSION_ID", nullable = true)
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "INSTANCE", nullable = true)
	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Column(name = "TEXTCODEPROB_ID", nullable = true)
	public Long getTextCodeProbId() {
		return textCodeProbId;
	}

	public void setTextCodeProbId(Long textCodeProbId) {
		this.textCodeProbId = textCodeProbId;
	}

	@Column(name = "LIWC_ID", nullable = true)
	public Long getLiwcId() {
		return liwcId;
	}

	public void setLiwcId(Long liwcId) {
		this.liwcId = liwcId;
	}

	@Column(name = "EMPATH_ID", nullable = true)
	public Long getEmpathId() {
		return empathId;
	}

	public void setEmpathId(Long empathId) {
		this.empathId = empathId;
	}

}
