package com.ciccFramework.algorithms.pso;
import java.util.ArrayList;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.Solution;

/* 
 * This class represents a particle in a Particle Swarm Optimization algorithm.
 * A particle corresponds to a candidate solution for a covering problem. Particles
 * maintain a velocity vector along with information about its personal best (best ever
 * encountered) position.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class Particle extends Solution {
	
	protected ArrayList<Integer> personalBest;
	protected ArrayList<Double> velocityVariables;
	protected int pBestFitness;

	public Particle(Particle copy) {
		super((Solution)copy);
		this.velocityVariables = new ArrayList<Double>(copy.velocityVariables);
		this.personalBest = new ArrayList<Integer>(copy.personalBest);
		this.pBestFitness = copy.pBestFitness;
	}
	
	public Particle(ArrayList<Integer> decisionVariables, CoveringProblem problem) {
		super(decisionVariables,problem);
		initVelocity();
	}
	
	private void initVelocity() {
		int size = this.decisionVariables.size();
		velocityVariables = new ArrayList<Double>(size);
		for (int i=0;i<size;i++) {
			velocityVariables.add(0.0);
		}
	}
	
	public ArrayList<Double> getVelocityVariablesCopy() {
		return new ArrayList<Double>(velocityVariables);
	}
	
	public void setVelocityVariableAtIndexToValue(int index, double newValue) {
		velocityVariables.set(index,newValue);
	}
	
	public double getVelocityVariableAtIndex(int index) {
		return velocityVariables.get(index);
	}
	
	public void setToPBest() {
		personalBest = new ArrayList<Integer>(this.decisionVariables);
		pBestFitness = fitness;
	}
	
	public int getPBestFitness() {
		return pBestFitness;
	}

	public ArrayList<Integer> getPBestDimensionsCopy() {
		return new ArrayList<Integer>(personalBest);
	}
	
	public int getPBestDecisionVariableAtIndex(int index) {
		return personalBest.get(index);
	}
	
}
