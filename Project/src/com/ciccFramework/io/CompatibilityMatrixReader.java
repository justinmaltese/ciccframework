package com.ciccFramework.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrix;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrixRowGenerationTask;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;

/* This class serves as a compatibility matrix reader which reads in compatibility matrices
 * from .matrix files, parsing them into arrays. Reading the file is parallelized, where each line
 * is read independently as a FileLineReadTask.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CompatibilityMatrixReader {
	
	private static CompatibilityMatrixReader _INSTANCE;
	protected CompatibilityMatrixReader() {
		
	}
	
	public static CompatibilityMatrixReader getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new CompatibilityMatrixReader();
		}
		return _INSTANCE;
	}
	
	public boolean fileExists(CoveringProblem problem) {
		String fileName = problem.compatibilityFileName();
		File file = new File(fileName);
		return file.exists();
	}
	
	/* This method is used to load a compatibility matrix from a given .matrix file. Loading
	 * the matrix into memory is done in parallel, distributed across all available cores. 
	 * 
	 */
	
	public CompatibilityMatrix loadMatrixFromFile(File file) throws FileNotFoundException {
		BitSet[] representation = null;
		
		if (!file.exists()) {
			throw new FileNotFoundException("File not found when loading matrix for file: " + file.getAbsolutePath());
		}
		
		try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            //determine number of rows via reading number of lines in file
            LineNumberReader  lnr = new LineNumberReader(new FileReader(file));
    		lnr.skip(Long.MAX_VALUE);
    		lnr.close();
    		int numRows = (lnr.getLineNumber()+1); // +1 to account for empty row 0 of compatibility matrix
    		representation = new BitSet[numRows];
    		
    		int numFreeCores = Runtime.getRuntime().availableProcessors();
    		ExecutorService executor = Executors.newFixedThreadPool(numFreeCores);

    		String line;
            int index = 1;
            //parse all lines of file in parallel via thread pool
            while((line = bufferedReader.readLine()) != null) {
            	Runnable task = new FileLineReadTask(line,index,representation);
            	executor.execute(task);
            	index++;
            }
            
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            
            
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.err.println("File not found: " + file.getAbsolutePath());                
        }
        catch(IOException ex) {
        	System.err.println("Error reading from file: " + file.getAbsolutePath());   
        }
		return new CompatibilityMatrix(representation);
	}
	

}
