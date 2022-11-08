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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user", catalog = "rotterdam")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 8027248615971672877L;
	private Long id;
	private Date timestamp;
	private String username;
	private String firstname;
	private String lastname;
	private String fullname;
	private String password;
	private String email;

	private Set<ContainerComment> containerComments = new HashSet<ContainerComment>(0);
	private Set<Group> groups = new HashSet<Group>(0);
	private Set<ContainerNote> containerNotes = new HashSet<ContainerNote>(0);
	private Set<Logevent> logevents = new HashSet<Logevent>(0);

	// bool for first login? used for setting up user information
	// enum for job role => will be used for prompts, try ask people in similar
	// jobs & you are rarely communicating with

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String email, String firstname, String lastname,
			String preferredlanguage, Set<ContainerComment> containerComments, Set<Group> groups,
			Set<ContainerNote> containerNotes) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.containerComments = containerComments;
		this.groups = groups;
		this.containerNotes = containerNotes;
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

	@Column(name = "USERNAME", unique = true, nullable = false, length = 30)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// Password can be null. This would be necessary if we have at one point of
	// time a login method without ldap/AD and then need to store the pw locally
	@Column(name = "PASSWORD", nullable = true)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EMAIL", nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FIRSTNAME")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "LASTNAME")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "FULLNAME")
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonManagedReference // break infinite cycle; see
							// http://stackoverflow.com/a/18288939
	public Set<ContainerComment> getContainerComments() {
		return this.containerComments;
	}

	public void setContainerComments(Set<ContainerComment> containerComments) {
		this.containerComments = containerComments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonManagedReference
	public Set<ContainerNote> getContainerNotes() {
		return this.containerNotes;
	}

	public void setContainerNotes(Set<ContainerNote> containerNotes) {
		this.containerNotes = containerNotes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonManagedReference
	public Set<Logevent> getLogevents() {
		return logevents;
	}

	public void setLogevents(Set<Logevent> logevents) {
		this.logevents = logevents;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_has_group", catalog = "rotterdam", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) })
	public Set<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", timestamp=" + timestamp + ", username=" + username + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", fullname=" + fullname + ", password=" + password + ", email=" + email
				+ "]";
	}

}
