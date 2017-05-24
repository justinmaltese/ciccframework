package com.ciccFramework.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;

import com.ciccFramework.common.Constants;
import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.BinaryCompatibilityChecker;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.compatibility.QaryCompatibilityChecker;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrix;
import com.ciccFramework.io.CompatibilityMatrixReader;

/* This class represents a K_q(n,r) covering code problem. The number
 * of desired decision variables is also stored, representing the size
 * of the size to be passed to the GreedyCover algorithm.
 * 
 * A compatibility matrix corresponding to the problem is also maintained.
 * While it is not initially stored, it can be initialized and destroyed to
 * selectively conserve memory.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CoveringProblem {
	public int q;
	public int n;
	public int r;
	public int numDecisionVariables;
	private CompatibilityChecker checker;
	
	public CoveringProblem (int q, int n, int r, int numDecisionVariables) {
		this.q = q;
		this.n = n;
		this.r = r;
		this.numDecisionVariables = numDecisionVariables;
	}
	
	public CoveringProblem (int q, int n, int r) {
		this.q = q;
		this.n = n;
		this.r = r;
	}
	
	public String toString() {
		return "K_"+q+"("+n+","+r+")";
	}
	
	/* This method is used to attach the associated compatibility checker
	 * for this problem. If q>3, a compatibility matrix is loaded from file.
	 * Compatibility matrices are often extremely large, thus this method is recommended
	 * only when access to the compatibiliy matrix is needed. When access is no longer
	 * needed, the removeCompatibilityChecker() method should be called to destroy the matrix,
	 * saving memory.
	 * 
	 */

	public void initCompatibilityChecker() {
		CompatibilityMatrix matrix = null;
		if (q == 2) {
			checker = new BinaryCompatibilityChecker(r);
		} else {
			try {
				String fileName = this.compatibilityFileName();
				File file = new File(fileName);
				matrix = CompatibilityMatrixReader.getInstance().loadMatrixFromFile(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			checker = new QaryCompatibilityChecker(matrix);			
	}
	}
	
	/* This method should be called when access to the corresponding
	 * compatibility checker is no longer needed. The compatibility checker
	 * is detroyed, saving memory.
	 */
	
	public void removeCompatibilityChecker() {
		checker = null;
	}
	
	public boolean hasCompatibilityChecker() {
		return (checker != null);
	}
	
	// Get corresponding filepath for .code files
	
	public String outputFileName() {
		String fileName = Constants.OUTPUT_DIRECTORY + "/" + "q=" + q + "/" + "r=" + r + "/" + n + Constants.CODE_FILE_EXTENSION;
		return fileName;
	}
	
	// Get corresponding filepath for .matrix files
	
	public String compatibilityFileName() {
		String fileName = Constants.COMPATIBILITY_DIRECTORY + "/" + "q=" + q + "/" + "r=" + r + "/" + n + Constants.MATRIX_FILE_EXTENSION;
		return fileName;
	}
	
	// Return q^n, representing the number of possible words for the problem
	
	public int getNumPossibleWords() {
		return new BigInteger(String.valueOf(q)).pow(n).intValueExact(); 
	}
	
	public CompatibilityChecker getCompatibilityChecker() {
		return checker;
	}
	
}
