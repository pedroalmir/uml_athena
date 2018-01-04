package br.ufpi.easii.athena.core.problem.base.objective;

import br.ufpi.easii.athena.core.solution.fitness.Fitness;
import br.ufpi.easii.athena.core.solution.fitness.MinimisationFitness;
import fj.F;

public class Minimise implements Objective {

    public Fitness evaluate(double fitness) {
        return new MinimisationFitness(fitness);
    }
    
    public <A> A fold(F<Minimise, A> a, F<Maximise, A> b) {
        return a.f(this);
    }

}
