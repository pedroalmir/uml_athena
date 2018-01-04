package br.ufpi.easii.athena.core.system.simulation.petri.place;

import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.system.simulation.SimulationManager;
import br.ufpi.easii.athena.core.system.simulation.petri.base.PetriNetObject;

/**
 * @author PedroAlmir
 */
public class Place extends PetriNetObject implements Comparable<Place>{

	// it's a magic number....
	public static final int UNLIMITED = -1;
	
	/** Token count */
	private int tokens = 0;
	private int maxTokens = UNLIMITED;
	
	/** Athena Infomation */
	private AthenaBundle bundle;
	private SimulationManager manager;
	
	/**
	 * @param bundle
	 */
	public Place(SimulationManager manager, AthenaBundle bundle) {
		super(bundle.getIdentifier());
		this.bundle = bundle;
		this.manager = manager;
	}
	
	/**
	 * @param name
	 */
	public Place(String name) {
		super(name);
	}

	/**
	 * @param name
	 * @param initial
	 */
	public Place(String name, int initial) {
		this(name);
		this.tokens = initial;
	}

	/**
	 * @param threshold
	 * @return
	 */
	public boolean hasAtLeastTokens(int threshold) {
		return (tokens >= threshold);
	}

	/**
	 * @param newTokens
	 * @return
	 */
	public boolean maxTokensReached(int newTokens) {
		if (hasUnlimitedMaxTokens()) { return false; }
		return (tokens + newTokens > maxTokens);
	}

	/**
	 * @return
	 */
	private boolean hasUnlimitedMaxTokens() {
		return maxTokens == UNLIMITED;
	}

	/**
	 * @return
	 */
	public int getTokens() {
		return tokens;
	}

	/**
	 * @param tokens
	 */
	public void setTokens(int tokens) {
		this.tokens = tokens;
	}

	/**
	 * @param max
	 */
	public void setMaxTokens(int max) {
		this.maxTokens = max;
	}

	/**
	 * @param weight
	 */
	public void addTokens(int weight) {
		this.tokens += weight;
	}

	/**
	 * @param weight
	 */
	public void removeTokens(int weight) {
		this.tokens -= weight;
	}

	@Override
	public String toString() {
		return super.toString() + " Tokens=" + this.tokens + " max=" + (hasUnlimitedMaxTokens() ? "unlimited" : this.maxTokens);
	}

	/**
	 * @return the bundle
	 */
	public AthenaBundle getBundle() {
		return bundle;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Place other = (Place) obj;
		if (bundle == null) {
			if (other.bundle != null) return false;
		} else if (!bundle.equals(other.bundle)) return false;
		return true;
	}

	@Override
	public int compareTo(Place o) {
		return 0;
	}

	/**
	 * @return the manager
	 */
	public SimulationManager getManager() {
		return manager;
	}
}
