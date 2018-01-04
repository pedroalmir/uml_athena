/**
 * 
 */
package br.ufpi.easii.athena.core.problem.base;

/**
 * @author PedroAlmir
 */
public enum ProblemClass {
	KNAPSACK("KnapsackProblem"), SHORTEST_PATH("ShortestPathProblem"), TRAVELING_SALESMAN("TravelingSalesmanProblem");
	
	/** Description */
	private String description;

	/**
	 * @param description
	 */
	private ProblemClass(String description) {
		this.description = description;
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
	 * @param description
	 * @return
	 */
	public static ProblemClass findByDescription(String description){
		for(ProblemClass pc : ProblemClass.values()){
			if(pc.getDescription().equals(description)){
				return pc;
			}
		}
		return null;
	}
}
