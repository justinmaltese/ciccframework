package com.ciccFramework.compatibility;


/* 
 * This interface represents a compatibility checker which has the functionality of
 * checking the compatibility of 2 different codewords based on a given minimum distance violation.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public interface CompatibilityChecker {
	public boolean checkCompatibility(int word1, int word2);
}
