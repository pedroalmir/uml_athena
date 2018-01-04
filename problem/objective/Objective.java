package br.ufpi.easii.athena.core.problem.base.objective;

import br.ufpi.easii.athena.core.solution.fitness.Fitness;
import fj.F;

public interface Objective {

    /**
     * @param fitness
     * @return Fitness class
     */
    Fitness evaluate(double fitness);
    /**
     * @param a
     * @param b
     * @return
     */
    <A> A fold(F<Minimise, A> a, F<Maximise, A> b);
}
