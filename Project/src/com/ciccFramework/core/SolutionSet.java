package com.ciccFramework.core;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ciccFramework.algorithms.ga.Chromosome;
import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityChecker;


/* This class represents a collection of solutions. Internally, an arraylist
 * is used, thus an ordering of solutions is implicitly imposed.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class SolutionSet<T extends Solution> extends ArrayList<T> {

	private final CoveringProblem problem;
	private static final long serialVersionUID = 7674160822769332828L;
	
	public SolutionSet(CoveringProblem problem) {
		super();
		this.problem = problem;
	}
	
	public double getAverageFitness() {
		double fitnessSum = 0;
		int numIllegal = 0;
		for(int i=0;i<size();i++) {
			double fitness = this.get(i).fitness;
			boolean isOutOfBounds = (fitness == Integer.MAX_VALUE);
			if (!isOutOfBounds) {
				fitnessSum += this.get(i).fitness;
			} else {
				numIllegal++;
			}
		}
		return fitnessSum/((double)(size()-numIllegal));
	}
	
	/* This function returns the Solution possessing the best
	 * fitness out of all members in the set
	 */
	
	public T getBest() {
		T best = null;
		for(int i=0;i<size();i++) {
			if (best == null || this.get(i).fitness < best.fitness) {
				best = this.get(i);
			}
		}
		return best;
	}
	
	/* This function returns the number of illegal solutions contained within the
	 * SolutionSet. A solution is deemed illegal if any of its decision variables have exited
	 * the search space.
	 */
	
	public int getNumIllegal() {
		int numIllegal = 0;
		for(int i=0;i<size();i++) {
			if (this.get(i).isOutOfBounds()) numIllegal++;
		}
		return numIllegal;
	}
	
	/* This method is responsible for performing evaluation of all solutions residing
	 * within the SolutionSet, assigning corresponding fitness values to each. Fitness evaluations
	 * are distributed across all cores to save processing time.
	 * 
	 */
	
	public void doEvaluation() {
		final int NUM_CORES = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(NUM_CORES);
		for (Solution current : this) {
			Runnable fitnessCalcTask = new FitnessCalculationTask(current,problem);
			executor.execute(fitnessCalcTask);
		}
		executor.shutdown();
        while (!executor.isTerminated()) {
        }
	}
	
	
}
