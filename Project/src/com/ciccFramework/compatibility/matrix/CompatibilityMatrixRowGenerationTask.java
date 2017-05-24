package com.ciccFramework.compatibility.matrix;

import java.util.BitSet;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityFunctions;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;

/* This class represents a task wherein the row of a 
 * compatibility matrix for a q-ary covering code problem
 * is generated. 
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CompatibilityMatrixRowGenerationTask implements Runnable {
	final int rowIndex;
	final int numCols;
	final BitSet[] representation;
	final CoveringProblem problem;
	
	public CompatibilityMatrixRowGenerationTask(BitSet[] representation, int rowIndex, CoveringProblem problem) {
		this.representation = representation;
		this.rowIndex = rowIndex;
		this.numCols = rowIndex;
		this.problem = problem;
	}
	
	public void run() {
		int base = problem.q;
		int wordLength = problem.n;
		int minDist = problem.r;
		BitSet rowBitset = new BitSet(numCols);
		
		// Compute compatibility of entire row and save in representation array
			for (int col=0;col<numCols;col++) {
				String rowInBaseQ = UtilFunctions.convertToBaseQ(rowIndex, base, wordLength);
				String colInBaseQ = UtilFunctions.convertToBaseQ(col, base, wordLength);
				boolean compatible = CompatibilityFunctions.compatible(rowInBaseQ, colInBaseQ, minDist);
				rowBitset.set(col,compatible);			
			}
			representation[rowIndex] = rowBitset;
	}
}
