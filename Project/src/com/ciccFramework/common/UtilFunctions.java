package com.ciccFramework.common;
import java.util.ArrayList;
import java.util.Random;

import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;

/* 
 * This class contains a variety of different utility functions.
 *
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class UtilFunctions {
	
	/* Returns a random integer list possessing the following properties:
	 * 1) Each integer is guaranteed to be unique
	 * 2) Each integer is guaranteed to be in the range [lowerLimit,upperLimit]
	 */
	
	public static ArrayList<Integer> generateRandomIntList(int size, int lowerLimit, int upperLimit) {
		ArrayList<Integer> returned = new ArrayList<Integer>();
		for (int i=0;i<size;i++) {
			returned.add(getRandIntNotInList(lowerLimit,upperLimit,returned));
		}
		return returned;
	}
	
	// Return random integer not in given arraylist
	
	public static int getRandIntNotInList(int lowerLimit, int upperLimit, ArrayList<Integer> list) {
		int generated = getRandIntInRange(lowerLimit,upperLimit);
		while (list.contains(generated)) {
			generated = getRandIntInRange(lowerLimit,upperLimit);
		}
		return generated;
	}
	
	// Return random integer in given range
	
	public static int getRandIntInRange(int lowerLimit, int upperLimit) {
		Random rand = new Random();
		int range = upperLimit - lowerLimit;
		int random =  rand.nextInt(range) + lowerLimit;
		return random;
	}
	
	
	
	public static int floorMod(int a, int b) {
		return (a % b + b) % b;
	}
	
	/* Converts a given integer word (stored in memory as bits) into its 
	 * base q representation, given a value of q.
	 */
	
	public static String convertToBaseQ(int word, int base, int wordLength) {
		String unpaddedRepresentation = Integer.toString(word,base);
		return String.format("%" + wordLength +"s", unpaddedRepresentation).replace(" ", "0");
	}
	
	
}
