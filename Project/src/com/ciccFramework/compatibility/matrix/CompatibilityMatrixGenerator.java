package com.ciccFramework.compatibility.matrix;

import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.CompatibilityFunctions;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.io.CompatibilityMatrixReader;
import com.ciccFramework.io.CompatibilityMatrixWriter;

/* 
 * This class is used to co-ordination the batch generation of compatibility
 * matrices for covering code problems. The generation of each matrix row is
 * done in parallel, delegated to the CompatibilityMatrixRowGenerationTask class.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CompatibilityMatrixGenerator implements Runnable {
	private final int maxNValue;
	private final int maxQValue;
	private final int maxRValue;
	
	public CompatibilityMatrixGenerator(int q, int n, int r) {
		this.maxNValue = n;
		this.maxRValue = r;
		this.maxQValue = q;
	}
	
	private CompatibilityMatrix generate(CoveringProblem problem) {
		BitSet[] representation;
		representation = new BitSet[problem.getNumPossibleWords()];
		//remove diagonal and upper triangular portion
		
		//Generate all rows of matrix using threadpool, utilizing all free processors
		int numFreeCores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(numFreeCores);
		for (int row=1;row<representation.length;row++) {
			Runnable rowGenerationTask = new CompatibilityMatrixRowGenerationTask(representation,row,problem);
			executor.execute(rowGenerationTask);
		}
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
			
		return new CompatibilityMatrix(representation);
	}
	
	
	public void run() {
		for (int q=3;q<=maxQValue;q++) {
			for (int r=1;r<=maxRValue;r++) {
				for (int n=r;n<=maxNValue;n++) {
					CoveringProblem problem = new CoveringProblem(q,n,r);
					boolean fileNotCreated = !CompatibilityMatrixReader.getInstance().fileExists(problem);
					if (fileNotCreated) { // generate matrix only if corresponding .matrix file doesnt exist already
						System.out.println("Starting: " + problem);
						CompatibilityMatrix generated = generate(problem);
						CompatibilityMatrixWriter.getInstance().writeToFile(generated,problem.compatibilityFileName());
						System.out.println("Finished: " + problem);
					}
				}
			}
		}
		System.out.println("Finished all jobs!");
	}
}
