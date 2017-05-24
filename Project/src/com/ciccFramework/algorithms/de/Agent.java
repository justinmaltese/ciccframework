package com.ciccFramework.algorithms.de;
import java.util.ArrayList;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.Solution;

/* 
 * This class represents an agent in a Differential Evolution algorithm. An agent
 * corresponds to a candidate solution for a covering problem.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */
  

public class Agent extends Solution {

	public Agent(Agent copy) {
		super((Solution)copy);
	}
	
	public Agent(ArrayList<Integer> decisionVariables, CoveringProblem problem) {
		super(decisionVariables,problem);
	}
	
}
