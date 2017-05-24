package com.ciccFramework.examples;

import java.util.HashMap;

import com.ciccFramework.core.ExecutionHandler;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.schema.AlgorithmSchema;
import com.ciccFramework.schema.XMLSchemaLoader;

/* Example of how to utilize the ExecutionHandler to compare algorithms
 * over multiple runs on different problems. 
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class ExecutionExample {
	public static void main(String[] args) {
		ExecutionHandler handler = new ExecutionHandler();
		
		// retrieve algorithm schemas
		HashMap<String,AlgorithmSchema> algorithmSchemas = XMLSchemaLoader.loadAlgorithmSchemas();
		
		AlgorithmSchema gaSchema = algorithmSchemas.get("GA");
		AlgorithmSchema psoSchema = algorithmSchemas.get("PSO");
	
		// add PSO algorithm parameters
		ParameterSet psoParams = new ParameterSet();
		psoParams.put("SWARM_SIZE",500);
		psoParams.put("MAX_ITERATIONS",200);
		psoParams.put("INERTIAL_WEIGHT",0.7);
		psoParams.put("COGNITIVE_WEIGHT",1.4);
		psoParams.put("SOCIAL_WEIGHT",1.4);
		
		// add genetic algorithm parameters
		ParameterSet gaParams = new ParameterSet();
		gaParams.put("POP_SIZE",500);
		gaParams.put("MAX_ITERATIONS",200);
		gaParams.put("CROSSOVER_RATE",0.85);
		gaParams.put("MUTATION_RATE",0.10);
		gaParams.put("ELITISM",true);
		gaParams.put("TOURNAMENT_SIZE",3);
		
		// attach algorithms to the execution handler
		handler.attachAlgorithmInstance(gaSchema, gaParams);
		handler.attachAlgorithmInstance(psoSchema, psoParams);
		
		// create desired covering problems
		CoveringProblem problemOne = new CoveringProblem(3,9,3,5);
		CoveringProblem problemTwo = new CoveringProblem(3,9,6,5);
		CoveringProblem problemThree = new CoveringProblem(3,10,4,5);
		CoveringProblem problemFour = new CoveringProblem(3,10,5,5);
		
		// attach problems to the execution handler
		handler.attachProblemInstance(problemOne);
		handler.attachProblemInstance(problemTwo);
		handler.attachProblemInstance(problemThree);
		handler.attachProblemInstance(problemFour);
		
		// modify settings of execution handler as needed
		handler.setNumRuns(20);
		handler.writeAlgorithmOutput(true);
		
		// execute all runs
		handler.run();
	}
}
