/**
 * 
 */
package br.ufpi.easii.athena.core.system;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.ufpi.easii.athena.common.model.PersistentEntity;
import br.ufpi.easii.athena.common.validation.ValidationClass;
import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.system.link.Link;
import br.ufpi.easii.athena.core.system.simulation.model.Simulation;
import br.ufpi.easii.athena.web.model.form.bundle.AthenaBundleForm;
import br.ufpi.easii.athena.web.model.form.link.LinkForm;
import br.ufpi.easii.athena.web.model.validation.AthenaSystemValidation;

/**
 * @author Pedro Almir
 */
@Entity
@Table(name = "athenaarchitecture")
@ValidationClass(AthenaSystemValidation.class)
public class AthenaArchitecture implements PersistentEntity{

	/** Serial Version UID */
	private static final long serialVersionUID = 3135982437340053630L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Architecture Name */
	private String name;
	/** Architecture description */
	@Column(columnDefinition = "LONGTEXT")
	private String description;
	/** Access key */
	@Column(unique = true)
	private String accessKey;
	/** new abstraction: module of modules */
	private boolean newAbstraction;
	private String shortName;
	
	/** List of bundles */
	@Transient private List<AthenaBundleForm> bundles;
	@Transient private List<AthenaBundle> realBundles;
	
	/** List of links */
	@Transient private List<LinkForm> links;
	@Transient private List<Link> realLinks;
	
	@Column(columnDefinition = "LONGTEXT")
	private String jsonBundles;
	@Column(columnDefinition = "LONGTEXT")
	private String jsonLinks;
	
	@ManyToOne(targetEntity = AthenaSystem.class, optional=false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "athenasystem_id")
	private AthenaSystem athenaSystem;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastExecution;
	
	/** List of simulations */
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Simulation.class, orphanRemoval = true)
	private List<Simulation> simulations;
	
	/**
	 * Default constructor
	 */
	public AthenaArchitecture() {}
	
	/**
	 * @param name
	 */
	public AthenaArchitecture(String name) {
		this.name = name;
		this.realBundles = new LinkedList<AthenaBundle>();
		this.realLinks = new LinkedList<Link>();
	}
	
	/**
	 * @param name
	 * @param bundles
	 * @param links
	 */
	public AthenaArchitecture(String name, List<AthenaBundleForm> bundles, List<LinkForm> links) {
		this.name = name;
		this.bundles = bundles;
		this.links = links;
		
		this.creationDate = new Date();
		this.lastExecution = null;
		
		Gson gson = new Gson();
		this.jsonBundles = gson.toJson(this.bundles);
		this.jsonLinks = gson.toJson(this.links);
	}
	
	/**
	 * @param name
	 * @param jsonBundles
	 * @param jsonLinks
	 */
	public AthenaArchitecture(String name, String jsonBundles, String jsonLinks) {
		this.name = name;
		this.jsonBundles = jsonBundles;
		this.jsonLinks = jsonLinks;
		
		this.creationDate = new Date();
		this.lastExecution = null;
		
		Gson gson = new Gson();
		Type typeListBundle = new TypeToken<ArrayList<AthenaBundleForm>>(){}.getType();
		this.bundles = gson.fromJson(jsonBundles, typeListBundle);
		
		Type typeListLink = new TypeToken<ArrayList<LinkForm>>(){}.getType();
		this.links = gson.fromJson(jsonLinks, typeListLink);
	}
	
	/**
	 * @param identifier
	 * @return list of bundles with this specific name
	 */
	public List<AthenaBundle> findBundleByName(String name){
		List<AthenaBundle> result = new LinkedList<AthenaBundle>();
		for(AthenaBundle bundle : this.realBundles){
			if(bundle.getName().equals(name)){
				result.add(bundle);
			}
		}
		return result;
	}
	
	/**
	 * 
	 */
	public void initRealBundlesAndLinks() {
		this.realBundles = new LinkedList<AthenaBundle>();
		this.realLinks = new LinkedList<Link>();
	}
	
	/**
	 * @param bundle
	 */
	public void addModule(AthenaBundle bundle){
		this.realBundles.add(bundle);
	}
	
	/**
	 * @param bundles
	 */
	public void addAllModule(List<AthenaBundle> bundles) {
		this.realBundles.addAll(bundles);
	}
	
	/**
	 * Add link method
	 * @param identifier
	 * @param label
	 * @param srcBundle
	 * @param dstBundle
	 * @param srcOutput
	 * @param dstInput
	 */
	public void addLink(String identifier, String label, AthenaBundle srcBundle, AthenaBundle dstBundle, Output srcOutput, Input dstInput){
		dstInput.setLinked(true); srcOutput.setLinked(true);
		this.realLinks.add(new Link(identifier, label, srcBundle, dstBundle, srcOutput, dstInput));
	}
	
	/**
	 * @param listOfLinks
	 */
	public void addAllLink(List<Link> listOfLinks){
		this.realLinks.addAll(listOfLinks);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the bundles
	 */
	public List<AthenaBundleForm> getBundles() {
		return bundles;
	}
	/**
	 * @param bundles the bundles to set
	 */
	public void setBundles(List<AthenaBundleForm> bundles) {
		this.bundles = bundles;
	}
	/**
	 * @return the jsonBundles
	 */
	public String getJsonBundles() {
		return jsonBundles;
	}
	/**
	 * @param jsonBundles the jsonBundles to set
	 */
	public void setJsonBundles(String jsonBundles) {
		this.jsonBundles = jsonBundles;
	}
	/**
	 * @return the links
	 */
	public List<LinkForm> getLinks() {
		return links;
	}
	/**
	 * @param links the links to set
	 */
	public void setLinks(List<LinkForm> links) {
		this.links = links;
	}
	/**
	 * @return the jsonLinks
	 */
	public String getJsonLinks() {
		return jsonLinks;
	}
	/**
	 * @param jsonLinks the jsonLinks to set
	 */
	public void setJsonLinks(String jsonLinks) {
		this.jsonLinks = jsonLinks;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the ahtenaSystem
	 */
	public AthenaSystem getAthenaSystem() {
		return athenaSystem;
	}
	/**
	 * @param athenaSystem the ahtenaSystem to set
	 */
	public void setAthenaSystem(AthenaSystem athenaSystem) {
		this.athenaSystem = athenaSystem;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AthenaArchitecture [name=" + name + ", bundles=" + bundles + ", links=" + links + "]";
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
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
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the lastExecution
	 */
	public Date getLastExecution() {
		return lastExecution;
	}
	
	/**
	 * @param lastExecution the lastExecution to set
	 */
	public void setLastExecution(Date lastExecution) {
		this.lastExecution = lastExecution;
	}
	/**
	 * @return the simulations
	 */
	public List<Simulation> getSimulations() {
		return simulations;
	}
	/**
	 * @param simulations the simulations to set
	 */
	public void setSimulations(List<Simulation> simulations) {
		this.simulations = simulations;
	}
	/**
	 * @return the realBundles
	 */
	public List<AthenaBundle> getRealBundles() {
		return realBundles;
	}
	/**
	 * @param realBundles the realBundles to set
	 */
	public void setRealBundles(List<AthenaBundle> realBundles) {
		this.realBundles = realBundles;
	}
	/**
	 * @return the realLinks
	 */
	public List<Link> getRealLinks() {
		return realLinks;
	}
	/**
	 * @param realLinks the realLinks to set
	 */
	public void setRealLinks(List<Link> realLinks) {
		this.realLinks = realLinks;
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
		result = prime * result
				+ ((jsonBundles == null) ? 0 : jsonBundles.hashCode());
		result = prime * result
				+ ((jsonLinks == null) ? 0 : jsonLinks.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AthenaArchitecture other = (AthenaArchitecture) obj;
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
		if (jsonBundles == null) {
			if (other.jsonBundles != null)
				return false;
		} else if (!jsonBundles.equals(other.jsonBundles))
			return false;
		if (jsonLinks == null) {
			if (other.jsonLinks != null)
				return false;
		} else if (!jsonLinks.equals(other.jsonLinks))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		return true;
	}

	/**
	 * @return the accessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * @param accessKey the accessKey to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * @return the newAbstraction
	 */
	public boolean isNewAbstraction() {
		return newAbstraction;
	}

	/**
	 * @param newAbstraction the newAbstraction to set
	 */
	public void setNewAbstraction(boolean newAbstraction) {
		this.newAbstraction = newAbstraction;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
