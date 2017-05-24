package com.ciccFramework.algorithms.pso;

import java.util.ArrayList;
import java.util.Random;

import com.ciccFramework.algorithms.ga.Chromosome;
import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.core.Algorithm;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.Solution;
import com.ciccFramework.core.SolutionSet;
import com.ciccFramework.gui.GraphicalUserInterface;

/* 
 * This class serves as a driver for the Particle Swarm Optimization Algorithm.
 * The algorithm executes for a predetermined number of iterations, returning
 * the best ever found solution upon termination.
 *
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class PSOAlgorithm extends Algorithm {

	Particle globalBest;
	private Random random = new Random();
	
	public PSOAlgorithm(CoveringProblem problem, ParameterSet params) {
		super(problem, params);
	}
	
	public Solution execute() {
		SolutionSet<Particle> swarm =  new SolutionSet<Particle>(problem);
		initSwarm(swarm);
		//main algorithm loop
		for (int iteration=0;iteration<(Integer)params.get("MAX_ITERATIONS");iteration++) {
			updatePositionAndVelocity(swarm);
			swarm.doEvaluation();
			updateGBestsAndPBests(swarm);
			writeSwarmStatistics(swarm,(iteration+1));
		}
		return globalBest;
	}

	// Create a random initial swarm
	
	private void initSwarm(SolutionSet<Particle> swarm) {
		for (int i=0;i<(Integer)params.get("SWARM_SIZE");i++) {
			ArrayList<Integer> randIntList = UtilFunctions.generateRandomIntList(problem.numDecisionVariables, 0, problem.getNumPossibleWords());
			Particle added = new Particle(randIntList,problem);
			swarm.add(added);
		}
		
		swarm.doEvaluation();
		initBests(swarm);
	}
	
	// set initial personal bests and global best
	private void initBests(SolutionSet<Particle> swarm) {
		swarm.get(0).setToPBest();
		Particle bestParticle = swarm.get(0);

		for (int i=1;i<swarm.size();i++) {
			Particle current = swarm.get(i);
			current.setToPBest();
			boolean isLegal = !current.isOutOfBounds();
			if (isLegal && current.fitness < bestParticle.fitness) {
				bestParticle = current;
			}	
		}
		

		globalBest = new Particle(bestParticle);
	}
	
	//update gbest (if required) and update personal bests of each particle
	
	private void updateGBestsAndPBests(SolutionSet<Particle> swarm) {
		Particle bestParticle = null;
		for (int i=0;i<(Integer)params.get("SWARM_SIZE");i++) {
			Particle current = swarm.get(i);
			boolean isLegal = !current.isOutOfBounds();
			if (isLegal && current.fitness < globalBest.fitness) {
				bestParticle = current;
			}
			
			if (isLegal && current.fitness < current.getPBestFitness()) {
				current.setToPBest();
			}
		}
		
		boolean gBestUpdateRequired = (bestParticle != null);
		if (gBestUpdateRequired) {
			globalBest = new Particle(bestParticle);
		}
	}
	
	private void updatePositionAndVelocity(SolutionSet<Particle> swarm) {
		for (int i=0;i<(Integer)params.get("SWARM_SIZE");i++) {
			Particle current = swarm.get(i);
			
			//update each decision variable using position+velocity update formulas
			for (int j=0;j<current.getNumDecisionVariables();j++) {
				double newVelocity = (current.getVelocityVariableAtIndex(j)*(Double)params.get("INERTIAL_WEIGHT")) + 
						(random.nextDouble()*(double)(current.getPBestDecisionVariableAtIndex(j)-current.getDecisionVariableAtIndex(j))*(Double)params.get("COGNITIVE_WEIGHT")) +
						(random.nextDouble()*(double)(globalBest.getDecisionVariableAtIndex(j)-current.getDecisionVariableAtIndex(j))*(Double)params.get("SOCIAL_WEIGHT"));
				current.setVelocityVariableAtIndexToValue(j, newVelocity);
				
				int newPosition = (int) Math.round((current.getDecisionVariableAtIndex(j) + newVelocity));
				current.setDecisionVariableAtIndexToValue(j, newPosition);
			}
		}
	}
	
	public void writeSwarmStatistics(SolutionSet<Particle> swarm, int iteration) {
		
		printLine("[ITERATION " + iteration + "] ");	
		int numIllegal = swarm.getNumIllegal();
		printLine("Average Population Fitness: "+ swarm.getAverageFitness() + "(" + numIllegal + " illegal)");
		printLine("Best Population Fitness: " + globalBest.fitness + " ---> " + globalBest);
	}
}
