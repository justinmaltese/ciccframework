package com.ciccFramework.io;

import java.util.BitSet;

/* This class represents a task wherein a line of a .matrix file
 * is parsed into a bitset and placed into a predetermined index of a
 * bitset array. Note that a line of a .matrix file represents a row of the
 * compatibility matrix itself.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class FileLineReadTask implements Runnable {
	private final String line;
	private final int index;
	private final BitSet[] representation;
	
	public FileLineReadTask(String line, int index, BitSet[] representation) {
		this.representation = representation;
		this.line = line;
		this.index = index;
	}
	
	public void run() {
		BitSet lineAsBits = parseBitSetFromString(line);
        representation[index] = lineAsBits;
	}
	
	/* This method parses a BitSet from a given line of a .matrix file.
	 * The characters in each line must be either 0 or 1 to represent bits. */
	
	private BitSet parseBitSetFromString(String line) {
		char[] bits = line.toCharArray();
		BitSet returned = new BitSet(bits.length);
		for (int i=0;i<bits.length;i++) {
			char currentBit = bits[i];
			boolean isValidBit = (currentBit == '1' || currentBit == '0');
			if (!isValidBit) {
				throw new RuntimeException("Error when parsing character \"" + currentBit + "\" as bit!");
			} else if (currentBit == '1') {
				returned.set(i);
			} //do nothing for 0 since bitset bits are false by default
		}
		return returned;
	}
}
