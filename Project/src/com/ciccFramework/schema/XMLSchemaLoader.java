package com.ciccFramework.schema;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ciccFramework.common.Constants;


/* This class parses algorithm schemas from XML files. Algorithm schemas also 
 * include a list of parameter schemas for each individual parameter of the algorithm.
 *  
 *  
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class XMLSchemaLoader {
	
	/* This method is used to load all available algorithm schemas from the designated
	 * algorithm schema directory. Each schema is contained within a separate .xml file.
	 * All files are loaded and their xml algorithm schemas are parsed into AlgorithmSchema
	 * objects. Each algorithm schema also loads a set of parameter schemas.
	 */
	
	public static HashMap<String,AlgorithmSchema> loadAlgorithmSchemas() {
		HashMap<String,AlgorithmSchema> schemaMap = new HashMap<String,AlgorithmSchema>();
		File schemaDirectory = new File(Constants.ALGORITHM_SCHEMA_DIRECTORY);
		// for each xml file parse algorithm schema
		for (File fileEntry : schemaDirectory.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	AlgorithmSchema schema =  parseAlgorithmSchemaFromFile(fileEntry);
	        	schemaMap.put(schema.ID,schema);
	        }
	    }
		return schemaMap;
	}
	
	// parse algorithm schema given xml file
	
	private static AlgorithmSchema parseAlgorithmSchemaFromFile(File file) {
		AlgorithmSchema parsedSchema = null;
		try {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		
		Node algorithm = doc.getElementsByTagName("algorithm").item(0);
		NamedNodeMap attributes = algorithm.getAttributes();
		
		// parse attributes of algorithm schema
		String id = attributes.getNamedItem("id").getTextContent();
		String algorithmName = attributes.getNamedItem("name").getTextContent();
		String driverClass = attributes.getNamedItem("driverClass").getTextContent();

		parsedSchema = new AlgorithmSchema(id,algorithmName,driverClass);
		Element element = (Element) algorithm;
		Element parameterSet = (Element) element.getElementsByTagName("parameterSet").item(0);

		// parse parameter schemas of associated algorithm
		parsedSchema.PARAMS = parseParameterList(parameterSet);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parsedSchema;
	}
	
	// parse list of parameter schemas given a parameterSet element
	
	private static ArrayList<ParameterSchema> parseParameterList(Element parameterSet) {
		ArrayList<ParameterSchema> parameterList = new ArrayList<ParameterSchema>();
		NodeList parameters = parameterSet.getElementsByTagName("parameter");
		
		
		// within loop, create parameter schema and add to schema list
		for (int i=0;i<parameters.getLength();i++) {
					
			NamedNodeMap current = parameters.item(i).getAttributes();
			
			// add required attributees (id and type)
			String id = current.getNamedItem("id").getTextContent();
			String type = current.getNamedItem("type").getTextContent();
			ParameterSchema createdParam = new ParameterSchema(id,type);

			// add label and defaultValue attributes if defined
			if (current.getNamedItem("label") != null) {
				createdParam.LABEL = current.getNamedItem("label").getTextContent();
			}
			
			if (current.getNamedItem("defaultValue") != null) {
				createdParam.DEFAULT_VALUE = current.getNamedItem("defaultValue").getTextContent();
			}
			parameterList.add(createdParam);
		}
		return parameterList;
	}
}
