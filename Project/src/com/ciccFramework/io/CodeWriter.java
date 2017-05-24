package com.ciccFramework.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.ciccFramework.algorithms.ga.Chromosome;
import com.ciccFramework.codes.CoveringCode;
import com.ciccFramework.codes.GreedyCoverAlgorithm;
import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.Solution;

/* This class represents a file writer utility which writes a covering code out
 * as a list of codewords to a .code file. Only codes which have less words than the current best
 * are written out, since smaller number of words are more desirable. 
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CodeWriter {
	
	private static CodeWriter _INSTANCE;
	protected CodeWriter() {
		
	}
	
	public static CodeWriter getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new CodeWriter();
		}
		return _INSTANCE;
	}
	
	/* Given a solution and problem, this function checks if the solution is a new best by loading
	 * the current best for the problem from file. If the solution is a new best, its corresponding covering
	 * code (generated using GreedyCover algorithm) is printed out to file and replaces the old best.
	 */
	
	public boolean writeCodeIfNewBest(Solution seed, CoveringProblem problem) {
		CompatibilityChecker checker = problem.getCompatibilityChecker();
		CoveringCode code = GreedyCoverAlgorithm.execute(seed.getDecisionVariablesCopy(),problem.getNumPossibleWords(),checker);
		
		// ensure that chromosome is illegal and new best
		if (newBest(problem,code.size())) {
			writeCode(code,problem);
			return true;
		} else {
			return false;
		}
	}
	
	// checks if a code is a new best. If the file is not found, this function returns true
	// since the inputted code is a new best by default (no previous best exists).
	
	private boolean newBest(CoveringProblem problem, int numWords) {
		String fileName = problem.outputFileName();
		int codeLength = 0;
		try {
			codeLength = CodeReader.getInstance().readCodeLength(fileName);
		} catch (Exception e) {
			return true;
		}
		
		// if a lesser number of words have been found (more desirable covering code)
		
		if (numWords < codeLength) {
			return true;
		} else {
			return false;
		}
	}
	
	// Writes a code out to a .code file. The name of the .code file
	// is determined by the problem at hand. One line per codeword
	// is printed.
	
	private void writeCode (CoveringCode code, CoveringProblem problem) {
		String fileName = problem.outputFileName();
		File file = new File(fileName);
		file.getParentFile().mkdirs();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int codeWordLength = problem.n;
		
		// prefix codeword with necessary number of 0s and convert to base q
		for (Integer current: code) {
			String representation = UtilFunctions.convertToBaseQ(current, problem.q, codeWordLength);
			writer.println(representation);
		}
		writer.close();
	}
}
