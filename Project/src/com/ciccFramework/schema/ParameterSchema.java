package com.ciccFramework.schema;

/* This class represents a schema for an algorithm parameter. A parameter schema
 * is required to possess both a unique ID and an associated type. 
 *  
 *  
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class ParameterSchema {
	public final String ID;
	public final String TYPE;
	public String LABEL;
	public String DEFAULT_VALUE;
	
	public ParameterSchema(String id, String type) {
		this.ID = id;
		this.TYPE = type;
	}
}
