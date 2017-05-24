package com.ciccFramework.core;

import java.lang.reflect.Constructor;

import com.ciccFramework.schema.AlgorithmSchema;

/* This class uses the Factory pattern to create algorithms from schemas.
 * Reflection is used to construct algorithm instances.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */


/* Construct algorithm from schema using reflection */

public class AlgorithmFactory {
	public static Algorithm create(AlgorithmSchema schema, CoveringProblem problem, ParameterSet params) {
		try {
			Class<?> cl = Class.forName(schema.DRIVER_CLASS);
			Constructor<?> constructor = cl.getConstructor(CoveringProblem.class, ParameterSet.class);
			return (Algorithm) constructor.newInstance(problem,params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
