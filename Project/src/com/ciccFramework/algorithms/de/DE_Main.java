package com.ciccFramework.algorithms.de;

import java.io.FileNotFoundException;

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
 * This class allows a single-run instance of the DE algorithm. The parameter
 * set and covering problem of the algorithm can be modified within.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class DE_Main {
	
	public static void main(String[] args) {
		ParameterSet params = new ParameterSet();
		
		//load params
		params.put("POP_SIZE",500);
		params.put("MAX_ITERATIONS",500);
		params.put("CROSSOVER_PROBABILITY",0.7);
		params.put("DIFFERENTIAL_WEIGHT",0.5);
		
		//load problem
		int q = 3;
		int n = 7;
		int r = 3;
		int numDecisionVars = 7;
		CoveringProblem problem = new CoveringProblem(q,n,r,numDecisionVars);
		
		new Thread(new DifferentialEvolutionAlgorithm(problem,params)).start();
	}
}
