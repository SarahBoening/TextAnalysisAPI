package de.rub.iaw.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "container_note", catalog = "rotterdam")
public class ContainerNote implements java.io.Serializable {

	private static final long serialVersionUID = -5508008810135306996L;
	private Long id;
	private Date timestamp;
	private Date lastEdit;
	private User user;
	private String title;
	private String content;
	private int numberOfChanges;
	private Set<ContainerComment> comments;
	
	// TODO OriginField => From Which Application!
	
	private Integer parentId;

	public ContainerNote() {
	}

	public ContainerNote(User user, String title, String content) {
		this.user = user;
		this.title = title;
		this.content = content;
	}

	public ContainerNote(User user, String title, String content,
			Integer parentId) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.parentId = parentId;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "TITLE", nullable = false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", nullable = false, length = 16777215)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTEDIT", nullable = true, length = 19)
	public Date getLastEdit() {
		return lastEdit;
	}

	public void setLastEdit(Date lastEdit) {
		this.lastEdit = lastEdit;
	}

	@Column(name = "NUMBEROFCHANGES")
	public int getNumberOfChanges() {
		return numberOfChanges;
	}

	public void setNumberOfChanges(int numberOfChanges) {
		this.numberOfChanges = numberOfChanges;
	}
	
	@Transient
	public Set<ContainerComment> getComments() {
		return comments;
	}

	public void setComments(Set<ContainerComment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "ContainerNote [id=" + id + ", timestamp=" + timestamp
				+ ", user=" + user.getUsername() + ", title=" + title + ", content="
				+ content + ", parentId=" + parentId + "]";
	}
	
}
