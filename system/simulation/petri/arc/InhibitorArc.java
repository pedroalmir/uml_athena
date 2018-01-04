package br.ufpi.easii.athena.core.system.simulation.petri.arc;

import br.ufpi.easii.athena.core.system.simulation.petri.place.Place;
import br.ufpi.easii.athena.core.system.simulation.petri.transition.Transition;

/**
 * @author PedroAlmir
 */
public class InhibitorArc extends Arc {

	/**
	 * @param name
	 * @param p
	 * @param t
	 */
	public InhibitorArc(String name, Place p, Transition t) {
		super(name, p, t);
	}

	@Override
	public boolean canFire() {
		return (place.getTokens() < this.getWeight());
	}
	
	@Override
	public void fire() {/* Do nothing */}

}
