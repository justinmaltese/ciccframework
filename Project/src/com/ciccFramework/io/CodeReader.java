package com.ciccFramework.io;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

/* This class serves as a code reader which reads covering codes from file.
 * Since there is one word per line in the file, the returned number of words in 
 * the read file is simply the number of lines. 
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CodeReader {
	private static CodeReader _INSTANCE;
	
	//necessary for Singleton design pattern
	protected CodeReader() {
		
	}
	
	public static CodeReader getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new CodeReader();
		}
		return _INSTANCE;
	}
	
	// Read the code length from file. The length of the code is simply the number of lines,
	// since there is one codeword per line.
	
	public int readCodeLength(String fileName) throws Exception {
		LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(fileName)));
		lnr.skip(Long.MAX_VALUE);
		lnr.close();
		return lnr.getLineNumber();
	}
}
