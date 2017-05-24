package com.ciccFramework.compatibility;

import java.util.BitSet;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrix;
import com.ciccFramework.core.ParameterSet;

/* 
 * This class represents a compatibility checker for q-ary words, where q>2.
 * Given two words, a pre-computed compatibility matrix is used to determine if 
 * they are within a given radius (hamming distance) of each other.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class QaryCompatibilityChecker implements CompatibilityChecker {
	
	
	CompatibilityMatrix matrix;
	
	public QaryCompatibilityChecker(CompatibilityMatrix matrix) {	
		this.matrix = matrix;
	}
	
	//check compatibility of two integer words
	
	public boolean checkCompatibility(int row, int col) {
		if (row<col) { // only use upper triangular portion of matrix
			return checkCompatibility(col,row);
		} else if (row == col) { // diagonal of matrix is trivially compatible (identical word is distance 0)
			return true;
		} else { // else check corresponding compatibility matrix value
			return matrix.get(row,col);
		}		
	}
	

}
