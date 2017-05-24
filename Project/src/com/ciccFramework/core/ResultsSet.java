package com.ciccFramework.core;

import java.util.ArrayList;

/* This class serves as a simple set of results from run instances. A list of
 * numbers is contained, where each number represents fitness of the best individual
 * found from a run instance.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class ResultsSet {
	public ArrayList<Integer> results;
	
	public ResultsSet() {
		results = new ArrayList<Integer>();
	}
	
	public void addResult(int result) {
		results.add(result);
	}
	
	/* Returns the best (lowest) value out of all runs in the RunSet.
	 * 
	 */
	
	public int getBest() {
		int best = Integer.MAX_VALUE;
		for (Integer current: results) {
			if (current < best) {
				best = current;
			}
		}
		return best;
	}
	
	/* Returns a string in the format (x/y), where x represents
	 * the # of run values equal to the best value overall and y
	 * is the total number of runs in the RunSet.
	 */
	
	public String getBestCountString() {
		int best = getBest();
		int count = 0;
		for (Integer current: results) {
			if (current == best) {
				count++;
			}
		}
		
		if (count == 0) {
			return "";
		} else {
			return "("+count+"/"+results.size()+")";
		}
	}
	
	/* Returns the average value for all runs in the RunSet.
	 *
	 */
	
	public double getAverage() {
		double avg = 0.0;
		for (Integer current: results) {
			avg += current;
		}
		return (avg/((double)results.size()));
	}
}
