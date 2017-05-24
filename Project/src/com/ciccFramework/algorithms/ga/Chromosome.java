package com.ciccFramework.algorithms.ga;
import java.util.ArrayList;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.Solution;

/* 
 * This class represents a chromosome in a Genetic Algorithm. A chromosome
 * corresponds to a candidate solution for a covering problem.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class Chromosome extends Solution {

	public Chromosome(Chromosome copy) {
		super((Solution)copy);
	}
	
	public Chromosome(ArrayList<Integer> decisionVariables, CoveringProblem problem) {
		super(decisionVariables,problem);
	}
	
}
