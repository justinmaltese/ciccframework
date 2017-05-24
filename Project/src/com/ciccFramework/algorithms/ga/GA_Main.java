package com.ciccFramework.algorithms.ga;

import java.io.FileNotFoundException;

import com.ciccFramework.algorithms.de.DifferentialEvolutionAlgorithm;
import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.BinaryCompatibilityChecker;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.compatibility.QaryCompatibilityChecker;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrix;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrixGenerator;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.io.CompatibilityMatrixReader;


/*  
 * This class allows a single-run instance of the GA. The parameter
 * set and covering problem of the algorithm can be modified within.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class GA_Main {
	
	public static void main(String[] args) {
		ParameterSet params = new ParameterSet();
		
		//load params
		params.put("POP_SIZE",500);
		params.put("MAX_ITERATIONS",200);
		params.put("CROSSOVER_RATE",0.85);
		params.put("MUTATION_RATE",0.10);
		params.put("ELITISM",true);
		params.put("TOURNAMENT_SIZE",3);
		
		//load problem
		int q = 5;
		int n = 4;
		int r = 2;
		int numDecisionVars = 50;
		CoveringProblem problem = new CoveringProblem(q,n,r,numDecisionVars);
		
		new Thread(new GeneticAlgorithm(problem,params)).start();
	}
}
