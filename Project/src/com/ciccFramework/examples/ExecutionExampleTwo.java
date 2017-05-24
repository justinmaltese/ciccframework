package com.ciccFramework.examples;

import java.util.HashMap;

import com.ciccFramework.core.ExecutionHandler;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.schema.AlgorithmSchema;
import com.ciccFramework.schema.XMLSchemaLoader;

/* Compact example of how to utilize the ExecutionHandler to compare algorithms
 * over multiple runs on different problems.
 * 
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class ExecutionExampleTwo {
	public static void main(String[] args) {
		ExecutionHandler handler = new ExecutionHandler();
		HashMap<String,AlgorithmSchema> algorithmSchemas = XMLSchemaLoader.loadAlgorithmSchemas();
		
		// attach desired algorithms with default parameter sets (specified in corresponding XML schema)
		handler.attachAlgorithmInstance(algorithmSchemas.get("GA"), algorithmSchemas.get("GA").defaultParameterSet());
		handler.attachAlgorithmInstance(algorithmSchemas.get("PSO"), algorithmSchemas.get("PSO").defaultParameterSet());
		handler.attachAlgorithmInstance(algorithmSchemas.get("DPSO"), algorithmSchemas.get("DPSO").defaultParameterSet());
		handler.attachAlgorithmInstance(algorithmSchemas.get("DE"), algorithmSchemas.get("DE").defaultParameterSet());
		handler.attachAlgorithmInstance(algorithmSchemas.get("DDE"), algorithmSchemas.get("DDE").defaultParameterSet());
		
		
		// attach desired problems
		
		handler.attachProblemInstance(new CoveringProblem(5,4,2,9));
		handler.attachProblemInstance(new CoveringProblem(5,5,3,7));
		handler.attachProblemInstance(new CoveringProblem(5,5,2,22));
		handler.attachProblemInstance(new CoveringProblem(5,6,2,40));
		handler.attachProblemInstance(new CoveringProblem(5,6,3,16));
		
		handler.run();
	}
}
