package br.ufpi.easii.athena.core.algorithm.event;

import br.ufpi.easii.athena.core.algorithm.Algorithm;

/**
 * This is the event raised by algorithms after iterations and completion.
 *
 * @see AlgorithmListener
 *
 */
public class AlgorithmEvent {

    /**
     * Creates a new instance of <code>AlgorithmEvent</code> with a given source algorithm.
     *
     * @param source The source {@link Algorithm}.
     */
    public AlgorithmEvent(Algorithm source) {
        this.source = source;
    }

    /**
     * Accessor for the source algorithm.
     *
     * @return the {@link Algorithm} that raised this event.
     */
    public Algorithm getSource() {
        return source;
    }

    private Algorithm source;
}
