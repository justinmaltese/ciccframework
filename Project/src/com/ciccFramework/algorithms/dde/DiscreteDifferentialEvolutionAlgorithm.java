package com.ciccFramework.algorithms.dde;

import java.util.ArrayList;
import java.util.Random;

import com.ciccFramework.algorithms.de.Agent;
import com.ciccFramework.algorithms.ga.Chromosome;
import com.ciccFramework.algorithms.pso.Particle;
import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.core.Algorithm;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.Solution;
import com.ciccFramework.core.SolutionSet;
import com.ciccFramework.gui.GraphicalUserInterface;

/* 
 * This class serves as a driver for the Discrete Differential Evolution Algorithm.
 * The algorithm executes for a predetermined number of iterations, returning
 * the best ever found solution upon termination.
 *
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class DiscreteDifferentialEvolutionAlgorithm extends Algorithm {

	private Random random = new Random();
	
	
	public DiscreteDifferentialEvolutionAlgorithm(CoveringProblem problem, ParameterSet params) {
		super(problem, params);
	}
	
	public Solution execute() {
		SolutionSet<Agent> population =  new SolutionSet<Agent>(problem);
		initPopulation(population);
		//main algorithm loop
		for (int iteration=0;iteration<(Integer)params.get("MAX_ITERATIONS");iteration++) {
			updatePositions(population);
			population.doEvaluation();
			writePopulationStatistics(population,(iteration+1));
		}
		return population.getBest();
	}

	// Create a random initial population
	
	private void initPopulation(SolutionSet<Agent> population) {
		for (int i=0;i<(Integer)params.get("POP_SIZE");i++) {
			ArrayList<Integer> randIntList = UtilFunctions.generateRandomIntList(problem.numDecisionVariables, 0, problem.getNumPossibleWords());
			Agent added = new Agent(randIntList,problem);
			population.add(added);
		}		
		population.doEvaluation();
	}
	
	// create trial position for each agent and accept if trial position better
	
	private void updatePositions(SolutionSet<Agent> population) {
		SolutionSet<Agent> trialPositions = new SolutionSet<Agent>(problem);
		for (int i=0;i<(Integer)params.get("POP_SIZE");i++) {
			Agent createdTrialPosition = createTrialPosition(i,population);
			trialPositions.add(createdTrialPosition);
		}
		
		trialPositions.doEvaluation();
		
		for (int i=0;i<(Integer)params.get("POP_SIZE");i++) {
			if (trialPositions.get(i).fitness < population.get(i).fitness) {
				population.set(i, trialPositions.get(i));
			}
		}
	}
	
	private Agent createTrialPosition(int index, SolutionSet<Agent> population) {
		
		// select 3 agents for crossover
		int NUM_XOVER_AGENTS = 3;
		ArrayList<Integer> positions = new ArrayList<Integer>(NUM_XOVER_AGENTS);
		positions.add(index);
		
		for (int i=0;i<NUM_XOVER_AGENTS;i++) {
			int randUniqueAgentIndex = UtilFunctions.getRandIntNotInList(0, population.size(), positions);
			positions.add(randUniqueAgentIndex);
		}
		
		
		ArrayList<Integer> trialPosition = new ArrayList<Integer>(problem.numDecisionVariables);
		int randDecisionVariableIndex = UtilFunctions.getRandIntInRange(0, problem.numDecisionVariables);
		
		Agent agent1 = population.get(positions.get(1));
		Agent agent2 = population.get(positions.get(2));
		Agent agent3 = population.get(positions.get(3));
		Agent currentAgent = population.get(index);
		
		// create decision variables for new trial position
		for (int i=0;i<problem.numDecisionVariables;i++) {
			double pos;
			//crossover if necessary
			if (random.nextDouble() < (Double)params.get("CROSSOVER_PROBABILITY") || i == randDecisionVariableIndex) {
				pos = agent1.getDecisionVariableAtIndex(i);
				//mutate if necessary
				if (random.nextDouble() < (Double)params.get("MUTATION_PROBABILITY")) {
					pos += ((Double)params.get("DIFFERENTIAL_WEIGHT")*(agent2.getDecisionVariableAtIndex(i) - agent3.getDecisionVariableAtIndex(i)));
					pos = Math.round(pos);
					pos = UtilFunctions.floorMod((int)pos, problem.getNumPossibleWords());
				}
				
			} else {
				pos = currentAgent.getDecisionVariableAtIndex(i);
			}
			
			trialPosition.add((int)(pos));
		}
		
		return new Agent(trialPosition,problem);
	}
	
	
	public void writePopulationStatistics(SolutionSet<Agent> pop, int iteration) {
		
		printLine("[ITERATION " + iteration + "] ");	
		printLine("Average Population Fitness: "+ pop.getAverageFitness());
		Agent best  = pop.getBest();
		printLine("Best Population Fitness: " + best.fitness + " ---> " + best);
	}
}
