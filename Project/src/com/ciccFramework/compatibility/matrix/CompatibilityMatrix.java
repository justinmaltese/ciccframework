package com.ciccFramework.compatibility.matrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;

/* 
 * This class serves as a bit matrix denoting the compatibility
 * between two integer words. Since the matrix is an adjacency array,
 * the lower triangular portion and the diagonal are both redundant
 * and thus omitted to conserve memory.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CompatibilityMatrix {
	
	private final BitSet[] representation;
	
	public CompatibilityMatrix(BitSet[] representation) {
		this.representation = representation;
	}


	public boolean get(int row, int col) {
		return representation[row].get(col);
	}
	
	// returns a count of the number of rows in the matrix
	
	public int rowCount() {
		return representation.length;
	}
}
