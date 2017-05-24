package com.ciccFramework.schema;

import java.util.ArrayList;

import com.ciccFramework.core.ParameterSet;

/* This class represents a schema for an algorithm created within the CICCFramework.
 * An algorithm schema also possesses a list of associated parameter schemas.
 *  
 *  
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */


public class AlgorithmSchema {
	public final String DRIVER_CLASS;
	public final String NAME;
	public final String ID;
	public ArrayList<ParameterSchema> PARAMS;
	
	public AlgorithmSchema(String id, String name, String driverClass) {
		this.ID = id;
		this.NAME = name;
		this.DRIVER_CLASS = driverClass;
	}
	
	/* This method returns a set of parameters with their default values,
	 * provided from the associated parameter schemas.
	 */
	
	public ParameterSet defaultParameterSet() {
		ParameterSet params = new ParameterSet();
		
		for (ParameterSchema paramSchema: PARAMS) {
			Object val = parseParameterValue(paramSchema);
			if (val != null) { // add parameter if it has a default value
				params.put(paramSchema.ID,val);
			}
		}
		return params;
	}
	
	/* This method parses a default parameter value of the correct type given
	 * an associated parameter schema. If no default value is found for the parameter
	 * schema, null is returned.
	 */
	
	private Object parseParameterValue(ParameterSchema schema) {
		if (schema.DEFAULT_VALUE == null) { return null; }
		if (schema.TYPE.equals("Integer")) {
			return Integer.parseInt(schema.DEFAULT_VALUE);
		} else if (schema.TYPE.equals("Double")) {
			return Double.parseDouble(schema.DEFAULT_VALUE);
		} else if (schema.TYPE.equals("Float")) {
			return Float.parseFloat(schema.DEFAULT_VALUE);
		} else if (schema.TYPE.equals("Boolean")) {
			return Boolean.parseBoolean(schema.DEFAULT_VALUE);
		} else {
			throw new RuntimeException("Error when parsing default parameter for " + schema.ID+ ": Type \"" + schema.TYPE + "\" does not exist!");
		}
	}
}
