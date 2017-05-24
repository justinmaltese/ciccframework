package com.ciccFramework.codes;

import java.util.ArrayList;


/* 
 * This class represents an error correcting code, containing
 * a list of integers which each correspond to a codeword.
 * 
 * 
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class CoveringCode extends ArrayList<Integer>{
	
	private static final long serialVersionUID = 5408368421823568314L;

	public CoveringCode() {
		super();
	}
	
	// Copy constructor
	
	public CoveringCode(ArrayList<Integer> words) {
		super();
		for (Integer current: words) {
			this.add(current);
		}
	}
}
