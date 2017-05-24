package com.ciccFramework.algorithms.dde;

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
 * This class allows a single-run instance of the DiscreteDE algorithm. The parameter
 * set and covering problem of the algorithm can be modified within.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class DiscreteDE_Main {
	
	public static void main(String[] args) {
		ParameterSet params = new ParameterSet();
		
		//load params
		params.put("POP_SIZE",100);
		params.put("MAX_ITERATIONS",500);
		params.put("CROSSOVER_PROBABILITY",0.7);
		params.put("MUTATION_PROBABILITY",0.3);
		params.put("DIFFERENTIAL_WEIGHT",1.0);
		
		//load problem
		int q = 4;
		int n = 7;
		int r = 2;
		int numDecisionVars = 150;
		CoveringProblem problem = new CoveringProblem(q,n,r,numDecisionVars);
		
		new Thread(new DiscreteDifferentialEvolutionAlgorithm(problem,params)).start();
	}
}
