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
@Table(name = "container_comment", catalog = "rotterdam")
public class ContainerComment implements java.io.Serializable {

	private static final long serialVersionUID = 590618769282487348L;
	private Long id;
	private Date timestamp;
	private Date lastEdit;
	private User user;
	private String content;
	private Long parentId;
	private int numberOfChanges;
	private Set<ContainerComment> childs;
	
	// TODO Origin Field (probably enum) 

	public ContainerComment() {
	}

	public ContainerComment(User user, String content) {
		this.user = user;
		this.content = content;
	}

	public ContainerComment(User user, String content, Long parentId) {
		this.user = user;
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

	@Column(name = "CONTENT", nullable = false, length = 16777215)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
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
	public Set<ContainerComment> getChilds() {
		return childs;
	}

	public void setChilds(Set<ContainerComment> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "ContainerComment [id=" + id + ", timestamp=" + timestamp
				+ ", lastEdit=" + lastEdit + ", user=" + user.getUsername() + ", content="
				+ content + ", parentId=" + parentId + ", numberOfChanges="
				+ numberOfChanges + ", childs=" + childs + "]";
	}
	
}
