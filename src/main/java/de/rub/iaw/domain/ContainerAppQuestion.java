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
@Table(name = "container_app_question", catalog = "rotterdam")
public class ContainerAppQuestion implements java.io.Serializable {

	private static final long serialVersionUID = 2485720300736613100L;
	private Long backendDatabaseID;
	private Long triggerTimestamp;
	private Long expiryTimestamp; 
	private Group group;
	private String title;
	private String question;
	
	public ContainerAppQuestion(){
		super();
	}
	
	public ContainerAppQuestion(Long backendDatabaseID, Long triggerTimestamp,
			Long expiryTimestamp, Group group, String title, String question) {
		super();
		this.backendDatabaseID = backendDatabaseID;
		this.triggerTimestamp = triggerTimestamp;
		this.expiryTimestamp = expiryTimestamp;
		this.group = group;
		this.title = title;
		this.question = question;
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
	
	@Column(name = "TRIGGERTIMESTAMP", nullable = false, length = 19)
	public Long getTriggerTimestamp() {
		return triggerTimestamp;
	}

	public void setTriggerTimestamp(Long triggerTimestamp) {
		this.triggerTimestamp = triggerTimestamp;
	}

	@Column(name = "EXPIRYTIMESTAMP", nullable = false, length = 19)
	public Long getExpiryTimestamp() {
		return expiryTimestamp;
	}

	public void setExpiryTimestamp(Long expiryTimestamp) {
		this.expiryTimestamp = expiryTimestamp;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID", nullable = false)
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@Column(name = "TITLE", nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "QUESTION", nullable = false)
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "ContainerAppQuestion [backendDatabaseID=" + backendDatabaseID
				+ ", triggerTimestamp=" + triggerTimestamp
				+ ", expiryTimestamp=" + expiryTimestamp + ", group=" + group
				+ ", title=" + title + ", question=" + question + "]";
	}
	
	public String toJson(){
		String json = "{ " + "\"backendDatabaseID\" : \"" + backendDatabaseID
				+ "\", \"triggerTimestamp\" : \"" + triggerTimestamp
				+ "\", \"expiryTimestamp\" : \"" + expiryTimestamp
				+ "\", \"title\" : \"" + title
				+ "\", \"question\" : \"" + question
				+ "\" }";
		System.out.println(json);
		return json;
	}
	
}
