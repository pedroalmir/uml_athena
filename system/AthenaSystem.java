/**
 * 
 */
package br.ufpi.easii.athena.core.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufpi.easii.athena.common.model.PersistentEntity;
import br.ufpi.easii.athena.common.validation.ValidationClass;
import br.ufpi.easii.athena.web.model.User;
import br.ufpi.easii.athena.web.model.validation.AthenaSystemValidation;

/**
 * This class represents the whole idea behind the AthenaService. Now it'll
 * be possible to create computational intelligence systems that integrate
 * different areas of IA.
 * @author Pedro Almir and Ronyerison
 */
@Entity
@Table(name = "athenasystem")
@ValidationClass(AthenaSystemValidation.class)
public class AthenaSystem implements PersistentEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = 32172733713619597L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/** System name */
	private String name;
	private boolean publicSystem; 

	@Column(columnDefinition = "LONGTEXT")
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "athenasystem_user", 
		joinColumns = {@JoinColumn(name = "athenasystem_id", nullable = false)},
		inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false)})
	private List<User> members;

	@OneToMany(mappedBy="athenaSystem", targetEntity=AthenaArchitecture.class, orphanRemoval = true)
	private List<AthenaArchitecture> architectures;
	/**
	 * Default constructor
	 */
	public AthenaSystem() {
		this.architectures = new ArrayList<AthenaArchitecture>();
		this.members = new ArrayList<User>();
	}

	/**
	 * @param name
	 * @param description
	 * @param creationDate
	 */
	public AthenaSystem(String name, String description) {
		this.name = name;
		this.description = description;
		this.creationDate = new Date();
		this.members = new ArrayList<User>();
		this.architectures = new ArrayList<AthenaArchitecture>();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void addArchitecture(AthenaArchitecture athenaArchitecture){
		this.architectures.add(athenaArchitecture);
	}
	
	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AthenaSystemWeb [id=" + id + ", name=" + name + ", creationDate=" + creationDate + ", owner=" + owner + "]";
	}

	/**
	 * @return the architectures
	 */
	public List<AthenaArchitecture> getArchitectures() {
		return architectures;
	}

	/**
	 * @param architectures the architectures to set
	 */
	public void setArchitectures(List<AthenaArchitecture> architectures) {
		this.architectures = architectures;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AthenaSystem other = (AthenaSystem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		return true;
	}

	/**
	 * @return the publicSystem
	 */
	public boolean isPublicSystem() {
		return publicSystem;
	}

	/**
	 * @param publicSystem the publicSystem to set
	 */
	public void setPublicSystem(boolean publicSystem) {
		this.publicSystem = publicSystem;
	}

	/**
	 * @return the members
	 */
	public List<User> getMembers() {
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(List<User> members) {
		this.members = members;
	}
	
	/**
	 * @param user
	 */
	public void addMember(User user){
		if(this.members != null){
			this.members.add(user);
		}
	}
}
