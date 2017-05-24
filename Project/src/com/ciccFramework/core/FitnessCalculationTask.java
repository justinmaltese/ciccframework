package com.ciccFramework.core;
import java.util.ArrayList;












import com.ciccFramework.algorithms.ga.Chromosome;
import com.ciccFramework.codes.CoveringCode;
import com.ciccFramework.codes.GreedyCoverAlgorithm;
import com.ciccFramework.compatibility.CompatibilityChecker;


/* This class represents a fitness calculation task to be performed in
 * its own separate thread. To compute fitness of a solution, its decision variables are used as
 * a seed to the GreedyCover algorithm and a resulting covering code is produced. The fitness is the number
 * of words in the generated covering code (minimization of fitness is desired).
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class FitnessCalculationTask implements Runnable {

	private final Solution solution;
	private final CoveringProblem problem;
	private final CompatibilityChecker checker;
	
	public FitnessCalculationTask(Solution solution, CoveringProblem problem) {
		this.solution = solution;
		this.problem = problem;
		checker = problem.getCompatibilityChecker();
	}
	
	// Perform the fitness calculation
	
	public void run() {
		if (solution.isOutOfBounds()) { 
			solution.fitness = Integer.MAX_VALUE; 
		} else {
			//if representation is legal, execute greedy cover algorithm to determine fitness 
			CoveringCode generated = GreedyCoverAlgorithm.execute(solution.getDecisionVariablesCopy(),problem.getNumPossibleWords(),checker);
			solution.fitness = generated.size();
		}
	}

}
