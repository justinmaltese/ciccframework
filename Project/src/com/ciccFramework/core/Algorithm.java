package com.ciccFramework.core;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.gui.GraphicalUserInterface;
import com.ciccFramework.io.CodeWriter;

/* This class serves as the basic skeleton for an algorithm. An 
 * algorithm must have a parameter set, corresponding problem and
 * access to a compatibility matrix to compute codeword compatibility.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public abstract class Algorithm implements Runnable {
	protected final ParameterSet params;
	protected final CoveringProblem problem;
	public Solution bestFound;
	private boolean printOutput = true;
	
	public Algorithm (CoveringProblem problem, ParameterSet params) {
		this.params = params;
		this.problem = problem;
		initCompatibilityCheckerIfNecessary();
	}
	
	/* This method ensures that the  algorithm has access to an associated 
	 * compatibility checker for the problem at hand.
	 */
	
	private void initCompatibilityCheckerIfNecessary() {
		if (!problem.hasCompatibilityChecker()) {
			problem.initCompatibilityChecker();
		}
	}
	
	public void run() {
		bestFound = execute();
		boolean newBest = CodeWriter.getInstance().writeCodeIfNewBest(bestFound, problem);
		if (newBest) {
			printLine("New best ever code found! Written to file: " + problem.outputFileName());
		} 
	}
	
	//determines if the algorithm prints live statistics to the console
	
	public void printOutput(boolean val) {
		printOutput = val;
	}
	
	protected void printLine(String s) {
		if (printOutput) {
			System.out.println(s);
		}
	}
	
	protected abstract Solution execute();
}
