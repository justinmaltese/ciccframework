package com.ciccFramework.codes;

import java.util.ArrayList;

import com.ciccFramework.compatibility.CompatibilityChecker;

/* 
 * This class serves as a greedy algorithm for finding a minimal covering code. A
 * valid covering code is produced when given a list of words as a seed.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */


public class GreedyCoverAlgorithm {
	
	// Generate a valid covering code when given a list of codewords as a seed
	
	public static CoveringCode execute(ArrayList<Integer> seed, long numPossibleWords, CompatibilityChecker checker) {
		CoveringCode returned = new CoveringCode(seed);
		outer:
		for (int i=0;i<numPossibleWords;i++) {
			for (int j=0;j<returned.size();j++) {
				
				if (checker.checkCompatibility(returned.get(j), i) == true) {
					continue outer;
				}
			}
			returned.add(i);
		}
		return returned;
	}
}
