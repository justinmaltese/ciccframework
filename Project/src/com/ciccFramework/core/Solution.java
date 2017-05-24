package com.ciccFramework.core;
import java.util.ArrayList;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityChecker;

/* This class serves as a solution to a minimal covering code problem. A solution
 * possesses a set of decision variables along with a corresponding fitness value.
 * 
 * Note that solutions are constrained with respect to a covering problem, hence decision
 * variables can be illegal if they exit the search space. Illegal representations are given
 * a fitness value of Integer.MAX_VALUE.
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class Solution {
	protected ArrayList<Integer> decisionVariables;
	public int fitness;
	private int lowerBounds = 0;
	private int upperBounds;
	
	public Solution(ArrayList<Integer> decisionVariables, CoveringProblem problem) {
		this.decisionVariables = decisionVariables;
		this.upperBounds = (problem.getNumPossibleWords()-1);
	}
	
	// copy constructor
	
	public Solution(Solution copy) {
		this.decisionVariables = copy.getDecisionVariablesCopy();
		this.lowerBounds = copy.lowerBounds;
		this.upperBounds = copy.upperBounds;
		this.fitness = copy.fitness;
	}
	
	// String representation of decision variables
	
	public String toString() {
		String strRepresentation = "[";
		for (int i=0;i<decisionVariables.size();i++) {
			boolean isLastIndex = (i==decisionVariables.size()-1);
			if (isLastIndex) {
				strRepresentation += decisionVariables.get(i);
			} else {
				strRepresentation += (decisionVariables.get(i) + ",");
			}
		}		
		strRepresentation += "]";
		return strRepresentation;
	}
	
	// Deep copy of representation
	
	public ArrayList<Integer> getDecisionVariablesCopy() {
		return new ArrayList<Integer>(decisionVariables);
	}
	
	public void setDecisionVariableAtIndexToValue(int index, int newValue) {
		decisionVariables.set(index,newValue);
	}
	
	public int getDecisionVariableAtIndex(int index) {
		return decisionVariables.get(index);
	}
	
	public int getNumDecisionVariables() {
		return decisionVariables.size();
	}
	
	/* Returns a boolean value indicating the legality of the
	 * set of decision variables contained within the solution
	 * 
	 */
	
	public boolean isOutOfBounds() {
		for (Integer currentDecisionVariable: this.decisionVariables) {
			if (currentDecisionVariable < lowerBounds || currentDecisionVariable > upperBounds) {
				return true;
			}
		}
		return false;
	}
	
	
}
