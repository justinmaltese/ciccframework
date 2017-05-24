package com.ciccFramework.compatibility;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.core.ParameterSet;

/* 
 * This class represents a compatibility checker for binary words.
 * Two binary words(integers) are compared using bit operations to determine if 
 * they are within a given radius (hamming distance) of each other.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class BinaryCompatibilityChecker implements CompatibilityChecker {
	
	
	private final int radius;
	
	public BinaryCompatibilityChecker(int radius) {
		this.radius = radius;
	}
	
	// Use bit operations to check compatibility of two words
	
	public boolean checkCompatibility(int word1, int word2) {
		if (CompatibilityFunctions.hammingDist(word1,word2) <= radius) {
			return true;
		} else {
			return false;
		}
	}

}
