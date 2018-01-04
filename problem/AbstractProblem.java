package br.ufpi.easii.athena.core.problem.base;

import org.nfunk.jep.JEP;

import br.ufpi.easii.athena.core.problem.base.objective.Minimise;
import br.ufpi.easii.athena.core.problem.base.objective.Objective;

/**
 * This is a convenience class that keeps track of the number of fitness
 * evaluations. This class can be extended instead of implementing
 * {@link Problem} directly.
 */
public abstract class AbstractProblem implements Problem {
	
	/** Objetive */
    protected Objective objective;
    /** Expression parser */
	protected JEP parser;

	/**
	 * 
	 */
	protected AbstractProblem() {
        this.objective = new Minimise();
        
        this.parser = new JEP();
        this.parser.addStandardFunctions();
        this.parser.addStandardConstants();
    }

    protected AbstractProblem(AbstractProblem copy) {
        this.objective = copy.objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public Objective getObjective() {
        return objective;
    }
}
