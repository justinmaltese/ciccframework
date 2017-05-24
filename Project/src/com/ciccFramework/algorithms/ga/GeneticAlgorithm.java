package com.ciccFramework.algorithms.ga;
import java.util.ArrayList;


import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.core.Algorithm;
import com.ciccFramework.core.FitnessCalculationTask;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.Solution;
import com.ciccFramework.core.SolutionSet;
import com.ciccFramework.gui.GraphicalUserInterface;

/* 
 * This class serves as a driver for the Genetic Algorithm.
 * The algorithm executes for a predetermined number of iterations, returning
 * the best ever found solution upon termination.
 *
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class GeneticAlgorithm extends Algorithm {
	
	private Random random = new Random();
	
	public GeneticAlgorithm(CoveringProblem problem, ParameterSet params) {
		super(problem, params);
	}
	
	/* Execute the genetic algorithm */
	
	public Solution execute() {
		int iteration = 0;
		SolutionSet<Chromosome> population = initPopulation((Integer)params.get("POP_SIZE"));
		population.doEvaluation();
		
		// main evolution loop
		while (iteration < (Integer)params.get("MAX_ITERATIONS")) {
			population = generateNewPop(population);
			population.doEvaluation();
			writePopulationStatistics(population,iteration+1);
			iteration++;
			
		}
	
		return population.getBest();
	}
	
	// Create a random initial population
	
	private SolutionSet<Chromosome> initPopulation(int numIndividuals) {
		SolutionSet<Chromosome> generated = new SolutionSet<Chromosome>(problem);
		for (int i=0;i<numIndividuals;i++) {
			ArrayList<Integer> randIntList = UtilFunctions.generateRandomIntList(problem.numDecisionVariables, 0, problem.getNumPossibleWords());
			generated.add(new Chromosome(randIntList,problem));
		}
		return generated;
	}
	
	/* Generated a new child population from a given parent population
	*  using selection and recombination operators.
	*/
	
	private SolutionSet<Chromosome> generateNewPop(SolutionSet<Chromosome> parentPop) {
		
		
		SolutionSet<Chromosome> childPop = new SolutionSet<Chromosome>(problem);
		// if elitism desired, persist best into next generation
		if ((Boolean)params.get("ELITISM")) {
			childPop.add(new Chromosome(parentPop.getBest()));
		}
		
		// while population not full keep performing selection + recombination
		while (childPop.size() < (Integer)params.get("POP_SIZE")) {
			
			Chromosome parent1 = tournamentSelection(parentPop);
			Chromosome parent2 = tournamentSelection(parentPop);
			ChromosomePair children = crossover(parent1,parent2);
			mutate(children.first);
			mutate(children.second);
			
			childPop.add(children.first);
			if (childPop.size() < (Integer)params.get("POP_SIZE")) {
				childPop.add(children.second);
			}
		}
		
		
		return childPop;
	}
	
	private Chromosome tournamentSelection(SolutionSet<Chromosome> pop) {
		Chromosome best = null;
		for (int i=0;i<(Integer)params.get("TOURNAMENT_SIZE");i++) {
			int randIndex = (int) UtilFunctions.getRandIntInRange(0, pop.size()-1);
			Chromosome selected = pop.get(randIndex);
			if (best == null || selected.fitness < best.fitness) {
				best = selected;
			}
		}
		return best;
	}
	
	/* Crossover is performed by first extracting the duplicated cities into
	 * each child chromosome and then shuffling the rest of the codewords, randomly distributing
	 * them onto each child.
	 */
	
	private ChromosomePair crossover(Chromosome parent1, Chromosome parent2) {
		
		if (random.nextDouble() > (Double)params.get("CROSSOVER_RATE")) {
			return new ChromosomePair(parent1,parent2);
		} else {
			ArrayList<Integer> child1 = new ArrayList<Integer>();
			ArrayList<Integer> child2 = new ArrayList<Integer>();

			ArrayList<Integer> parent1Representation = parent1.getDecisionVariablesCopy();
			ArrayList<Integer> parent2Representation = parent2.getDecisionVariablesCopy();
			
			ArrayList<Integer> duplicated = new ArrayList<Integer>();
			ArrayList<Integer> shuffled = new ArrayList<Integer>();
			
			for (int i=0;i<parent1Representation.size();i++) {
				if (parent2Representation.contains(parent1Representation.get(i))) {
					duplicated.add(parent1Representation.get(i));
				} else {
					shuffled.add(parent1Representation.get(i));
				}
			}
			
			for (int i=0;i<parent2Representation.size();i++) {
				if (!duplicated.contains(parent2Representation.get(i))) {
					shuffled.add(parent2Representation.get(i));
				}
			}
			
			Collections.shuffle(shuffled);
			
			// Add the duplicated words to each child
			
			for (int i=0;i<duplicated.size();i++) {
					child1.add(duplicated.get(i));
					child2.add(duplicated.get(i));
			}
			
			// Add all words at even index to child 1
			// Add all words at odd index to child 2
			for (int i=0;i<shuffled.size();i++) {
				if (i % 2 == 0) {
					child1.add(shuffled.get(i));
				} else {
					child2.add(shuffled.get(i));
				}
		}
			

			return new ChromosomePair(new Chromosome(child1,problem),
					new Chromosome(child2,problem));
		}
	}
	
	// Randomly mutate a codeword with a given probability
	
	private void mutate (Chromosome individual) {
		ArrayList<Integer> representation = individual.getDecisionVariablesCopy();
		if (random.nextDouble() < (Double)params.get("MUTATION_RATE")) {
			int selectedIndex = random.nextInt(representation.size());
			individual.setDecisionVariableAtIndexToValue(selectedIndex, UtilFunctions.getRandIntNotInList(0,problem.getNumPossibleWords(),representation));
		}
	}
	
	// Write population statistics to the GUI
	
	public void writePopulationStatistics(SolutionSet<Chromosome> pop, int iteration) {
		
		printLine("[ITERATION " + iteration + "] ");	
		printLine("Average Population Fitness: "+ pop.getAverageFitness());
		Chromosome best  = pop.getBest();
		printLine("Best Population Fitness: " + best.fitness + " ---> " + best);
	}
}
