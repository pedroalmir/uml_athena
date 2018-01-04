/**
 * 
 */
package br.ufpi.easii.athena.core.algorithm.factory;

import java.util.List;

import br.ufpi.easii.athena.core.algorithm.Algorithm;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;

/**
 * The abstract factory pattern is a software creational design pattern first 
 * described in Design Patterns, the book. It provides a way to encapsulate a 
 * group of individual factories that have a common theme without specifying 
 * their concrete classes.
 * <br>
 * In normal usage, the client software creates a concrete implementation of the 
 * abstract factory and then uses the generic interface of the factory to create 
 * the concrete objects that are part of the theme. The client does not know 
 * (or care) which concrete objects it gets from each of these internal factories, 
 * since it uses only the generic interfaces of their products.
 * <br>
 * This pattern separates the details of implementation of a set of objects from 
 * their general usage and relies on object composition, as object creation is 
 * implemented in methods exposed in the factory interface.
 * 
 * @author Pedro Almir
 *
 */
public interface AlgorithmFactory {
	/**
	 * @param inputs
	 * @param outputs
	 * @param settings
	 * @return
	 */
	Algorithm createAlgorithm(List<Input> inputs, List<Output> outputs, List<Setting> settings);
	
}
