package br.ufpi.easii.athena.core.problem.base.objective;

import br.ufpi.easii.athena.core.solution.fitness.Fitness;
import br.ufpi.easii.athena.core.solution.fitness.MaximisationFitness;
import fj.F;

public class Maximise implements Objective {

    public Fitness evaluate(double fitness) {
        return new MaximisationFitness(fitness);
    }
    
    public <A> A fold(F<Minimise, A> a, F<Maximise, A> b) {
        return b.f(this);
    }

}
