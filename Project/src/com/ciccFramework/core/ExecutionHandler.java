package com.ciccFramework.core;

import java.util.ArrayList;
import java.util.HashMap;

import com.ciccFramework.algorithms.de.DifferentialEvolutionAlgorithm;
import com.ciccFramework.common.Constants;
import com.ciccFramework.io.LatexWriter;
import com.ciccFramework.schema.AlgorithmSchema;

/* This class handles all execution within the CICCFramework. Instances of
 * algorithms and problems can be passed to an ExecutionHandler and a set
 * of comparisons can be executed. Results of an execution instance is printed in LaTex
 * table format to an output file upon completion.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class ExecutionHandler implements Runnable {
	private ArrayList<AlgorithmSchema> schemas;
	private ArrayList<ParameterSet> paramSets;
	private ArrayList<CoveringProblem> problems;
	private String outputPath = Constants.DEFAULT_LATEX_OUTPUT_PATH;
	private boolean writeAlgorithmOutput = false;
	private int numRuns = 10;
	
	public ExecutionHandler() {
		//algorithmSchemas and ParameterSets stored in parallel ArrayLists
		schemas = new ArrayList<AlgorithmSchema>();
		paramSets = new ArrayList<ParameterSet>();
		problems = new ArrayList<CoveringProblem>();
	}
	
	public void attachAlgorithmInstance(AlgorithmSchema schema, ParameterSet params) {
		schemas.add(schema);
		paramSets.add(params);
	}
	
	public void attachProblemInstance(CoveringProblem problem) {
		problems.add(problem);
	}
	
	//remove all algorithm instances by reinitializing Parameter and AlgorithmSchema lists
	public void removeAllAlgorithms() {
		schemas = new ArrayList<AlgorithmSchema>();
		paramSets = new ArrayList<ParameterSet>();
	}
	
	
	//remove all problem instances by reinitializing CoveringProblem list
	public void removeAllProblems() {
		problems = new ArrayList<CoveringProblem>();
	}
	
	public void setNumRuns(int runs) {
		numRuns = runs;
	}
	
	/* This method determines if all algorithm output is displayed live during 
	 * execution to the user.
	 */
	
	public void writeAlgorithmOutput(boolean val) {
		writeAlgorithmOutput = val;
	}
	
	public void setOutputPath(String path) {
		outputPath = path;
	}
	
	/* This method performs execution of all attached algorithms on all attached problems. Results
	 * from each run are saved in RunSets and written out to LaTeX tables.
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	public void run() {
		
		//ensure at least one algorithm and problem are present
		if (schemas.size() == 0) {
			throw new RuntimeException("Error when running ExecutionHandler: At least one algorithm must be added!");
		} else if (problems.size() == 0) {
			throw new RuntimeException("Error when running ExecutionHandler: At least one problem must be added!");
		}
		ResultsSet[][] executionResults = new ResultsSet[schemas.size()][problems.size()];
		for (int i=0;i<problems.size();i++) {
			//get problem and load compatibility matrix
			CoveringProblem currentProblem = problems.get(i);
			System.out.println("Loading compatibility matrix for problem: " + currentProblem);
			currentProblem.initCompatibilityChecker();
			System.out.println("Compatibility matrix loading completed!");
			
			//for each algorithm, create schema and execute the desired number of runs for the current problem
			for (int j=0;j<schemas.size();j++) {
				AlgorithmSchema currentSchema = schemas.get(j);
				ParameterSet currentParamSet = paramSets.get(j);
				Algorithm algorithm = AlgorithmFactory.create(currentSchema,currentProblem,currentParamSet);
				algorithm.printOutput(writeAlgorithmOutput);
				executionResults[j][i] = doRuns(algorithm);	
				System.out.println("COMPLETED: " + currentProblem + " problem for algorithm \"" + currentSchema.NAME + "\"");
			}
			
			//remove compatibility checker of the problem to save memory
			currentProblem.removeCompatibilityChecker();
		}
		
		//write results as LaTeX table to .tex file when all runs completed
		LatexWriter.getInstance().write(outputPath, schemas, problems, executionResults);
		System.out.println("All runs completed! Results written to file: " + outputPath);
	}
	
	private ResultsSet doRuns(Algorithm algorithm) {
		ResultsSet returned = new ResultsSet();
		for (int i=0;i<this.numRuns;i++) {
			algorithm.run();
			returned.addResult(algorithm.bestFound.fitness);
			
		}
		return returned;
	}
	
	
}
