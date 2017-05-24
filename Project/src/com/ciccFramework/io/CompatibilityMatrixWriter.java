package com.ciccFramework.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.ciccFramework.common.UtilFunctions;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrix;
import com.ciccFramework.core.ParameterSet;

/* This class serves as a compatibility matrix writer which writes compatibility matrices
 * out to .matrix files. 
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CompatibilityMatrixWriter {
	
	private static CompatibilityMatrixWriter _INSTANCE;
	protected CompatibilityMatrixWriter() {
		
	}
	
	public static CompatibilityMatrixWriter getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new CompatibilityMatrixWriter();
		}
		return _INSTANCE;
	}
	
	public void writeToFile(CompatibilityMatrix matrix, String fileName) {
		PrintWriter writer = null;
		try {
			File file = new File(fileName);
			//create file if it doesnt exist
			if(!file.exists()) {
                	file.getParentFile().mkdirs();
					file.createNewFile();
            }
			
			//initialize printwriter and write matrix representation to file
			
			writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			writeMatrix(matrix,writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* This method writes a compatibility matrix out to a file. The matrix is written
	 * character by character to the file, printing lines for each row of the matrix.
	 * 
	 */
	
	public void writeMatrix(CompatibilityMatrix matrix, PrintWriter writer) {
		int numRows = matrix.rowCount();
		for (int row=1;row<numRows;row++) {
			int size = row;
			for (int col=0;col<size;col++) {
				if (matrix.get(row,col)) {
					writer.write("1");
				} else {
					writer.write("0");
				}
			}
			writer.println();
		}
	}
	
}
