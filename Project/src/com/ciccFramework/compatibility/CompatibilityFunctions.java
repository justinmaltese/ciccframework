package com.ciccFramework.compatibility;

/* 
 * This class contains several different functions related to
 * computing the compatibility between two codewords.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CompatibilityFunctions {
	
	public static int hammingDist(int word1, int word2) {
		int intermediate = word1 ^ word2;
		return Integer.bitCount(intermediate);
	}
	
	public static int hammingDist(String word1, String word2) {
		int distance = 0;
	    for (int i = 0; i < word1.length(); i++) {
	        if (word1.charAt(i) != word2.charAt(i)) {
	            distance++;
	        }
	    }

	    return distance;
	}
	
	/* Checks if two words (represented as Strings) are compatible with
	 * respect to a given minimum distance.
	 */
	
	public static boolean compatible(String word1, String word2, int minDistance) {
		int distance = 0;
	    for (int i = 0; i < word1.length(); i++) {
	        if (word1.charAt(i) != word2.charAt(i)) {
	            distance++;
	            //terminate early if minimum distance reached
	            if (distance > minDistance) return false;
	        }
	    }

	    return true;
	}
}
