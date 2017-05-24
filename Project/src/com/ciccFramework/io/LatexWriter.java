package com.ciccFramework.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.ciccFramework.common.Constants;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.core.ResultsSet;
import com.ciccFramework.schema.AlgorithmSchema;

/* This class writes the results of a set of runs into a neatly formatted
 * LaTeX table. The table itself is written to a .tex file and includes LaTeX preamble.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class LatexWriter {
	
	private static LatexWriter _INSTANCE;
	protected LatexWriter() {
		
	}
	
	public static LatexWriter getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new LatexWriter();
		}
		return _INSTANCE;
	}
	
	/* This method parses a nicely formatted LaTeX table from a set of given RunResults.
	 * Schemas for each algorithm along with a list of problems are required to build the table
	 * itself. The table along with preamble is written to a file using the provided filepath (ideally possessing a .tex extension).
	 */
	
	public void write(String fileName, ArrayList<AlgorithmSchema> schemas, ArrayList<CoveringProblem> problems, ResultsSet[][] runResults) {

		try {
			File file = new File(fileName);
			
		// create .tex file if it doesn't exist
		if(!file.exists()) {
        	file.getParentFile().mkdirs();
			file.createNewFile();
		}
		String figure = parsePreamble();
		
		// parse LaTeX table from ResultsSet
		figure += parseTableHeader(problems);
		figure += parseTableBody(schemas,problems,runResults);
		figure += parseTableFooter();
		figure += Constants.LINE_SEPARATOR + "\\end{document}";
		PrintWriter writer = null;
		
		// write results table out to a .tex file
		writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		writer.println(figure);
		writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String parsePreamble() {
		String result = "\\documentclass[12pt, letterpaper]{article}" + Constants.LINE_SEPARATOR;
		
		result += "\\usepackage{multirow}" + Constants.LINE_SEPARATOR;
		result += "\\usepackage[right=0.5in,left=0.5in]{geometry}" + Constants.LINE_SEPARATOR;
		result += "\\begin{document}" + Constants.LINE_SEPARATOR + Constants.LINE_SEPARATOR;
		return result;
	}
	
	private String parseTableHeader(ArrayList<CoveringProblem> problems) {
		String result = "\\begin{table}[t!]" + Constants.LINE_SEPARATOR;
		result += "\\begin{tabular}{|l||l|";
		for (int i=0;i<problems.size();i++) {
			result += "c";
		}
		result += "| }\\hline\n";
		result += "&& \\multicolumn{" + problems.size() + "}{ |c| }{\\textbf{Problem}} \\\\" + Constants.LINE_SEPARATOR;
		result += "\\textbf{Algorithm} & \\textbf{Metric} ";
		for (CoveringProblem currentProblem: problems) {
			result += "& $" + currentProblem + "$ ";
		}
			result += " \\\\ \\hline \\hline" + Constants.LINE_SEPARATOR;
		return result;
	}
	
	private String parseTableBody(ArrayList<AlgorithmSchema> schemas, ArrayList<CoveringProblem> problems, ResultsSet[][] runResults) {
		String result = "";
		String[] metrics = {"Best","Average"};
		int NUM_NON_PROBLEM_COLS = 2; // number of columns in the table which are not associated with problems
		
		for (int algIndex=0;algIndex<schemas.size();algIndex++) {
			result += "\\multirow{2}{*}{" + schemas.get(algIndex).NAME + "}" + Constants.LINE_SEPARATOR;
			
			//write algorithm results for each metric
			for (String currentMetric: metrics) {
				result += "& " + currentMetric;
				for (int probIndex=0;probIndex<problems.size();probIndex++) {
					if (currentMetric.equals("Best")) {
						result += " & " + runResults[algIndex][probIndex].getBest() + runResults[algIndex][probIndex].getBestCountString();
					} else if (currentMetric.equals("Average")) {
						double avg = runResults[algIndex][probIndex].getAverage();
						result += " & " + new DecimalFormat("#.##").format(avg);
					}				
				}
				result += " \\\\ ";
				if (currentMetric.equals("Best")) {
					result += "\\cline{2-" + (NUM_NON_PROBLEM_COLS+problems.size()) +"}" + Constants.LINE_SEPARATOR;
				} else if (currentMetric.equals("Average")) {
					result += "\\hline" + Constants.LINE_SEPARATOR;
				}	
			}
			
		}
		return result;
	}
	
	private String parseTableFooter() {
		String result = "\\end{tabular}" + Constants.LINE_SEPARATOR;
		result += "\\end{table}" + Constants.LINE_SEPARATOR;
		return result;
	}
}
