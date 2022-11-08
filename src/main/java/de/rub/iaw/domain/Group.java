package de.rub.iaw.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "groups", catalog = "rotterdam")
public class Group implements java.io.Serializable {

	private static final long serialVersionUID = -8024247341626021916L;
	private Long id;
	private Date timestamp;
	private String name;
	private String password;
	private Long parentId;
	private Set<User> users = new HashSet<User>(0);

	public Group() {
	}

	public Group(String name) {
		this.name = name;
	}

	public Group(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public Group(String name, String password, Long parentID) {
		this.name = name;
		this.password = password;
		this.parentId = parentID;
	}

	public Group(String name, String password, Set<User> users) {
		this.name = name;
		this.password = password;
		this.users = users;
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

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", length = 30)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "PARENTID")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	// Bi-Directional relationship needs owning and reverse side. Only relevant
	// for ORM. For other side see user class
	// http://stackoverflow.com/questions/13228455/hibernate-bi-directional-many-to-many-association-creates-duplicates
	// @ManyToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "user_has_group", catalog = "rotterdam",
	// joinColumns = { @JoinColumn(name = "GROUP_ID", nullable = false,
	// updatable = false) },
	// inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false,
	// updatable = false) }
	// )
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
