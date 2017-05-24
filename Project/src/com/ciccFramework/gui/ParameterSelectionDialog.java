package com.ciccFramework.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.schema.AlgorithmSchema;
import com.ciccFramework.schema.ParameterSchema;

/* This class represents a dialog interface for selecting parameter set values. Widgets for
 * each parameter are generated dynamically according to a given algorithm schema.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class ParameterSelectionDialog {
	private JFrame frame;
	private AlgorithmSchema schema;
	private int nextXPosition;
	private int nextYPosition;
	private ArrayList<JComponent> components;
	private GraphicalUserInterface gui;
	
	public ParameterSelectionDialog(AlgorithmSchema schema, GraphicalUserInterface gui) {
		this.schema = schema;
		initialize();
		frame.setVisible(true);
		this.gui = gui;
	}
	
	// initialize all components and widgets of the dialog
	
	private void initialize() {
		nextXPosition = 30;
		nextYPosition = 30;
		components = new ArrayList<JComponent>();
		
		// setup window
		int windowHeight = (75 * (schema.PARAMS.size()+1));
		int windowWidth = 375;
		frame = new JFrame();
		frame.setLayout(null);
		frame.setBounds(100, 100, windowWidth, windowHeight);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		// setup title label
		JLabel lblCodeParameters = new JLabel(schema.ID + " Parameters");
		lblCodeParameters.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCodeParameters.setBounds(((frame.getWidth()/2)-(150/2)), nextYPosition, 150, 26); //center label
		nextYPosition += 50;
		frame.add(lblCodeParameters);
		
		
		// add widgets for all parameters
		for (ParameterSchema param: schema.PARAMS) {
			placeParam(param);
		}
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(((frame.getWidth()/2)-(89/2)), nextYPosition, 89, 23);
		saveButton.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	            	ParameterSet params = parseParams();
	            	gui.addAlgorithm(schema, params);
	            	frame.dispatchEvent(new WindowEvent(
	                        frame, WindowEvent.WINDOW_CLOSING));
	            }
	        });
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.add(saveButton);
	}
	
	/* This method places a label and a widget according to a given parameter schema, 
	 * allowing the user to enter a value for the associated parameter. Widgets
	 * are generated dynamically.
	 */
	
	private void placeParam(ParameterSchema param) {
		
		JLabel paramLabel = new JLabel(param.LABEL + ":");
		paramLabel.setBounds(nextXPosition, nextYPosition, 200, 14);
		frame.add(paramLabel);
		
		int widgetXPosition = (nextXPosition + paramLabel.getWidth() + 10);
		int widgetYPosition = nextYPosition;
		
		addWidget(param,widgetXPosition,widgetYPosition);
		
		nextYPosition += 50;
	}
	
	/* This method determines the type of widget from the TYPE attribute of a parameter schema and
	 * adds it to the GUI. Boolean parameters are given checkboxs while all other parameter types are 
	 * assigned text fields.
	 */
	
	private void addWidget(ParameterSchema param, int xPos, int yPos) {
		if (param.TYPE.equals("Integer") || param.TYPE.equals("Long") || param.TYPE.equals("Float") || param.TYPE.equals("Double")) {
			JTextField added = new JTextField();
			if (param.DEFAULT_VALUE != null) {
				added.setText(param.DEFAULT_VALUE);
			}
			added.setColumns(10);
			added.setBounds(xPos, yPos, 41, 20);
			components.add(added);	
			frame.add(added);
		} else if (param.TYPE.equals("Boolean")) {
			JCheckBox added = new JCheckBox("");
			if (param.DEFAULT_VALUE != null) {
				if (param.DEFAULT_VALUE.equals("true")) {
					added.setSelected(true);
				} else if (param.DEFAULT_VALUE.equals("false")) {
					added.setSelected(false);
				}
			}
			added.setBounds(xPos, yPos, 21, 23);
			components.add(added);	
			frame.add(added);
		}
	}
	
	/* This method parses a set of parameters from the user-entered values
	 * of the form widgets.
	 */
	
	private ParameterSet parseParams() {
		ParameterSet parsed = new ParameterSet();
		for (int i=0;i<schema.PARAMS.size();i++) {
			ParameterSchema currentParam = schema.PARAMS.get(i);
			JComponent currentComponent = components.get(i);
			
			Object val = null;
			if (currentParam.TYPE.equals("Integer")) {
				val = Integer.parseInt(((JTextField)currentComponent).getText());
			} else if (currentParam.TYPE.equals("Double")) {
				val = Double.parseDouble(((JTextField)currentComponent).getText());
			} else if (currentParam.TYPE.equals("Float")) {
				val = Float.parseFloat(((JTextField)currentComponent).getText());
			} else if (currentParam.TYPE.equals("Long")) {
				val = Long.parseLong(((JTextField)currentComponent).getText());
			} else if (currentParam.TYPE.equals("Boolean")) {
				val = (((JCheckBox)currentComponent).isSelected());
			}
			parsed.put(currentParam.ID,val);
		}
		return parsed;
	}
}
