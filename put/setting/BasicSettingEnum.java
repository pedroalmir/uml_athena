/**
 * 
 */
package br.ufpi.easii.athena.core.put.setting;

import br.ufpi.easii.athena.core.put.base.SettingEnumInterface;
import br.ufpi.easii.athena.core.type.base.Type;
import br.ufpi.easii.athena.core.type.container.graph.DOTType;
import br.ufpi.easii.athena.core.type.derivative.expression.MathExpression;
import br.ufpi.easii.athena.core.type.derivative.select.SelectType;
import br.ufpi.easii.athena.core.type.primitive.numeric.Int;
import br.ufpi.easii.athena.core.type.primitive.numeric.Real;
import br.ufpi.easii.athena.core.type.primitive.string.StringType;

/**
 * @author PedroAlmir
 */
public enum BasicSettingEnum implements SettingEnumInterface{
	OBJECTIVE("objective", "Objective") {
		@Override
		public String getDescription() {
			return "This setting defines the objective.";
		}

		@Override
		public Type getType() {
			SelectType type = new SelectType();
			type.addSelectTypeItemAvailable("Maximization", "Maximization", "This item is the Maximization objetive.");
			type.addSelectTypeItemAvailable("Minimization", "Minimization", "This item is the Minimization objetive.");
			return type;
		}
	}, MAX_ITERATION("maximum_iterations", "Maximum of Iterations") {
		@Override
		public String getDescription() {
			return "This setting defines the maximum number of iterations performed by the algorithm. It is a stopping condition.";
		}

		@Override
		public Type getType() {
			return Int.valueOf(0);
		}
	}, MAX_ERROR("maximum_error", "Maximum Error") {
		@Override
		public String getDescription() {
			return "This setting defines the maximum error accepted by the algorithm. It is a stopping condition.";
		}

		@Override
		public Type getType() {
			return Real.valueOf(0.0);
		}
	}, HEURISTIC_FUNCTION("heuristic_function", "Heuristic Function") {
		@Override
		public String getDescription() {
			return "This function will be used by the algorithm to guide the search for new solutions.";
		}

		@Override
		public Type getType() {
			return new MathExpression("");
		}
	}, QUALITY_FUNCTION("quality_function", "Quality Function") {
		@Override
		public String getDescription() {
			return "This function will be used by the algorithm to evaluate the quality of the solutions.";
		}

		@Override
		public Type getType() {
			return new MathExpression("");
		}
	}, RESTRICTION_FUNCTION("restriction_function", "Restriction Function") {
		@Override
		public String getDescription() {
			return "This function restricts the search for solutions.";
		}

		@Override
		public Type getType() {
			return new MathExpression("");
		}
	}, DOT("dot_string", "DOT String") {
		@Override
		public String getDescription() {
			return "This setting allows for the graph definition that will be used by the algorithm. If it is not set, the system will build a complete graph.";
		}

		@Override
		public Type getType() {
			return new DOTType("");
		}
	}, CONV_KERNEL("conv_kernel", "Convolution Kernel") {
		@Override
		public String getDescription() {
			return "The convolution is performed by sliding the kernel over the image, generally starting at the top left corner, "
					+ "so as to move the kernel through all the positions where the kernel fits entirely within the boundaries of the image.";
		}

		@Override
		public Type getType() {
			return new StringType("");
		}
	}, CONV_DIVISION("conv_division", "Convolution Division") {
		@Override
		public String getDescription() {
			return "This setting divides the result of convolution.";
		}

		@Override
		public Type getType() {
			return Int.valueOf(1);
		}
	};
	
	/** Setting identifier */
	private String identifier;
	/** Setting name */
	private String name;
	
	/**
	 * @param identifier
	 * @param name
	 */
	private BasicSettingEnum(String identifier, String name) {
		this.identifier = identifier;
		this.name = name;
	}

	@Override
	public String getIdentifier() {
		return this.identifier;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
