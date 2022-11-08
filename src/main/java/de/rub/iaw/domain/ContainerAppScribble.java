package de.rub.iaw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "container_app_scribble", catalog = "rotterdam")
public class ContainerAppScribble implements java.io.Serializable {
	
	private static final long serialVersionUID = 6523386845133719621L;
	private Long backendDatabaseID;
	private Long creatinTimestamp; //creatinDate
	private Long lastModificationTimestamp;
	private User user;
	private ContainerAppQuestion question;
	private String title;
	private String content;
	private String correspondingInputType;
	private int status;
	
	public ContainerAppScribble() {
		super();
		this.question = null;
	}
	
	public ContainerAppScribble(Long backendDatabaseID, Long creatinTimestamp,
			Long lastModificationTimestamp, User user,
			ContainerAppQuestion question, String title, String content,
			String correspondingInputType, int status) {
		super();
		this.backendDatabaseID = backendDatabaseID;
		this.creatinTimestamp = creatinTimestamp;
		this.lastModificationTimestamp = lastModificationTimestamp;
		this.user = user;
		this.question = question;
		this.title = title;
		this.content = content;
		this.correspondingInputType = correspondingInputType;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getBackendDatabaseID() {
		return backendDatabaseID;
	}

	public void setBackendDatabaseID(Long backendDatabaseID) {
		this.backendDatabaseID = backendDatabaseID;
	}

	@Column(name = "CREATINGTIMESTAMP", nullable = false, length = 19)
	public Long getCreatinTimestamp() {
		return creatinTimestamp;
	}
	
	public void setCreatinTimestamp(Long creatinTimestamp) {
		this.creatinTimestamp = creatinTimestamp;
	}
	
	@Column(name = "MODIFICATIONTIMESTAMP", nullable = false, length = 19)
	public Long getLastModificationTimestamp() {
		return lastModificationTimestamp;
	}

	public void setLastModificationTimestamp(Long lastModificationTimestamp) {
		this.lastModificationTimestamp = lastModificationTimestamp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID", nullable = true)
	public ContainerAppQuestion getQuestion() {
		return question;
	}

	public void setQuestion(ContainerAppQuestion question) {
		this.question = question;
	}

	@Column(name = "TITLE", nullable = false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "CONTENT", nullable = false)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "INPUT_TYPE", nullable = false)
	public String getCorrespondingInputType() {
		return correspondingInputType;
	}

	public void setCorrespondingInputType(String correspondingInputType) {
		this.correspondingInputType = correspondingInputType;
	}
	
	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ContainerAppScribble [backendDatabaseID=" + backendDatabaseID
				+ ", creatinTimestamp=" + creatinTimestamp
				+ ", lastModificationTimestamp=" + lastModificationTimestamp 
				+ ", question=" + question + ", title="
				+ title + ", content=" + content + ", correspondingInputType="
				+ correspondingInputType + ", status=" + status + "]";
	}
	
	public String toJson(){
		String questionId = (question != null) ? question.getBackendDatabaseID().toString() : null ;
		String json =  "{ " + "\"backendDatabaseID\" : \"" + backendDatabaseID 
				+ "\", \"title\" : \"" + title
				+ "\", \"creatinTimestamp\" : \"" + creatinTimestamp
				+ "\", \"lastModificationTimestamp\" : \"" + lastModificationTimestamp 
				+ "\", \"questionId\" : \"" + questionId 
				+ "\", \"content\" : " + content 
				+ ", \"correspondingInputType\" : \"" + correspondingInputType 
				+ "\", \"status\": \"" + status + "\" }";
		return json;
	}
	
}
